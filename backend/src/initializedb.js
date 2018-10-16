const {Costumer, Card, Show} = require('./models')

function printSeparator (section) {
  console.log(`############\n# ${section}\n###########`)
}

module.exports = async () => {
  console.log('\n\n\n\n\n')
  console.log('Initializing Data')
  printSeparator('Costumers')

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

  console.log('User Created')
  console.log(JSON.stringify(costumer))
  console.log(JSON.stringify(await costumer.getCard()))

  var all = await Costumer.findAll(
    {
      include: [
        {
          model: Card
        }
      ]
    })

  console.log('All')
  console.log(JSON.stringify(all))
  printSeparator('Shows')

  var show = await Show.create({
    name: 'Example Show',
    date: '1999/12/10',
    price: 12.3
  })

  console.log(JSON.stringify(show))
}
