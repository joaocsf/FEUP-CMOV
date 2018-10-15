module.exports = (sequelize, DataTypes) => {
  const show = sequelize.define('Show', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    date: {
      type: DataTypes.DATE,
      allowNull: false
    },
    price: {
      type: DataTypes.FLOAT,
      allowNull: false
    }
  }, {
    freezeTableName: true
  })

  show.associate = (models) => {
    show.hasMany(models.Ticket, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return show
}
