package itt.lnc.news_aggregation_client;

import itt.lnc.news_aggregation_client.menu.HomeMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NewsAggregationClientApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NewsAggregationClientApplication.class, args);
        HomeMenu homeMenu = context.getBean(HomeMenu.class);

        homeMenu.displayMenu();
    }

}
