package itt.lnc.news_aggregation.controller;

import itt.lnc.news_aggregation.dto.ExternalServerDTO;
import itt.lnc.news_aggregation.service.ExternalServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external-servers")
public class ExternalServerController {

    private final ExternalServerService externalServerService;

    public ExternalServerController(ExternalServerService externalServerService) {
        this.externalServerService = externalServerService;
    }

    @GetMapping
    public ResponseEntity<List<ExternalServerDTO>> getAllServers() {
        return ResponseEntity.ok(externalServerService.getAllServers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExternalServerDTO> updateServerApiKey(@PathVariable Long id, String apiKey) {
        return ResponseEntity.ok(externalServerService.updateApiKey(id, apiKey));
    }


}
