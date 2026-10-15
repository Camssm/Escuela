package com.app.Cursos.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

	public static final String EXCHANGE = "alumno.exchange";
	public static final String QUEUE = "alumno.eventos.queue";
	public static final String ROUTING_KEY = "alumno.eventos.routingkey";

	public static final String ADMIN_EXCHANGE = "administracion.exchange";
	public static final String ADMIN_QUEUE = "administracion.eventos.queue";
	public static final String ADMIN_ROUTING_KEY = "administracion.eventos.routingkey";

	@Bean
	public Queue alumnoQueue() {
		return new Queue(QUEUE, true);
	}

	@Bean
	public TopicExchange alumnoExchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding alumnoBinding(@Qualifier("alumnoQueue") Queue queue,
			@Qualifier("alumnoExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public Queue administracionQueue() {
		return new Queue(ADMIN_QUEUE, true);
	}

	@Bean
	public TopicExchange administracionExchange() {
		return new TopicExchange(ADMIN_EXCHANGE);
	}

	@Bean
	public Binding administracionBinding(@Qualifier("administracionQueue") Queue queue,
			@Qualifier("administracionExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ADMIN_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}