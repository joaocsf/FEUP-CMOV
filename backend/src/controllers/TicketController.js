const { Ticket } = require('../models')
const { Op, literal } = require('sequelize')

module.exports = {

    async buyTicket(req, res) {
        try {

            var tickets = []
            var total_tickets = 100

            for (var i = 0; i < req.body.numberOfTickets; i++) {
                var new_ticket = await Ticket.create({
                    seat: Math.floor(Math.random() * total_tickets) + 1,
                    ShowId: req.body.showId,
                    CostumerUuid: req.body.costumerUuid
                })

                tickets.push(new_ticket)
            }

            console.log(req.body)

            res.status(200).send({ msg: 'Success', tickets: tickets, body: req.body })
        } catch (error) {
            console.log(error)
            res.status(500).send({ msg: 'Invalid Data' + error})
        }
    },
    async getTickets(req, res) {
        try {

            var tickets = await Ticket.findAll({
                attributes: ['uuid', 'used', 'seat', 'CostumerUuid'],
                where: {
                    CostumerUuid: req.query.costumerUuid
                },
            })

            var all_tickets = await Ticket.findAll();

            res.status(200).send({ msg: 'Success', tickets: tickets })
        } catch (error) {
            console.log(error)
            res.status(500).send({ msg: 'Invalid Data' + error })
        }
    }
}
