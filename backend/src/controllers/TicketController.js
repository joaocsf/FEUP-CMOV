const { Ticket, Show } = require('../models')
const { Op, literal } = require('sequelize')

module.exports = {

    async buyTicket(req, res) {
        try {

            var tickets = []
            var total_seats = 100

            for (var i = 0; i < req.body.numberOfTickets; i++) {
                var new_ticket = await Ticket.create({
                    seat: Math.floor(Math.random() * total_seats) + 1,
                    ShowId: req.body.showId,
                    CostumerUuid: req.body.costumerUuid
                })

                tickets.push(new_ticket)
            }

            res.status(200).send({ msg: 'Success', tickets: tickets})
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
                include:[Show]
            })

            res.status(200).send({ msg: 'Success', tickets: tickets })
        } catch (error) {
            console.log(error)
            res.status(500).send({ msg: 'Invalid Data' + error })
        }
    }
}
