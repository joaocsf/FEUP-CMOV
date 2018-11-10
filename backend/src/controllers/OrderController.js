const { Order, Costumer, Product } = require('../models')
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

      var products = await Product.findAll({
        where: {
          id: {
            [Op.in]: parsed.productIds
          }
        }
      })

      var total = 0
      for (var product of products) {
        var id = product.id
        var quantity = parsed.products[id]
        var price = quantity * product.price
        total += price
        productSummary.push({
          id: id,
          quantity: quantity,
          price: price,
          name: product.name
        })
      }

      var costumer = await Costumer.find({
        where: {
          uuid: parsed.uuid
        }
      })

      var requestValid = costumer.verify(order, validation)

      console.log(`Request is Valid? ${requestValid}`)

      var result = {msg: 'Products Sold!', productSummary: productSummary, total: total}

      res.status(200).send(result)
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
