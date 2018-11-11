const { Order, Costumer, Product, Voucher } = require('../models')
const { Op } = require('sequelize')

function parseComponents (objects) {
  var uuid = objects[0]
  objects = objects.slice(1)
  var products = []
  var productIds = []
  var vouchers = []
  for (var value of objects) {
    var elements = value.split(':')
    if (elements.length === 2) {
      var id = elements[0].charCodeAt(0)
      var quantity = elements[1].charCodeAt(0)
      productIds.push(id)
      products[id] = quantity
    } else {
      vouchers.push(value)
    }
  }

  return {uuid: uuid, products: products, vouchers: vouchers, productIds: productIds}
}

module.exports = {
  async order (req, res) {
    try {
      var order = req.body.order
      var validation = req.body.validation
      var productSummary = []
      var components = order.split('_')
      var parsed = parseComponents(components)
      var costumerUuid = parsed.uuid

      var costumer = await Costumer.find({
        where: {
          uuid: parsed.uuid
        }
      })
      var requestValid = costumer.verify(order, validation)
      console.log(`Request is Valid? ${requestValid}`)

      if (!requestValid) {
        res.status(500).send({msg: 'Invalid Request'})
        return
      }

      var products = await Product.findAll({
        where: {
          id: {
            [Op.in]: parsed.productIds
          }
        }
      })
      var vouchers = []
      if (parsed.vouchers.length > 0) {
        vouchers = await Voucher.findAll({
          where: {
            uuid: {
              [Op.in]: parsed.vouchers
            },
            OrderId: null,
            CostumerUuid: costumerUuid
          }
        })
      }

      var productFromID = []

      var total = 0
      for (var product of products) {
        var id = product.id
        var quantity = parsed.products[id]
        var price = quantity * product.price
        total += price

        productFromID[id] = product

        productSummary.push({
          id: id,
          quantity: quantity,
          price: price,
          name: product.name
        })
      }
      var discount = false
      var validVouchers = []

      for (var voucher of vouchers) {
        if (voucher.type === 'discount' && !discount) {
          discount = true
          validVouchers.push(voucher)
          continue
        }
        if (voucher.type === 'discount') {
          continue
        }
        var prod = productFromID[voucher.ProductId]
        var discountValue = prod.price
        total -= discountValue
        validVouchers.push(voucher)
      }

      if (discount) {
        total *= 0.95
      }

      order = await Order.create({
        type: 'product',
        total: total,
        CostumerUuid: costumerUuid
      })

      order.setVouchers(validVouchers)

      for (var p of products) {
        var q = parsed.products[p.id]

        order.addProduct(p, {
          through: {quantity: q}
        })
      }

      var result = {msg: 'Products Sold!', order_id: order.id, productSummary: productSummary, total: total}

      res.status(200).send(result)
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
