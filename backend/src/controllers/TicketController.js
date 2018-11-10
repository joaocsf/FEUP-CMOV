const { Ticket , Show} = require('../models')

module.exports = {

  async buyTicket (req, res) {
    try {
      var tickets = []
      var totalTickets = 100

      for (var i = 0; i < req.body.numberOfTickets; i++) {
        var newTicket = await Ticket.create({
          seat: Math.floor(Math.random() * totalTickets) + 1,
          ShowId: req.body.showId,
          CostumerUuid: req.get('costumerUuid')
        })
        tickets.push(newTicket)
      }

      res.status(200).send({msg: 'Success', tickets: tickets})
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data' + error})
    }
  },
  async getTickets (req, res) {
    try {
      var tickets = await Show.findAll({
        attrubutes:["name", "seat", "used"],
        include:[
          {
            model:Ticket,
            required:true,
            where: {
              used:false,
              CostumerUuid: req.get("costumerUuid"),
            }
          }
        ]
      })

      res.status(200).send({ msg: 'Success', tickets: tickets })
    } catch (error) {
      console.log(error)
      res.status(500).send({ msg: 'Invalid Data' + error })
    }
  }
}
