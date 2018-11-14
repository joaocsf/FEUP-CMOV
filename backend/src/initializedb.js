const {Terminal, Costumer, Card, Show, Ticket, Product} = require('./models')

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
    username: 'username',
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

  var show1 = await Show.create({
    name: 'Example Show',
    date: '2019/01/01',
    price: 12.3,
    duration: 120
  })
  var show2 = await Show.create({
    name: 'Example Show2',
    date: '2030/12/10',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show2',
    date: '2019/12/10',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show3',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show4',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show5',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show6',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show7',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show8',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Example Show9',
    date: '2019/12/11',
    price: 12.3,
    duration: 120
  })

  printSeparator('Tickets')
  var ticket = await Ticket.create()
  await ticket.setShow(show1)
  await ticket.setCostumer(costumer)
  var ticket2 = await Ticket.create()
  await ticket2.setShow(show1)
  await ticket2.setCostumer(costumer)
  var ticket3 = await Ticket.create()
  await ticket3.setShow(show2)
  await ticket3.setCostumer(costumer)

  printSeparator('Products')
  await Product.create({name: 'Fried Potatoes', quantity: 200, price: 2.50})
  await Product.create({name: 'Good Beans', quantity: 200, price: 2.50})
  await Product.create({name: 'Watery Water', quantity: 200, price: 1.00})
  await Product.create({name: 'Item 0', quantity: 200, price: 0.00})
  await Product.create({name: 'Item 1', quantity: 200, price: 1.00})
  await Product.create({name: 'Item 2', quantity: 200, price: 2.00})
  await Product.create({name: 'Item 3', quantity: 200, price: 3.00})
  await Product.create({name: 'Item 4', quantity: 200, price: 4.00})
  await Product.create({name: 'Item 5', quantity: 200, price: 5.00})

  printSeparator('Terminals')

  await Terminal.create({identifier: 'terminal1', password: 'password'})
  await Terminal.create({identifier: 'terminal2', password: 'pw2'})

  console.log(JSON.stringify(show1))
}
