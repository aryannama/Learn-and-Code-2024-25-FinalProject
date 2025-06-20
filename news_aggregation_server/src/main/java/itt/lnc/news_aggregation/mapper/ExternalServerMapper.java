package itt.lnc.news_aggregation.mapper;

import itt.lnc.news_aggregation.dto.ExternalServerDTO;
import itt.lnc.news_aggregation.model.ExternalServer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExternalServerMapper {

    private final ModelMapper modelMapper;

    public ExternalServer toEntity(ExternalServerDTO dto) {
        return modelMapper.map(dto, ExternalServer.class);
    }

    public ExternalServerDTO toDto(ExternalServer entity) {
        return modelMapper.map(entity, ExternalServerDTO.class);
    }
}
