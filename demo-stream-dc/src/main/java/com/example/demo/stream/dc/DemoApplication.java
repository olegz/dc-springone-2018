package com.example.demo.stream.dc;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;

/**
 *
 * @author Oleg Zhurakousky
 *
 */
@EnableBinding(Processor.class)
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class,
				"--spring.cloud.stream.function.definition=uppercase|reverse");
	}



	@Bean
	public Function<Person, String> uppercase() {
		return x -> {
			System.out.println("Received: " + x);
			return x.toString().toUpperCase();
			};
	}


	@Bean
	public Function<Flux<String>, Flux<String>> reverse() {
		return x -> x
				.doOnNext(System.out::println)
				.map(v -> new StringBuilder(v).reverse().toString());
	}
//	"--spring.cloud.stream.function.definition=uppercase",
//	"--management.endpoints.web.exposure.include=bindings"

}
