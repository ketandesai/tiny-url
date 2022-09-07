# Getting Started
A Url Shortener example, using Spring Boot and Redis.
I created this app to learn more about Spring Boot and Redis, as
well as basic System and API Design.

Along the way I learnt about:
1. API Design
2. Key Generation
3. Hashing
4. Encoding/Decoding

## To Run
To get up and running in development mode

    `docker-compose up`


## 💡 Motivation

I wanted to learn more about System Design and a URL Shortener is one of the most basic System Design problems
to tackle.

There are many URL shortener apps available today. The goal was to learn from some of the best and apply them to this application and create a well document API.

## 💻 Technologies

-This project was build using:
- **[Spring Boot](https://spring.io/projects/spring-boot/)**

- **[Redis](https://redis.io)**


## 😢 Challenges

- There are many URL shortening apps available, and they seem like very simple applications.  However  one of the biggest challenges was understanding how to generate a small key to represent a large url.  And to make the process Scalable.  

## 🏎 Roadmap

- [ ] Make application deployable as a Microservice, potentially using Kubernetes.
- [ ] Make the app scalable, spin up multiple instances if traffic peaks.