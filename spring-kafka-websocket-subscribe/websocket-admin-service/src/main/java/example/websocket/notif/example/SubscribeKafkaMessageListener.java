package example.websocket.notif.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

//Alternative to SubscribeApplicationListener. Use only one of them.
@Service
public class SubscribeKafkaMessageListener {

	private ApplicationContext context;
    
    @Autowired
    public SubscribeKafkaMessageListener(ApplicationContext context) {

        this.context = context;
    }

//    @EventListener
//    public void handleSubscribeEvent(SessionSubscribeEvent event) {
//    	
//    	System.out.println("handleSubscribeEvent is invoked");
//    	Map map = (Map)event.getMessage().getHeaders().get("nativeHeaders");
//    	List list = (List)map.get("destination");
//    	String topicpath = (String)list.get(0);
//    	
//    	String[] s = topicpath.split("/");
//    	
//    	System.out.println("Create message listener, topic = " + s[s.length - 1]);
//    	// Create message listener
//    	KafkaMessageListener listener = context.getBean(KafkaMessageListener.class, "greetingGrp", s[s.length - 1]);
//        
//        System.out.println("Done creating message listener");
//    }
}