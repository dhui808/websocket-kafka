package example.websocket.notif.example;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import lombok.extern.slf4j.Slf4j;

//Alternative to SubscribeKafkaMessageListener. Use only one of them.
@Service
@Slf4j
public class SubscribeApplicationListener implements ApplicationListener<SessionSubscribeEvent> {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
    	
    	log.info("onApplicationEvent is invoked");
    	Map map = (Map)event.getMessage().getHeaders().get("nativeHeaders");
    	List list = (List)map.get("destination");
    	String topicpath = (String)list.get(0);
    	
    	String[] s = topicpath.split("/");
    	String topic = s[s.length - 1];
    	log.info("Create message listener, topic = " + topic);
        
    	KafkaConsumer consumer = setup();
    	
    	consumeFromBeginning(consumer, topic);
    }
    
    private void consumeFromBeginning(KafkaConsumer consumer, String topic) {
        
    	consumer.subscribe(Arrays.asList(topic));
        consumer.seekToBeginning(consumer.assignment());

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

        Iterator<ConsumerRecord<String, String>> it = records.iterator();
        ConsumerRecord<String, String> record = null;
        
        if (!it.hasNext()) {
        	log.info("There is no consumer record.");
        	return;
        }
        
        record = it.next();
        log.info(record.value());
        
        String message = record.value();
        log.info("Received Json Message in groupId='" + consumer.groupMetadata().groupId() + "'," + message);

        String[] messageArray  = message.split(";");
        simpMessagingTemplate.convertAndSend("/topic/greetings/"+ messageArray[1], messageArray[0]);
        log.info("message is sent: " + message);
        
        consumer.unsubscribe();
    }

    private KafkaConsumer setup() {
 
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID()
          .toString());

        KafkaConsumer consumer = new KafkaConsumer<>(consumerProperties);
        
        return consumer;
    }
}