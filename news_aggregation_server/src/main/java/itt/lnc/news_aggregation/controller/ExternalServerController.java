package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.ApiKeyUpdateRequest;
import itt.lnc.news_aggregation.dto.ExternalServerDto;
import itt.lnc.news_aggregation.service.ExternalServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/external-servers")
public class ExternalServerController {

    private final ExternalServerService externalServerService;

    @GetMapping
    public ResponseEntity<List<ExternalServerDto>> getAllServers() {
        return ResponseEntity.ok(externalServerService.getAllServers());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateServerApiKey(@PathVariable Long id, @RequestBody ApiKeyUpdateRequest request) {
        externalServerService.updateApiKey(id, request.getApiKey());
    }

}
