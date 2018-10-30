const { Show } = require('../models')
const { Op, literal } = require('sequelize')
const moment = require('moment')

module.exports = {
  async listShows(req, res) {
    try {
      var shows = await Show.findAll({
        attributes: ['id', 'name', 'date', 'price',
          [literal('(SELECT Count(*) FROM "Ticket" WHERE "Ticket"."ShowId"="Show".id)'), 'atendees']],
        where: {
          date: {
            [Op.gte]: moment().toDate()
          }
        },
        order: [
          ['date', 'ASC']
        ]
      })
      console.log(JSON.stringify(shows))
      res.status(200).send({ shows: shows })
    } catch (error) {
      res.status(500).send({ msg: 'Invalid Data' })
    }
  },
  async listShowsPopular(req, res) {
    try {
      var shows = await Show.findAll({
        attributes: ['id', 'name', 'date', 'price',
          [literal('(SELECT Count(*) FROM "Ticket" WHERE "Ticket"."ShowId"="Show".id)'), 'atendees']],
        where: {
          date: {
            [Op.gte]: moment().toDate()
          }
        },
        order: [
          [literal('atendees'), 'DESC'],
          ['date', 'ASC']
        ]
      })
      console.log(JSON.stringify(shows))
      res.status(200).send({ shows: shows })
    } catch (error) {
      console.log(error)
      res.status(500).send({ msg: 'Invalid Data' })
    }
  },
}
