package com.app.Alumno.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

    public static final String EXCHANGE = "administracion.exchange";
    public static final String QUEUE = "administracion.alumno.queue";
    public static final String ROUTING_KEY = "administracion.alta.routingkey";

    @Bean
    public Queue administracionQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange administracionExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding administracionBinding() {
        return BindingBuilder.bind(administracionQueue()).to(administracionExchange()).with(ROUTING_KEY);
    }
}