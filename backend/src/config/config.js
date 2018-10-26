module.exports = {
  port: process.env.PORT || 8080,
  db: {
    username: process.env.DB_USERNAME || 'ardb',
    password: process.env.DB_PASSWORD || 'ardb',
    database: process.env.DB_NAME || 'ardb',
    url: process.env.DB_URL,
    options: {
      host: process.env.DB_HOST || 'localhost',
      dialect: 'postgres',
      operatorsAliases: false
    }
  },
  authentication: {
    jwtSecret: process.env.JWT_SECRET || 'secret',
    expiration: process.env.JWT_EXPIRE || 60 * 60 * 24 * 7,
    adminPassword: process.env.ADMIN_DEFAULT || 'secretpw'
  },
  joiOptions: {
    allowUnknown: true
  },
  regex: {
    line: /^[a-zA-Z0-9 záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ.,?!\-;:()]*$/,
    multiLine: /^[a-zA-Z0-9 \nzáàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ.,?!\-;:()]*$/,
    username: /^[a-zA-Z0-9]{4,32}$/,
    password: /^[a-zA-Z0-9]{5,32}$/,
    email: /^.*@.*$/,
    name: /^[a-zA-Z0-9 záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ.,?!\-;:()]{3,32}$/
  }
}
