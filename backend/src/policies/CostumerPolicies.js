const Joi = require('joi')
const config = require('../config/config')

module.exports = {
  createCostumer (req, res, next) {
    const costumerSchema = {
      username: Joi.string().alphanum().required(),
      name: Joi.string().regex(config.regex.name).required(),
      password: Joi.string().required(),
      nif: Joi.number().max(999999999).min(0).required(),
      publicKey: Joi.string().required()
    }

    const cardSchema = {
      type: Joi.string().alphanum().required(),
      number: Joi.string().regex(/^[0-9]{16}$/).required(),
      validity: Joi.number()
    }

    const schema = {
      user: Joi.object(costumerSchema).required(),
      card: Joi.object(cardSchema).required()
    }

    const {error} = Joi.validate(req.body, schema, config.joiOptions)

    if (error) {
      console.log(error)
      res.status(400).send({error: 'Structure Error'})
    } else next()
  }
}
