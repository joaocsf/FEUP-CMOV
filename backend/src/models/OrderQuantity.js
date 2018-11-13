module.exports = (sequelize, DataTypes) => {
  const orderQuantity = sequelize.define('OrderQuantity', {
    quantity: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    price: {
      type: DataTypes.FLOAT,
      allowNull: false
    }
  }, {
    freezeTableName: true
  })

  return orderQuantity
}
