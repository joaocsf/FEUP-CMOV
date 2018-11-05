const { Product } = require('../models')

module.exports = {
  async list (req, res) {
    try {
      var products = await Product.findAll({
        attributes: ['id', 'name', 'price']
      })

      res.status(200).send({ products: products })
    } catch (error) {
      res.status(500).send({ msg: 'Invalid Data' })
    }
  }
}
