async function addVoucher (order, options) {
  var models = this.models
  var total = await models.Order.sum('total', {
    where: {
      CostumerUuid: order.CostumerUuid
    }
  })

  total = isNaN(total) ? 0 : total
  var multiple = 100
  var beforeValue = Math.floor(total / multiple)
  var afterValue = Math.floor((total + order.total) / multiple)

  var nVouchers = afterValue - beforeValue

  for (var i = 0; i < nVouchers; i++) {
    await models.Voucher.create({
      type: 'discount',
      CostumerUuid: order.CostumerUuid
    })
  }
}

module.exports = (sequelize, DataTypes) => {
  const order = sequelize.define('Order', {
    total: {
      type: DataTypes.FLOAT,
      allowNull: false
    },
    type: {
      type: DataTypes.ENUM,
      values: ['product', 'ticket']
    }
  }, {
    freezeTableName: true,
    hooks: {
      beforeCreate: addVoucher
    }
  })

  order.associate = (models) => {
    order.belongsTo(models.Costumer, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    order.belongsToMany(models.Product, {
      through: models.OrderQuantity,
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    order.hasMany(models.Voucher, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    order.hasMany(models.Ticket, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    order.models = models
  }

  return order
}
