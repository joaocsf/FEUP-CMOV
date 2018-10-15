const Promise = require('bluebird')
const bcrypt = Promise.promisifyAll(require('bcrypt-nodejs'))
// const Like = require('../models').Like

function hashPassword (user, options) {
  const SALT_FACTOR = 8

  if (!user.changed('password')) {
    return
  }

  return bcrypt
    .genSaltAsync(SALT_FACTOR)
    .then(salt => bcrypt.hashAsync(user.password, salt, null))
    .then(hash => {
      user.setDataValue('password', hash)
    })
}

module.exports = (sequelize, DataTypes) => {
  const costumer = sequelize.define('Costumer', {
    uuid: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV1,
      primaryKey: true
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false
    },
    nif: {
      type: DataTypes.INTEGER,
      allowNull: false,
      unique: true

    },
    username: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true
    },
    password: {
      type: DataTypes.STRING,
      allowNull: false
    },
    publicKey: {
      type: DataTypes.TEXT,
      allowNull: false
    }
  }, {
    freezeTableName: true,
    hooks: {
      beforeCreate: hashPassword,
      beforeUpdate: hashPassword,
      beforeSave: hashPassword
    }
  })

  costumer.associate = (models) => {
    costumer.hasOne(models.Card, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    costumer.hasMany(models.Ticket, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })

    costumer.hasMany(models.Order, {
      onDelete: 'CASCADE',
      onUpdate: 'CASCADE'
    })
  }

  costumer.prototype.comparePassword = function (password) {
    return bcrypt.compareAsync(password, this.password)
  }

  return costumer
}
