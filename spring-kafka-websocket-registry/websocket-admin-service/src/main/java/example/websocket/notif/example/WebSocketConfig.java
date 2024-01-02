package example.websocket.notif.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private ApplicationContext context;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/my-websocket").setAllowedOrigins("http://localhost:4200");
	}

	// configureClientInboundChannel is alternatively replaced by SubscribeKafkaMessageListener
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                
//                StompCommand command = accessor.getCommand();
//                System.out.println("command=" + command.toString());
//                
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                	
//                } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                    
//                	String topicpath = accessor.getDestination();
//                	String[] s = topicpath.split("/");
//                	
//                	System.out.println("Create message listener, topic = " + s[s.length - 1]);
//                	// Create message listener
//                	KafkaMessageListener listener = context.getBean(KafkaMessageListener.class, "greetingGrp", s[s.length - 1]);
//                    
//                    System.out.println("Done creating message listener");
//                }
//                return message;
//            }
//        });
//    }
}
