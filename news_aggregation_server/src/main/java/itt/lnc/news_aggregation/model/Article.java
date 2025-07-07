package itt.lnc.news_aggregation.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "article_category",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @Column(columnDefinition = "TEXT")
    private String articleUrl;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    private LocalDate publishedAt;

    private boolean hidden = false;
    private int reportCount = 0;
    private int likeCount = 0;
    private int dislikeCount = 0;

}
