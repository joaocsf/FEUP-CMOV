const {Ticket, Show, Costumer, Order} = require('../models')

module.exports = {

  async buyTicket (req, res) {
    try {
      var tickets = []
      var totalSeats = 100
      var showId = req.body.showId
      var costumerUuid = req.get('uuid')
      var numberOfTickets = req.body.numberOfTickets

      var show = await Show.find({
        where: {
          id: showId
        }
      })

      var costumer = await Costumer.find({
        where: {
          uuid: costumerUuid
        }
      })

      for (var i = 0; i < numberOfTickets; i++) {
        var newTicket = await Ticket.create({
          seat: Math.floor(Math.random() * totalSeats) + 1,
          ShowId: show.id,
          CostumerUuid: costumer.uuid
        })
        tickets.push(newTicket)
      }

      var totalAmount = show.price * numberOfTickets

      var order = await Order.create({
        total: totalAmount,
        type: 'ticket',
        CostumerUuid: costumer.uuid
      })

      await order.setTickets(tickets)

      res.status(200).send({msg: 'Success', tickets: tickets})
    } catch (error) {
      console.log(error)
      res.status(500).send({msg: 'Invalid Data' + error})
    }
  },
  async getTickets (req, res) {
    try {
      var tickets = await Show.findAll({
        attrubutes: ['name', 'seat', 'used'],
        include: [
          {
            model: Ticket,
            required: true,
            where: {
              used: false,
              CostumerUuid: req.get('uuid')
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
