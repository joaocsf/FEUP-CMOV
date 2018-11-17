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

  async login (req, res) {
    try {
      var costumer = await Costumer.find({
        where: {
          username: req.body.username
        }
      })

      if (!await costumer.comparePassword(req.body.password)) {
        res.status(401).send({msg: 'Unknown User'})
        return
      }

      costumer.publicKey = req.body.publicKey
      await costumer.save()

      res.status(200).send({msg: 'Success', uuid: costumer.uuid})
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
  },
  async updatePassword (req, res) {
    console.log(req.body)

    try {
      var costumer = await Costumer.find({
        where: {
          uuid: req.body.uuid
        }
      })
      console.log(costumer)

      if (!await costumer.comparePassword(req.body.oldPassword)) {
        res.status(401).send({msg: 'Wrong password'})
        return
      }

      await Costumer.update({password: req.body.newPassword}, {
        where: {
          uuid: req.body.uuid
        }
      })

      res.status(200).send({msg: 'Updated with success'})
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
