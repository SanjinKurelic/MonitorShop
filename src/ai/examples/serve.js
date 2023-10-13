module.exports = {
  serve: (url) => {
    const express = require('express')

    const app = express()

    app.get('/', function (req, res) {
      res.sendFile(__dirname + '/pages/' + url + '.html')
    })
    app.listen(8080)

    return 'http://localhost:8080/'
  }
}
