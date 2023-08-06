package Service.Gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class RequestController {

    @Value("${GETNAMES_SERVICE_URL:http://localhost:8081/api/names}")
    private String NAMES_DB_URL;

    @Autowired
    private RestTemplate restTemplate;
   
    @DeleteMapping("/names")
    public ResponseEntity<?> handledDelete(@RequestBody String jsonRequest) {
        return performRequest(RequestType.DELETE, jsonRequest, NAMES_DB_URL, "names-db");
    }

    @PostMapping("/names")
    public ResponseEntity<?> handlePost(@RequestBody String jsonRequest) {
        return performRequest(RequestType.POST, jsonRequest, NAMES_DB_URL, "names-db");

    }

    @GetMapping("/names")
    public ResponseEntity<?> handleGet(@RequestBody String jsonRequest) {
        return performRequest(RequestType.GET, jsonRequest, NAMES_DB_URL, "names-db");
    }


    public enum RequestType {
        GET, POST, DELETE, PUT // add other methods as needed
    }

    public ResponseEntity<?> performRequest(RequestType type, String requestBody, String route, String serviceName) {
        // Create HTTP headers and set content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HTTP entity with the requestBody and headers
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = null;

        try {
            switch (type) {
                case GET:
                    response = restTemplate.exchange(route, HttpMethod.GET, entity, String.class);
                    break;
                case POST:
                    response = restTemplate.exchange(route, HttpMethod.POST, entity, String.class);
                    break;
                case DELETE:
                    response = restTemplate.exchange(route, HttpMethod.DELETE, entity, String.class);
                    break;
                case PUT:
                    response = restTemplate.exchange(route, HttpMethod.PUT, entity, String.class);
                    break;
                default:
                    return response;
            }
        }
        catch(Exception e) {
            // on failure, requestNewUrl, eventually an address to an orchestration managed service
            return performRequest(type, requestBody, requestNewURL(serviceName), serviceName);
        }
        return response;
    }


    private String requestNewURL(String serviceName) throws IllegalArgumentException {
        if ("names-db".equals(serviceName)) {
            return NAMES_DB_URL;
        } else {
            throw new IllegalArgumentException("Bad service name");
        }
    }

}

