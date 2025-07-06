package itt.lnc.news_aggregation_client.dto;

import lombok.Data;

@Data
public class UserReaction {
    private boolean liked;
    private boolean disliked;
    private boolean reported;
}
