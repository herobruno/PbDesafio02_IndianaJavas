package indiana.javas.msnotification.configs;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RabbitMQConfigTest {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Test
    public void testQueue() {
        String expectedQueueName = "default.email";
        Queue queue = rabbitMQConfig.queue();
        assertEquals(expectedQueueName, queue.getName());
        assertTrue(queue.isDurable());
    }
}
