module.exports = {
  parse: async (url, content) => {
    const {Configuration, OpenAIApi} = require('openai');
    const fs = require('fs')

    const configuration = new Configuration({
      organization: '', // TODO add key
      apiKey: '', // TODO add key
    });

    const openai = new OpenAIApi(configuration);
    const messages = [
      {
        role: 'system',
        content: 'Write Selenium test in json format so I can import it in Selenium IDE. User will supply html page that is located on ' + url
      },
      {
        role: 'user',
        content: content.replace(/\s/g, '')
      }
    ];

    console.log('Sending request to ChatGPT')
    openai.createChatCompletion({
      model: 'gpt-3.5-turbo', messages
    })
      .then((response) => {
        const file = Math.random() + ".side"

        fs.writeFileSync(file, response.data.choices[0].message.content)

        console.log('Stored response from ChatGPT in file ' + file)

        console.log('Running selenium test case.')
        //const selenium = require('selenium-side-runner')
        //selenium.run(file)
      })
      .catch(error => console.log('There was an error while contacting ChatGPT: ' + error))
  }
}
