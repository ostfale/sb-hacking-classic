package de.ostfale.book.sbhackingclassic.bootstrap;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTraceConfig {

    @Bean
    public HttpTraceRepository traceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
