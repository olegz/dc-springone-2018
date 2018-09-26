package com.example.demo.stream.dc;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.support.GenericMessage;

public class DemoApplicationTests {

	@Test
	public void test() {
		ApplicationContext context = SpringApplication.run(new Class[] {DemoApplication.class,
				TestChannelBinderConfiguration.class}, new String[] {
						"--spring.cloud.stream.function.definition=upperCase|reverse"});
		InputDestination source = context.getBean(InputDestination.class);
		OutputDestination result = context.getBean(OutputDestination.class);
		source.send(new GenericMessage<byte[]>("{\"name\":\"bob\", \"id\":123}".getBytes()));
		byte[] payload = result.receive().getPayload();
		System.out.println(new String(payload));
	}
}
