package example.kafka.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MessageProducerController {
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
    @GetMapping("/producer/{userId}/{message}")
    public ResponseEntity<String> postData(@PathVariable("message") String message, @PathVariable("userId") int userId){
        
        // 1) INITIALISATION OF CONNECTION PROPERTIES
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 2) INITIALISATION OF THE PRODUCER
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 3) CREATION, SENDING AND CLOSING THE CONNECTION TO THE KAFKA SERVER
        ProducerRecord<String, String> producerRecord =	new ProducerRecord<>(Integer.toString(userId), message + ";" + userId);
        producer.send(producerRecord);
        producer.flush();
        producer.close();
        
        //Deliberate delay to let listener consume produced message before main thread stops
        try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // delete topic
        // initialize admin client
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000);
        props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 1000);
        AdminClient admin = AdminClient.create(props);
        AdminClient ac = AdminClient.create(props);
        List<String> list = new ArrayList<String>();
        list.add(Integer.toString(userId));
        
        ac.deleteTopics(list);
        
        return ResponseEntity.ok("ok");
    }
}
