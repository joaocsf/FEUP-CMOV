module.exports = (sequelize, DataTypes) => {
  const show = sequelize.define('Show', {
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    image: {
      type: DataTypes.STRING,
      allowNull: true
    },
    date: {
      type: DataTypes.DATE,
      allowNull: false
    },
    price: {
      type: DataTypes.FLOAT,
      allowNull: false
    },
    duration: {
      type: DataTypes.INTEGER,
      allowNull: false
    }
  }, {
    freezeTableName: true,
    indexes: [
      {
        name: 'date_index',
        method: 'BTREE',
        fields: [{attribute: 'date', order: 'DESC'}]
      }
    ]
  })

  show.associate = (models) => {
    show.hasMany(models.Ticket, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return show
}
