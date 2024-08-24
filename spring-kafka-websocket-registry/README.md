## Spring Boot, Spring for Apache Kafka, WebSocket - Part 4

### Architecture
	Message Producer: using Spring for Apache Kafka.
	MessageProducer and the topic are created dynamically.
	Topic is deleted after the message is sent.

	Message Consumer: using Spring for Apache Kafka. KafkaMessageListener implements Spring MessageListener.
	
	ConcurrentMessageListenerContainer is created and started dynamically.
	
	KafkaMessageListener is created from the container and the topic parameter.
	
	Once the message is received and published to WebSocket topic, the container is stopped.
	
	WebSocket can connect with server either before or after the message producer publishes a message to the topic.
	
	SessionSubscribeEvent is raised when a new WebSocket client using a Simple Messaging Protocol (e.g. STOMP) sends a subscription request.

	This is the right way of dynamically starting and stopping Kafka listener container.
	
	https://stackoverflow.com/questions/69160889/spring-kafka-close-the-container-and-read-the-messages-from-specific-offset-wit
	
	https://bikas-katwal.medium.com/start-stop-kafka-consumers-or-subscribe-to-new-topic-programmatically-using-spring-kafka-2d4fb77c9117

### How to make sure offset is always 0?

	ConcurrentMessageListenerContainer<String, String> container =
                this.factory.createContainer(new TopicPartitionOffset(topic, 0, 0L, false));
	
	Note, TopicPartitionOffset needs to be created with relativeToCurrent = false (the fourth parameter in the constructor).
	  	
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
	100

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
