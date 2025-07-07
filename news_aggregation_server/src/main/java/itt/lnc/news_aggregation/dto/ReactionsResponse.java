package itt.lnc.news_aggregation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactionsResponse {
    private boolean liked = false;
    private boolean disliked = false;
    private boolean reported = false;
}
