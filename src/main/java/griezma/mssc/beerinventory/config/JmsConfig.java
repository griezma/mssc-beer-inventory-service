package griezma.mssc.beerinventory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String INVENTORY_EVENT_QUEUE = "beer-inventory-event";

    @Bean
    MessageConverter jsonConverter(ObjectMapper om) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(om);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_JsonType");
        return converter;
    }
}
