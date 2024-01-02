package example.websocket.notif.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.extern.slf4j.Slf4j;

//Alternative to SubscribeKafkaMessageListener. Use only one of them.
@Service
@Slf4j
public class SubscribeApplicationListener implements ApplicationListener<SessionSubscribeEvent> {

	private ApplicationContext context;
    
    @Autowired
    public SubscribeApplicationListener(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
    	
    	log.info("onApplicationEvent is invoked");
    	Map map = (Map)event.getMessage().getHeaders().get("nativeHeaders");
    	List list = (List)map.get("destination");
    	String topicpath = (String)list.get(0);
    	
    	String[] s = topicpath.split("/");
    	
    	log.info("Create message listener, topic = " + s[s.length - 1]);
    	// Create message listener
    	KafkaMessageListener listener = context.getBean(KafkaMessageListener.class, "greetingGrp", s[s.length - 1]);
        
        log.info("Done creating message listener");
    }
}