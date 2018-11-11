const {Ticket, Show, Costumer, Order, Voucher} = require('../models')

module.exports = {

  async buyTicket (req, res) {
    try {
      var tickets = []
      var totalSeats = 100
      var requestString = req.body.request
      var validation = req.body.validation
      var request = JSON.parse(requestString)
      var showId = request.showId
      var costumerUuid = req.get('uuid')
      var numberOfTickets = request.numberOfTickets

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

      if (!costumer.verify(requestString, validation)) {
        res.status(500).send({msg: 'Invalid Data'})
        return
      }

      var oldVouchers = await Voucher.findAll({
        attributes: ['uuid'],
        where: {
          CostumerUuid: costumer.uuid
        }
      })

      var oldVouchersUUIDS = []
      for (var voucher of oldVouchers) {
        oldVouchersUUIDS.push(voucher.uuid)
      }

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

      var newVouchers = await Voucher.findAll({
        attributes: ['uuid', 'type', 'ProductId'],
        where: {
          CostumerUuid: costumer.uuid
        }
      })
      var addedVouchers = newVouchers.filter(v => !oldVouchersUUIDS.includes(v.uuid))

      res.status(200).send({msg: 'Success', tickets: tickets, vouchers: addedVouchers})
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
