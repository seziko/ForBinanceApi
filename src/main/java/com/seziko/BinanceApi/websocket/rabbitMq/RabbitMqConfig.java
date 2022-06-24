package com.seziko.BinanceApi.websocket.rabbitMq;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RabbitMqConfig {

        @Value("${sr.rabbit.exchange.name}")
        String exchangeName;
        @Value("${sr.rabbit.queue.name}")
        String queueName;
        @Value("${sr.rabbit.routing.name}")
        String routingKey;
        @Value("firstRoutingName")

        @Autowired
        private RabbitTemplate rabbitTemplate;


        @Bean
        public Queue queue() {
                return new Queue(queueName);
        }

        @Bean
        public DirectExchange exchange() {
                return new DirectExchange(exchangeName);
        }

        @Bean
        public Binding binding(final Queue queue, final DirectExchange directExchange) {
                return BindingBuilder.bind(queue).to(directExchange).with(routingKey);
        }
}


