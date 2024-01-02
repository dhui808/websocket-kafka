package example.websocket.notif.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.TopicPartitionOffset;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

//Alternative to SubscribeKafkaMessageListener. Use only one of them.
@Service
public class SubscribeApplicationListener implements ApplicationListener<SessionSubscribeEvent> {

	private ApplicationContext context;
    private final ConcurrentKafkaListenerContainerFactory<String, String> factory;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    public SubscribeApplicationListener(ApplicationContext context,
    		ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        this.context = context;
        this.factory = factory;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
    	
    	System.out.println("onApplicationEvent is invoked");
    	Map map = (Map)event.getMessage().getHeaders().get("nativeHeaders");
    	List list = (List)map.get("destination");
    	String topicpath = (String)list.get(0);
    	
    	String[] s = topicpath.split("/");
    	String topic = s[s.length - 1];
    	System.out.println("Create message listener, topic = " + topic);
        
        // new code
        ConcurrentMessageListenerContainer<String, String> container =
                this.factory.createContainer(new TopicPartitionOffset(topic, 0, 0L, false));
        KafkaMessageListener listener = new KafkaMessageListener("greetingGrp", topic, simpMessagingTemplate, container);
        
        container.getContainerProperties().setMessageListener(listener);
        container.getContainerProperties().setGroupId("greetingGrp");
        container.setBeanName(topic + ".container");
        container.getContainerProperties().setIdleEventInterval(3000L); 
        container.start();
    }
}