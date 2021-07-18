package net.prmts.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:settings.properties")
public class PropertiesSourceConfig {

}
