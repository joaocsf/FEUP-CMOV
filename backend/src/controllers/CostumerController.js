// const {Costumer, Card} = require('../models')

module.exports = {
  async createCostumer (req, res) {
    try {
      // var name = req.body.name
      // var username = req.body.username
      // var nif = req.body.nif
      // var password = req.body.password
      console.log(JSON.stringify(req.body))
      res.status(200).send('Success')
    } catch (error) {
      res.status(500).send('Invalid Data')
    }
  }
}
