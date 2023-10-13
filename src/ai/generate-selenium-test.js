const usage =`
  This script is used to write automatic test cases for Selenium using ChatGPT.
  To use this project please provide URL of a page you wish to test, for example:
  node generate-selenium-test "https://localhost:8080"
`
let url = process.argv.slice(2)[0]

if (!url) {
  console.log(usage)
  process.exit()
}

// For local development - serve html
if (url === 'calculator' || url === 'login') {
  url = require('./examples/serve').serve(url)
}

// Parse page
const parser = require('./util/ai-parser')
require('./util/download-page').download(url, parser.parse)
