package com.captech.VirtualThreadsTomcat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



@SpringBootApplication
public class VirtualThreadsTomcatApplication {

	@Bean
	public AsyncTaskExecutor applicationTaskExecutor() {
		// enable async servlet support
		ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
		return new TaskExecutorAdapter(executorService::execute);
	}

	@Bean
	public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
		return protocolHandler -> {
			protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
		};
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	public static void main(String[] args) {
		SpringApplication.run(VirtualThreadsTomcatApplication.class, args);
	}

}
