
async function addVoucher (ticket, options) {
  var models = this.models

  var products = await models.Product.findAll()
  var productIndex = Math.floor((Math.random() * products.length))
  var product = products[productIndex]

  if (products.length === 0) return

  await models.Voucher.create({
    type: 'product',
    ProductId: product.id,
    CostumerUuid: ticket.CostumerUuid
  })
}

module.exports = (sequelize, DataTypes) => {
  const ticket = sequelize.define('Ticket', {
    uuid: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV1,
      primaryKey: true
    },
    used: {
      type: DataTypes.BOOLEAN,
      defaultValue: false,
      allowNull: false
    },
    seat: {
      type: DataTypes.INTEGER,
      defaultValue: 23,
      allowNull: false
    }
  }, {
    freezeTableName: true,
    hooks: {
      afterCreate: addVoucher
    }
  })

  ticket.associate = (models) => {
    ticket.belongsTo(models.Show, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    ticket.belongsTo(models.Costumer, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    ticket.models = models
  }

  return ticket
}
