const {Costumer, Card} = require('../models')

module.exports = {
  async registration (req, res) {
    try {
      
      console.log(JSON.stringify(req.body))

      let costumer = await Costumer.create({
        name: req.body.name,
        username: req.body.username,
        password: req.body.password,
        nif: req.body.nif
      });

      console.log("Hello");

      await Card.create({
        type: req.body.card_type,
        number: req.body.card_number,
        validity: req.body.card_validation_number,
      })

      console.log(JSON.stringify(req.body))
      res.status(200).send({msg: 'Success'})
    } catch (error) {
      res.status(500).send({msg: 'Invalid Data'})
    }
  }
}
