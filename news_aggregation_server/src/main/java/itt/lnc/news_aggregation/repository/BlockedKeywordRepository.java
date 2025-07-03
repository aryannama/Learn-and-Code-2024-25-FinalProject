package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.BlockedKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedKeywordRepository extends JpaRepository<BlockedKeyword, Long> {
}
