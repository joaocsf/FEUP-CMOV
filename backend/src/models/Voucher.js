module.exports = (sequelize, DataTypes) => {
  const voucher = sequelize.define('Voucher', {
    uuid: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV1,
      primaryKey: true
    },
    type: {
      type: DataTypes.ENUM,
      values: ['product', 'discount']
    }
  }, {
    freezeTableName: true
  })

  voucher.associate = (models) => {
    voucher.belongsTo(models.Product, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    voucher.belongsTo(models.Order, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    voucher.belongsTo(models.Costumer, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return voucher
}
