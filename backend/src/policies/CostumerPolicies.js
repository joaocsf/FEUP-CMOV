const Joi = require('joi')
const config = require('../config/config')

module.exports = {
  createCostumer (req, res, next) {
    const costumerSchema = {
      username: Joi.string().regex(config.regex.username).required().trim(),
      name: Joi.string().regex(config.regex.name).required().trim(),
      password: Joi.string().regex(config.regex.password).required().trim(),
      nif: Joi.string().min(0).max(9).required(),
      publicKey: Joi.string().required()
    }

    const cardSchema = {
      type: Joi.string().required(),
      number: Joi.string().regex(/^[0-9]{16}$/).required(),
      validity: Joi.string().required()
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
