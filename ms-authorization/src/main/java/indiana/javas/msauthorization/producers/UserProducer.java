package indiana.javas.msauthorization.producers;


import indiana.javas.msauthorization.entities.User;
import indiana.javas.msauthorization.entities.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessage(User user) {
        var emailDto = new EmailDTO(
                user.getId(),
                user.getEmail(),
                "Dados alterados com sucesso!",
                String.format("Olá %s,\n\nSeus dados foram alterados recentemente, " +
                        "se você solicitou essa alteração, tudo tranquilo, " +
                        "se não foi você, entre em contato!", user.getFirstName())
        );

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
