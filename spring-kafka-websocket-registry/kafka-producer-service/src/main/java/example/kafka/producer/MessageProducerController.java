package example.kafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import example.kafka.producer.KafkaProducerConfig.MessageProducer;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageProducerController {

	@Autowired
	private ApplicationContext context;
	
    @GetMapping("/producer/{userId}/{message}")
    public ResponseEntity<String> postData(@PathVariable("message") String message, @PathVariable("userId") int userId){
        
        MessageProducer producer = context.getBean(MessageProducer.class);
        producer.sendMessages(message, userId);
        
        //Deliberate delay to let listener consume produced message before main thread stops
        try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // delete topic
        KafkaAdmin kafkaAdmin = context.getBean(KafkaAdmin.class);
        Map<String, Object> props = kafkaAdmin.getConfigurationProperties();
        AdminClient ac = AdminClient.create(props);
        List<String> list = new ArrayList<String>();
        list.add(Integer.toString(userId));
        
        ac.deleteTopics(list);
        kafkaAdmin = null;
        
        return ResponseEntity.ok("ok");
    }
}
