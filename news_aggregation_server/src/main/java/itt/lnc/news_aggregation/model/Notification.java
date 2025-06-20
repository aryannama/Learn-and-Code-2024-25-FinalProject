package itt.lnc.news_aggregation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "article_id"}))
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private boolean isRead;
}
