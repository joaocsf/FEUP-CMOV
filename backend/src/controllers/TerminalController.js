const {Terminal} = require('../models')
const jwt = require('jsonwebtoken')
const config = require('../config/config')

module.exports = {

  async login (req, res) {
    try {
      var identifier = req.body.identifier
      var password = req.body.password
      var terminal = await Terminal.find({
        where: {
          identifier: identifier
        }
      })
      console.log(terminal)

      var authentic = await terminal.comparePassword(password)

      if (!authentic) {
        res.status(401).send({ms: 'Unknown Terminal'})
        return
      }
      var token = jwt.sign({terminal: identifier}, config.authentication.jwtSecret)

      res.status(200).send({token: token})
    } catch (error) {
      console.log(error)
      res.status(500).send({ms: 'Error Loging In'})
    }
  }
}
