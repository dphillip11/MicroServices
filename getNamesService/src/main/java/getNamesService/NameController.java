package getNamesService;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/names")
public class NameController {

    private static final long DELAY_IN_MS = 5 * 1000;
    private final AtomicLong lastProcessedTimestamp = new AtomicLong(0);

    @Autowired
    private NameService nameService;

    private synchronized boolean isWithinCountdown(){
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastProcessedTimestamp.get();
        if (timeElapsed < DELAY_IN_MS) {
            return true;
        }
        else
        {
            lastProcessedTimestamp.set(currentTime);
            return false;
        }
    }

    private ResponseEntity<?> busyResponse()
    {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Busy, await end of countdown");
    }

    @GetMapping
    public ResponseEntity<?> getAllNames() {
        if (isWithinCountdown())
        {
            return busyResponse();
        }
        var names = nameService.getAllNames();
        return ResponseEntity.ok(names);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteName(@RequestBody Name nameToDelete) {
        if(isWithinCountdown())
        {
            return busyResponse();
        }
        var name = nameToDelete.getName();
        nameService.deleteName(name);
        return ResponseEntity.ok("Name " + name + " deleted successfully");
    }

    @PostMapping
    public ResponseEntity<?> addName(@RequestBody Name newName) {
        if(isWithinCountdown())
        {
            return busyResponse();
        }
        nameService.addName(newName.getName());
        return ResponseEntity.ok("Name " + newName.getName() + " added successfully");
    }

}
