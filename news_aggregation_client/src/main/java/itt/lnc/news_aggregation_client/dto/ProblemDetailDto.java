package itt.lnc.news_aggregation_client.dto;

import lombok.Data;

@Data
public class ProblemDetailDto {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}
