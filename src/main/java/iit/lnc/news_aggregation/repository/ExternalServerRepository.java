package iit.lnc.news_aggregation.repository;

import iit.lnc.news_aggregation.model.ExternalServer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalServerRepository extends JpaRepository<ExternalServer,Long> {
}
