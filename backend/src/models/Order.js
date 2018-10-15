module.exports = (sequelize, DataTypes) => {
  const order = sequelize.define('Order', {
    total: {
      type: DataTypes.FLOAT,
      allowNull: false
    }
  }, {
    freezeTableName: true
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
  }

  return order
}
