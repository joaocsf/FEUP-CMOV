const Promise = require('bluebird')
const bcrypt = Promise.promisifyAll(require('bcrypt-nodejs'))
// const Like = require('../models').Like

function hashPassword (term, options) {
  const SALT_FACTOR = 8

  if (!term.changed('password')) {
    return
  }

  return bcrypt
    .genSaltAsync(SALT_FACTOR)
    .then(salt => bcrypt.hashAsync(term.password, salt, null))
    .then(hash => {
      term.setDataValue('password', hash)
    })
}

module.exports = (sequelize, DataTypes) => {
  const term = sequelize.define('Terminal', {
    identifier: {
      type: DataTypes.STRING,
      allowNull: false
    },
    password: {
      type: DataTypes.STRING,
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

  term.prototype.comparePassword = function (password) {
    return bcrypt.compareAsync(password, this.password)
  }

  return term
}
