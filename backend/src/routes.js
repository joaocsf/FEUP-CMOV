const CostumerController = require('./controllers/CostumerController')
const CostumerPolicies = require('./policies/CostumerPolicies')

const ShowController = require('./controllers/ShowController')

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

  // *************
  // * Shows
  // *************
  app.get('/shows',
    ShowController.listShows
  )

  app.get('/shows/popular',
    ShowController.listShowsPopular
  )
  //
}
