package Microservices.GatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class GatewayController {

    @Autowired
    private RestTemplate restTemplate;

    private final String GETNAMES_SERVICE_URL = "http://getnames-service:8080/api/names";

    @GetMapping("/names")
    public ResponseEntity<String> getAllNames() {
        ResponseEntity<String> response = restTemplate.getForEntity(GETNAMES_SERVICE_URL, String.class);
        return ResponseEntity.ok("Gateway: " + response.getBody());
    }
}
