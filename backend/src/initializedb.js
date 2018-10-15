const {Costumer, Card} = require('./models')

module.exports = async () => {
  console.log('Initializing Data')

  var card = await Card.create({
    type: '123',
    number: 12312312,
    validity: 231
  })

  var costumer = await Costumer.create({
    name: 'Hello',
    nif: '213123123',
    username: 'UserName',
    password: 'password',
    publicKey: '1231232iioaiod1o2io12oi321'
  })

  await costumer.setCard(card)

  console.log('\n\n\n\n\n')
  console.log('User Created')
  console.log(JSON.stringify(costumer))
  console.log(JSON.stringify(await costumer.getCard()))
  console.log('\n\n\n\n\n')

  var all = await Costumer.findAll(
    {
      include: [
        {
          model: Card
        }
      ]
    })

  console.log('\n\n\n\n\n')
  console.log('All')
  console.log(JSON.stringify(all))
  console.log('\n\n\n\n\n')
}
