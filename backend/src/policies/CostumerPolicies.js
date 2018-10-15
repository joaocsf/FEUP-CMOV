const Joi = require('joi')
const config = require('../config/config')

module.exports = {
  createCostumer (req, res, next) {
    const schema = {
      username: Joi.string().alphanum().required(),
      name: Joi.string().regex(config.regex.name).required(),
      password: Joi.string().required(),
      nif: Joi.number().max(999999999).min(0).required()
    }

    const {error} = Joi.validate(req.body, schema, config.joiOptions)

    if (error) {
      res.status(400).send('Structure Error')
    } else next()
  }
}
