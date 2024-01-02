## Spring Boot, Spring for Apache Kafka, WebSocket - Part 2

### Architecture
	Message Producer: using Spring for Apache Kafka.
	MessageProducer and the topic are created dynamically.
	Topic is deleted after the message is sent.

	Message Consumer: using Spring for Apache Kafka. Annotation @KafkaListener on method. The listener is prototype bean (stateful bean) and is created dynamically.
	
	Once the message producer deletes the topic, the message consumer sees some warning messages.
	
	WebSocket needs to connect with server before the message producer publishes a message to the topic.

	Override configureClientInboundChannel to create Kafka listener dynamically. (not using ApplicationListener or @EventListener)
	
	Note:
	The approach used in spring-kafka-websocket-registry is the right way for dynamically starting and stopping Kafka listener container.
	
### Start Kafka with the -d option to run in detached mode
	docker-compose up -d

	http://localhost:3040
	
### WebsocketExample

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.0.3.

	npm i

	
### Build

	ng build
		
### Development server

	ng serve --open

### Connect to WebSocket Server

	http://localhost:4200
		
	Topic to connect on:
	123

### Start NotificationWebsocketApplication

### Start KafkaProducerApplication

### Send a message
	curl localhost:9080/producer/123/sayhello-123
	
