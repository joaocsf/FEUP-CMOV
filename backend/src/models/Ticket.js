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
    freezeTableName: true
  })

  ticket.associate = (models) => {
    ticket.belongsTo(models.Show, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    }),
    ticket.belongsTo(models.Costumer, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return ticket
}
