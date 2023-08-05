package getNamesService;

import getNamesService.Name;
import getNamesService.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NameService {

    @Autowired
    private NameRepository nameRepository;

    public void deleteName(String name) {
        nameRepository.deleteByName(name);
    }
    public List<Name> getAllNames() {
        return nameRepository.findAll();
    }

    public void addName(String name) {
        Name newName = new Name();
        newName.setName(name);
        nameRepository.save(newName);
    }
}
