package tw.fengqing.spring.hello;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
// import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class CommandLineApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(CommandLineApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		// 根據 application.properties 裡的配置來決定 WebApplicationType
		// SpringApplication.run(CommandLineApplication.class, args);
	}
}
