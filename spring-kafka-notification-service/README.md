## Spring Boot, Apache Kafka, WebSocket - Part 1

### Architecture
	Message Producer: using Kafka Producer API (instead of Spring for Apache Kafka).
	The topic mytopic is created programmatically and not removed after the message is sent.

	Message Consumer: using Spring for Apache Kafka. Annotation @KafkaListener on method. The listener is created with Spring annotation.
	
	WebSocket needs to connect with server before the message producer publishes a message to the topic.
	
### UI	
	This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.0.3.

	npm i

### Start Kafka with the -d option to run in detached mode

	docker-compose up -d

	http://localhost:3040
	
### Build

	ng build
		
### Development server

	ng serve --serve

### Connect to WebSocket Server

	http://localhost:4200
	
	Topic to connect on:
	123

	where topic 123 matches the userId in the producer REST API URL.
	
### Start NotificationWebsocketApplication

### Start KafkaProducerApplication

### Send a message
	curl localhost:9080/producer/123/sayhello-123
	
	where userId 123 matches the part of WebSocket topic path.
	 
