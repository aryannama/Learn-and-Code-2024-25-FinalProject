package itt.lnc.news_aggregation.model;

import itt.lnc.news_aggregation.enums.ServerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String apiKey;
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    private ServerStatus status;

    private LocalDateTime lastAccessed;
}
