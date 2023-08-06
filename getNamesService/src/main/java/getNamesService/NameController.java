package getNamesService;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

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
        return ResponseEntity.ok("Busy, await end of countdown");
    }

    @GetMapping
    public List<String> getAllNames() {
        if (isWithinCountdown())
        {
            var response = new ArrayList<String>();
            response.add("Busy, await end of countdown");
            return response;
        }
        var names = nameService.getAllNames();
        return convertNamesToStringList(names);
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

    public List<String> convertNamesToStringList(List<Name> names)
    {
        return names.stream()
                .map(Name::getName)
                .collect(Collectors.toList());
    }
}
