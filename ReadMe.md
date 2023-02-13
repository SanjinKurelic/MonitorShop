# Monitor Shop

Monitor Shop is a web shop for buying monitors. This is a demo application for Selenium automated test presentation.

Implemented features are:

- user can add monitor to cart
- user can change quantity of item in cart

## Getting started:

Frontend is written in Svelte, and automatic tests are written in Java 17.

Running the project will require Java, NPM and Docker installed on the system:

- Java/JDK 17+
- Node.js and NPM
- Docker

## Running

Firstly, frontend should be started, so we can fire test cases against it. To start frontend, open the terminal inside `src/frontend/` directory and run this command:
> npm run dev

After that, you will be able to start TestNG cases with Maven:
> .\mvnw clean test

There are two test cases:
- Selenium with TestContainers (require Docker on host system)
- Selenium with local driver (require Chrome version 110 on host system)

## Technologies:

- Selenium
- Selenium Fluent
- Apache Http Client 5
- Constretto
- Lombok
- MapStruct
- TestContainers
- TestNG
- Socks 5 proxy
- Svelte
