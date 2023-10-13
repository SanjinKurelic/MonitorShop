module.exports = {
  download: async (url, callback) => {
    const axios = require('axios')

    axios.get(url)
      .then((response) => callback(url, response.data))
      .catch((error) => console.log('Error while downloading the page: ' + error))
  }
}
