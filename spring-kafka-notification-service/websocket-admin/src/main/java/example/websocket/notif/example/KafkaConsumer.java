package example.websocket.notif.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "mytopic", groupId = "mygroup")
    public void consume(String message) {
        String[] messageArray  = message.split(";");
        simpMessagingTemplate.convertAndSend("/topic/greetings/"+ messageArray[1], messageArray[0]);
        System.out.println("message = " + message);
    }
}