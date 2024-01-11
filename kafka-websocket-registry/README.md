## Spring Boot, Apache Kafka, WebSocket

### Architecture
	Message Producer: using Apache Kafka Producer. Spring for Apache Kafka is not used.
	MessageProducer and the topic are created dynamically.
	Topic is deleted after the message is sent.

	Message Consumer: using Apache Kafka Consumer
	
	Kafka consumer subscribes the topic dynamically when a SessionSubscribeEvent is received. 
	
	Once the message is received and published to WebSocket topic, Kafka custumer unsubscribes the topic.
	
	WebSocket can connect with server either before or after the message producer publishes a message to the topic.
	
	SessionSubscribeEvent is raised when a new WebSocket client using a Simple Messaging Protocol (e.g. STOMP) sends a subscription request. This part of Sprig WebSocket.

### How to make sure offset is always 0?

	KafkaConsumer.seekToBeginning
	  	
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
	curl localhost:9080/producer/100/sayhello-100
	

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
