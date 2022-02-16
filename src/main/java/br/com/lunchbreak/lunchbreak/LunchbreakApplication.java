package br.com.lunchbreak.lunchbreak;

import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class LunchbreakApplication {

    private static final Logger logger = LoggerFactory.getLogger(LunchbreakApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LunchbreakApplication.class, args);
	}

	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
        logger.info("Spring boot application running in GMT-3 timezone :"+LocalDateTime.now()); 
    }

}
