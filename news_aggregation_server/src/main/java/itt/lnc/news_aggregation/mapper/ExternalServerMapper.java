package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.ExternalServerDto;
import itt.lnc.news_aggregation.model.ExternalServer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalServerMapper {

    private final ModelMapper modelMapper;

    public ExternalServer toEntity(ExternalServerDto dto) {
        return modelMapper.map(dto, ExternalServer.class);
    }

    public ExternalServerDto toDto(ExternalServer entity) {
        return modelMapper.map(entity, ExternalServerDto.class);
    }
}
