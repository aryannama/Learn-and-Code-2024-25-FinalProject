package itt.lnc.news_aggregation.repository;

import itt.lnc.news_aggregation.model.UserReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReactionRepository extends JpaRepository<UserReaction, Long> {
    Optional<UserReaction> findByUserIdAndArticleId(Long userId, Long id);

    List<UserReaction> findByUserIdAndLikedTrue(Long userId);
}
