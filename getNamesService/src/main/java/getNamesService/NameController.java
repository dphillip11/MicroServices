package getNamesService;

import java.util.stream.Collectors;

import getNamesService.Name;
import getNamesService.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/api/names")
public class NameController {

    @Autowired
    private NameService nameService;

    @GetMapping
    public List<String> getAllNames() {
        var names = nameService.getAllNames();
        return convertNamesToStringList(names);
    }

    @DeleteMapping("/{name}")
    @Transactional
    public ResponseEntity<?> deleteName(@PathVariable String name) {
        System.out.println("delete mapping called");
        nameService.deleteName(name);
        return ResponseEntity.ok("Name " + name + " deleted successfully");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addName(@RequestBody String name) {
        nameService.addName(name);
        return ResponseEntity.ok("Name " + name + " added successfully");
    }

    public List<String> convertNamesToStringList(List<Name> names)
    {
        return names.stream()
                .map(Name::getName)
                .collect(Collectors.toList());
    }
}
