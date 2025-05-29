# 📨 Message-Broker

**MessageBroker** is a Spring Boot project that showcases sending and receiving messages through multiple brokers:

- 🟢 Amazon SQS (real)
- 🟢 ElasticMQ (SQS emulator for local dev)
- 🟢 ActiveMQ (JMS support)

Brokers are **dynamically selected per request**, making this ideal for demos, testing, and development.

---

## 🚀 Features

- Dynamically choose broker per request (`sqs`, `elasticmq`, `activemq`)
- SQS SDK v2 integration with AWS IAM/STS support
- Local development with Docker (ElasticMQ + ActiveMQ)
- Clean, modular architecture
- Swagger UI for testing

---

## 🧰 Prerequisites

- ✅ Java 21+
- ✅ Maven
- ✅ Docker & Docker Compose
- ✅ AWS CLI (for real SQS only)

---

## ▶️ Run Local Brokers

This project includes a pre-configured `docker-compose.yml`.

### Start ElasticMQ & ActiveMQ:

```bash
./mvnw clean install
```
## 🛠️ Run the Spring Boot App
```bash
docker-compose up
```
or with any IDE.
You can configure the seeder broker in application.properties:
```properties
message.seeder.broker=sqs   # or activemq, elasticmq
spring.profiles.active=dev  # restrict this to dev only
```
---
### 🌐 Test with Swagger UI
```bash
http://localhost:8080/swagger-ui.html
```
---
### ☁️ Using Real Amazon SQS
1. Create a queue in AWS Console
2. Set these in application.properties:
```properties
sqs.use-local=false
sqs.aws.region=us-east-1 # or your region
```
3. Configure credentials using `aws configure` or environment variables
4. Use Swagger or cURL with `"broker": "sqs"`