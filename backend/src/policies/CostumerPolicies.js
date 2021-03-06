const {Costumer} = require('../models')
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
      res.status(400).send({error: 'Structure Error'})
    } else next()
  },

  login (req, res, next) {
    const schema = {
      oldPassword: Joi.string().regex(config.regex.password).required().trim(),
      newPassword: Joi.string().regex(config.regex.password).required().trim()
    }

    const {error} = Joi.validate(req.body, schema, config.joiOptions)

    if (error) {
      res.status(400).send({error: 'Structure Error'})
    } else next()
  },

  async verifyUser (req, res, next) {
    const header = {
      uuid: Joi.string().required(),
      verification: Joi.string().required()
    }
    var obj = {
      uuid: req.get('uuid'),
      verification: req.get('verification')
    }

    const {error} = Joi.validate(obj, header, config.joiOptions)

    if (error) {
      res.status(400).send({error: 'Structure Error'})
      return
    }

    try {
      var costumer = await Costumer.find({
        where: {
          uuid: obj.uuid
        }
      })

      if (!costumer.verify(obj.uuid, obj.verification)) {
        res.status(400).send({error: 'Invalid User'})
        return
      }

      next()
    } catch (error) {
      res.status(400).send({error: 'Structure Error'})
    }
  }
}
