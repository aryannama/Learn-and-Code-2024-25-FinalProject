package itt.lnc.news_aggregation.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "notification_configurations", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category_id"}))
public class NotificationConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean isEnabled;

    private List<String> keywords;
}
