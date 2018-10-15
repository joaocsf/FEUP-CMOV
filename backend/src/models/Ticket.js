module.exports = (sequelize, DataTypes) => {
  const ticket = sequelize.define('Ticket', {
    used: {
      type: DataTypes.BOOLEAN,
      defaultValue: false,
      allowNull: false
    }
  }, {
    freezeTableName: true
  })

  ticket.associate = (models) => {
    ticket.belongsTo(models.Show, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  return ticket
}
