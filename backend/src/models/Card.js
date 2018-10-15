module.exports = (sequelize, DataTypes) => {
  const card = sequelize.define('Card', {
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
      allowNull: false
    }
  }, {
    freezeTableName: true
  })

  card.associate = (models) => {
    card.belongsTo(models.Costumer, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return card
}
