package itt.lnc.news_aggregation_client;

import itt.lnc.news_aggregation_client.menu.MenuContext;
import itt.lnc.news_aggregation_client.utils.ConsoleUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsAggregationClientApplication implements CommandLineRunner {

    private final MenuContext menuContext;

    public NewsAggregationClientApplication(MenuContext menuContext) {
        this.menuContext = menuContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(NewsAggregationClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ConsoleUtil.printHeader("Welcome to the News Aggregator application");
        menuContext.start();
    }
}
