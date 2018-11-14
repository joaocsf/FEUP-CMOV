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
    name: 'Big Bad Fortune',
    date: '2019/01/01',
    image: 'https://i.imgur.com/OMlHSDF.jpg',
    price: 12.3,
    duration: 120
  })
  var show2 = await Show.create({
    name: 'Ambitious Creation',
    date: '2030/12/10',
    image: 'https://i.imgur.com/kx4YMWS.png',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Dragonkey',
    date: '2019/12/10',
    image: 'https://i.imgur.com/Xp0theU.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Heroic Tales',
    date: '2019/12/11',
    image: 'https://i.imgur.com/aOCS6Kw.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Maximum Romance',
    date: '2019/12/11',
    image: 'https://i.imgur.com/8MKmCW0.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Unknown Dream',
    date: '2019/12/11',
    image: 'https://i.imgur.com/aYhPTvf.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Sunmyth',
    date: '2019/12/11',
    image: 'https://i.imgur.com/QCHzuI5.png',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Heavenshow',
    date: '2019/12/11',
    image: 'https://i.imgur.com/TFPuTdG.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Onyxyear',
    date: '2019/12/11',
    image: 'https://i.imgur.com/cKEkM89.jpg',
    price: 12.3,
    duration: 120
  })
  await Show.create({
    name: 'Randomcat',
    date: '2019/12/11',
    image: 'https://i.imgur.com/llAZKNc.jpg',
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
  await Product.create({
    name: 'Cheesy Fries',
    image: 'https://i.imgur.com/4sbKkty.jpg',
    quantity: 200,
    price: 2.5})
  await Product.create({
    name: 'Pepperoni Pizza',
    image: 'https://i.imgur.com/qXABbMu.jpg',
    quantity: 200,
    price: 2.50})
  await Product.create({
    name: 'Buffalo Chicken Club',
    image: 'https://i.imgur.com/VJdJNUs.jpg',
    quantity: 200,
    price: 1.50})
  await Product.create({
    name: 'Oreo Mousse',
    image: 'https://i.imgur.com/us1MW6y.jpg',
    quantity: 200,
    price: 1.59})
  await Product.create({
    name: 'Chocolate Bombshell',
    image: 'https://i.imgur.com/QBlhc10.jpg',
    quantity: 200,
    price: 2.25})
  await Product.create({
    name: 'Planter\'s Punch',
    image: 'https://i.imgur.com/uTMENbX.png',
    quantity: 200,
    price: 2.25})

  printSeparator('Terminals')

  await Terminal.create({identifier: 'terminal1', password: 'password'})
  await Terminal.create({identifier: 'terminal2', password: 'pw2'})

  console.log(JSON.stringify(show1))
}
