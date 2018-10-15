const CostumerController = require('./controllers/CostumerController')
const CostumerPolicies = require('./policies/CostumerPolicies')

module.exports = (app) => {
  // *****************
  // **** Costumers ****
  // *****************
  app.post('/costumer',
    CostumerPolicies.createCostumer,
    CostumerController.createCostumer
  )
}
