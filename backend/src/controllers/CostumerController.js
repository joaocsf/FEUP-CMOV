const {Costumer, Card} = require('../models')

module.exports = {
  async registration (req, res) {
    try {
      var user = req.body.user
      var card = req.body.card

      var createdUser = await Costumer.create({
        nif: user.nif,
        name: user.name,
        username: user.username,
        password: user.password,
        publicKey: user.publicKey
      })

      var createdCard = await Card.create({
        type: card.type,
        number: card.number,
        validity: card.validity
      })

      createdUser.setCard(createdCard)

      res.status(200).send({msg: 'Success', uuid: createdUser.uuid})
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  },

  async showAllCostumers (req, res) {
    try {
      var costumers = await Costumer.findAll()
      res.status(200).send({costumers: costumers})
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
