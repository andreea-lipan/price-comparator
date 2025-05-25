package accesa.pricecomparatorbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PriceComparatorBeApplication {

    public static void main(String[] args) {
        System.out.println("Hello!!");
        SpringApplication.run(PriceComparatorBeApplication.class, args);
    }

}
