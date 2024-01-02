package example.websocket.notif.example;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KafkaMessageListener  {
    private final String id;

    private final String topic;

	private SimpMessagingTemplate simpMessagingTemplate;

	private ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory;
	
    public String getId() {
        return this.id;
    }

    public String getTopic() {
        return this.topic;
    }
	public KafkaMessageListener(String id, String topic, SimpMessagingTemplate simpMessagingTemplate,
			ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory) {
		this.id = id;
        this.topic = topic;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.concurrentKafkaListenerContainerFactory = concurrentKafkaListenerContainerFactory;
	}
	
	@KafkaListener(containerFactory = "concurrentKafkaListenerContainerFactory", topicPartitions =
	{ @TopicPartition(topic = "#{__listener.topic}", partitions = { "0", "1" },
	     partitionOffsets = @PartitionOffset(partition = "*", initialOffset = "0"))
	})
	public void listenGroupGreeting( @Headers Map<String, String> header,
    		@Header(KafkaHeaders.OFFSET) long os,
    		@Header(KafkaHeaders.RECEIVED_PARTITION) int receivedpartition,
    		@Header(KafkaHeaders.GROUP_ID) String group,
    		String message) {

        log.info("Received Json Message in groupId='" + group + "'," + message + ", offset=" + os + ", receivedpartition=" + receivedpartition);
//        if (null == simpMessagingTemplate) {
//        	System.out.println("simpMessagingTemplate is null. do nothing.");
//        	return;
//        }
        String[] messageArray  = message.split(";");
        simpMessagingTemplate.convertAndSend("/topic/greetings/"+ messageArray[1], messageArray[0]);
        log.info("message is sent: " + message);
        
        //concurrentKafkaListenerContainerFactory.
    }
}
