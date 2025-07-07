package itt.lnc.news_aggregation_client.exception;

import itt.lnc.news_aggregation_client.dto.ProblemDetailDto;

public class ApiException extends RuntimeException {
    public ApiException(ProblemDetailDto problemDetailDto) {
        super(problemDetailDto.getDetail());
    }
}
