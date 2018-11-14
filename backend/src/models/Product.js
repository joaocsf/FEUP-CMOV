module.exports = (sequelize, DataTypes) => {
  const product = sequelize.define('Product', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    image: {
      type: DataTypes.STRING,
      allowNull: true
    },
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

  product.associate = (models) => {
    product.belongsToMany(models.Order, {
      through: models.OrderQuantity,
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return product
}
