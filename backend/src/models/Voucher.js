module.exports = (sequelize, DataTypes) => {
  const voucher = sequelize.define('Voucher', {
    type: {
      type: DataTypes.STRING,
      allowNull: false
    },
    number: {
      type: DataTypes.TEXT,
      allowNull: false
    },
    validity: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
  }, {
    freezeTableName: true
  })

  voucher.associate = (models) => {
    voucher.belongsTo(models.Product, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    voucher.belongsTo(models.Order,{
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return voucher
}