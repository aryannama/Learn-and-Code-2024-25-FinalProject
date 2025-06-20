package itt.lnc.news_aggregation;

import itt.lnc.news_aggregation.external_server.NewsFetcher;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewsAggregationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsAggregationApplication.class, args);
//        ApplicationContext context = SpringApplication.run(NewsAggregationApplication.class, args);
//        NewsFetcher fetcher = context.getBean(NewsFetcher.class);
//
//        fetcher.fetchNews();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
