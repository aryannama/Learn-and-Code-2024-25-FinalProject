package itt.lnc.news_aggregation.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "notification_keywords",
            joinColumns = @JoinColumn(name = "notification_configuration_id")
    )
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();
}
