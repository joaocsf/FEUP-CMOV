const { Show, Order, Costumer, Product, Voucher, Ticket } = require('../models')
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
      products[id] = {quantity: quantity}
    } else {
      vouchers.push(value)
    }
  }

  return {uuid: uuid, products: products, vouchers: vouchers, productIds: productIds}
}

async function parseTickets (tickets) {
  if (tickets.length === 0) return null

  var showId = tickets[0].ShowId
  var show = await Show.find(
    {
      attributes: ['id', 'name', 'date', 'price', 'duration'],
      where: {id: showId}
    })

  show.dataValues.Tickets = []

  for (var ticket of tickets) {
    show.dataValues.Tickets.push(ticket)
  }

  return show
}

function parseProducts (products) {
  if (products.length === 0) return null

  var parsedProducts = []
  var productByID = []
  for (var product of products) {
    var quantity = product.OrderQuantity.quantity
    var price = product.OrderQuantity.price
    var prod =
      {
        id: product.id,
        name: product.name,
        quantity: quantity,
        price: price
      }
    productByID[prod.id] = prod
    parsedProducts.push(prod)
  }
  return {parsed: parsedProducts, prodByID: productByID}
}

function parseVouchers (vouchers, productByID) {
  if (vouchers.length === 0) return null

  var parsedIDs = {}
  for (var voucher of vouchers) {
    if (voucher.type === 'discount') {
      parsedIDs[-1] = {
        id: -1,
        name: '5% Discount',
        quantity: 1,
        Vouchers: [voucher]
      }
      continue
    }
    var productId = voucher.ProductId
    var product = null
    if (productId in parsedIDs) {
      product = parsedIDs[productId]
    } else {
      product = productByID[productId]
      product = {
        id: product.id,
        name: product.name,
        quantity: product.quantity
      }
      product.Vouchers = []
      parsedIDs[productId] = product
    }
    product.Vouchers.push(voucher)
  }

  return Object.values(parsedIDs)
}

async function parseOrder (order) {
  var ticketShow = await parseTickets(order.Tickets)
  var parsedProducts = parseProducts(order.Products)

  if (parsedProducts != null) {
    var products = parsedProducts.parsed
    var productsByID = parsedProducts.prodByID

    var vouchers = parseVouchers(order.Vouchers, productsByID)
  }

  var finalOrder = {
    id: order.id,
    total: order.total,
    type: order.type,
    date: order.createdAt,
    nif: order.Costumer.nif
  }

  if (ticketShow != null) { finalOrder.Tickets = ticketShow }
  if (products != null) { finalOrder.Products = products }
  if (vouchers != null) { finalOrder.Vouchers = vouchers }

  return finalOrder
}

async function parseOrders (orders) {
  var parsedOrders = []
  for (var order of orders) {
    var o = await parseOrder(order)
    parsedOrders.push(o)
  }
  return parsedOrders
}

async function findOrders (uuid) {
  var orders = await Order.findAll(
    {
      where: {
        CostumerUuid: uuid
      },
      include:
      [
        {model: Costumer, required: false},
        {model: Ticket, attributes: ['uuid', 'seat', 'ShowId'], required: false},
        {model: Voucher, attributes: ['ProductId', 'uuid', 'type'], required: false},
        {model: Product, required: false}
      ],
      limit: 10,
      order: [['"createdAt"', 'DESC']]
    })
  return orders
}

async function findOrder (uuid, orderID) {
  var order = await Order.findOne(
    {
      where: {
        id: orderID
      },
      include:
      [
        {model: Costumer, required: false},
        {model: Ticket, attributes: ['uuid', 'seat', 'ShowId'], required: false},
        {model: Voucher, attributes: ['ProductId', 'uuid', 'type'], required: false},
        {model: Product, required: false}
      ]
    })
  return order
}

module.exports = {
  async getOrder (orderID) {
    var order = await Order.findOne({where: {id: orderID}})
    return parseOrder(order)
  },

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
        var quantity = parsed.products[id].quantity
        var price = quantity * product.price
        parsed.products[id].price = price

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

      await order.setVouchers(validVouchers)

      for (var p of products) {
        var q = parsed.products[p.id].quantity
        var pric = parsed.products[p.id].price

        await order.addProduct(p, {
          through: {quantity: q, price: pric}
        })
      }
      order = await findOrder(costumerUuid, order.id)
      order = await parseOrder(order)
      console.log(JSON.stringify(order))
      var result = {msg: 'Products Sold!', order: order}

      res.status(200).send(result)
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  },

  async getOrders (req, res) {
    try {
      var orders = await findOrders(req.get('uuid'))
      var parsedOrders = await parseOrders(orders)

      res.status(200).send({msg: 'Success', orders: parsedOrders})
    } catch (error) {
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
