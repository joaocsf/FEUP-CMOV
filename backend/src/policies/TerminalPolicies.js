const Joi = require('joi')
const config = require('../config/config')
const jwt = require('jsonwebtoken')

function JwtSigned (req) {
  const userData = {token: req.get('auth')}
  const schema = {
    token: Joi.string().regex(/^[A-Za-z0-9-_]+\.[A-Za-z0-9-_]+\.[A-Za-z0-9-_.+/=]*$/).required()
  }

  const {error} = Joi.validate(userData, schema, config.joiOptions)

  if (error) {
    throw new Error('Invalid Token')
  } else {
    return jwt.verify(userData.token, config.authentication.jwtSecret)
  }
}

module.exports = {
  loginTerminal (req, res, next) {
    const loginSchema = {
      identifier: Joi.string().regex(config.regex.username).required().trim(),
      password: Joi.string().regex(config.regex.password).required().trim()
    }

    const {error} = Joi.validate(req.body, loginSchema, config.joiOptions)

    if (error) {
      res.status(400).send({error: 'Structure Error'})
    } else next()
  },

  async verifyToken (req, res, next) {
    try {
      JwtSigned(req)
      next()
    } catch (error) {
      res.status(401).send({error: 'Invalid Token'})
    }
  }
}
