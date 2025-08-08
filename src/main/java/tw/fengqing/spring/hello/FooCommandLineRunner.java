package tw.fengqing.spring.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
// 設置執行順序，數字越小，執行越早
@Order(1)
@Slf4j
public class FooCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("Foo");
    }
}
