package itt.lnc.news_aggregation.external_server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsFetcherScheduler {

    private final NewsFetcher newsFetcher;

//    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)
//    public void scheduleNewsFetchers() {
//        log.info("Starting news fetcher scheduler...");
//        newsFetcher.fetchNews();
//    }
}
