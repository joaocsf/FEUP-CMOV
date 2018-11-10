const CostumerController = require('./controllers/CostumerController')
const CostumerPolicies = require('./policies/CostumerPolicies')
const OrderController = require('./controllers/OrderController')

const ShowController = require('./controllers/ShowController')

const ProductController = require('./controllers/ProductController')

const TicketController = require('./controllers/TicketController')
const TicketPolicies = require('./policies/TicketPolicies')

module.exports = (app) => {
  // *****************
  // * Costumers
  // *****************
  app.post('/costumer/create',
    CostumerPolicies.createCostumer,
    CostumerController.registration
  )

  app.get('/costumers',
    CostumerController.showAllCostumers
  )

  app.post('/costumer/login',
    CostumerController.login
  )

  // *************
  // * Shows
  // *************
  app.get('/shows',
    ShowController.listShows
  )

  app.get('/shows/popular',
    ShowController.listShowsPopular
  )

  // *************
  // * Ticket
  // *************

  app.post('/ticket/buy',
    CostumerPolicies.verifyUser,
    TicketPolicies.buyTicket,
    TicketController.buyTicket
  )

  app.get('/ticket/costumer',
    TicketController.getTickets
  )

  // *************
  // * Products
  // *************

  app.get('/products',
    ProductController.list
  )

  // *************
  // * Orders
  // *************

  app.post('/order',
    OrderController.order)
}
