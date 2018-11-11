const {Voucher} = require('../models')

module.exports = {

  async getVouchers (req, res) {
    try {
      var vouchers = await Voucher.findAll({
        where: {
          CostumerUuid: req.get('uuid'),
          OrderId: null
        }
      })
      res.status(200).send({ msg: 'Success', vouchers: vouchers })
    } catch (error) {
      console.log(error)
      res.status(500).send({ msg: 'Invalid Data' + error })
    }
  }
}
