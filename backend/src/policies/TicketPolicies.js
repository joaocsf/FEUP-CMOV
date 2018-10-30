const Joi = require('joi')

module.exports = {
  buyTicket (req, res, next) {
    const ticketSchema = {
      numberOfTickets: Joi.string().required(),
      showId: Joi.string().required(),
      costumerUuid: Joi.string().required()  
    }

    const {error} = Joi.validate(req.body, ticketSchema)

    if (error) {
      console.log(error)
      res.status(400).send({error: 'Structure Error'})
    } else next()
  }
}
