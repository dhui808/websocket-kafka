## Spring Boot, Spring for Apache Kafka, WebSocket - Part 3

### Architecture
	Message Producer: using Spring for Apache Kafka.
	MessageProducer and the topic are created dynamically.
	Topic is deleted after the message is sent.

	Message Consumer: using Spring for Apache Kafka. Annotation @KafkaListener on method. The listener is prototype bean (stateful bean) and is created dynamically.
		
	Once the message producer deletes the topic, the message consumer sees some warning messages.
	
	SessionSubscribeEvent is raised when a new WebSocket client using a Simple Messaging Protocol (e.g. STOMP) sends a subscription request.

	Using SessionSubscribeEvent to create Kafka listener dynamically.
	
	Use either SubscribeKafkaMessageListener (uncomment code)or
	SubscribeApplicationListener.
	
	Note:
	The approach used in spring-kafka-websocket-registry is the right way for dynamically starting and stopping Kafka listener container.
	
### Start Kafka with the -d option to run in detached mode

	docker-compose up -d

	http://localhost:3040
	
### Websocket UI

	Angular 16.0.3.

	npm i

	
### Build UI

	ng build
		
### Start Angular Development server

	ng serve --open

### Connect to WebSocket Server

	http://localhost:4200
		
	Topic to connect on:
	123

	Click "Connect to websocket" button.
	
### Start NotificationWebsocketApplication

### Start KafkaProducerApplication

### Send a message
	curl localhost:9080/producer/123/sayhello-123
	

### SessionSubscribeEvent
	SessionSubscribeEvent.getMessage ->
	GenericMessage 
	[payload = byte[0], headers = {
	        simpMessageType = SUBSCRIBE,
	        stompCommand = SUBSCRIBE,
	        nativeHeaders = {
	            id = [sub - 0],
	            destination = [/topic/greetings / 123]
	        },
	        simpSessionAttributes = {},
	        simpHeartbeat = [J @ 64d2d30f, simpSubscriptionId = sub - 0, simpSessionId = 3c047ff5 - dd69 - c3af - ba93 - 920d49df3388, simpDestination = /topic/greetings / 123
	    }
	]
