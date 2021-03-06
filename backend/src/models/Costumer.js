const Promise = require('bluebird')
const bcrypt = Promise.promisifyAll(require('bcrypt-nodejs'))
const NodeRSA = require('node-rsa')

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
      type: DataTypes.STRING,
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

  costumer.prototype.verify = function (data, signature) {
    var key = new NodeRSA()
    key.importKey(this.publicKey, 'public')
    var buffer = Buffer.from(signature, 'base64')
    return key.verify(data, buffer, 'binary')
  }

  return costumer
}
