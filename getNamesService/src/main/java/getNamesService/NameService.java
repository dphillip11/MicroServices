package getNamesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NameService {

    @Autowired
    private NameRepository nameRepository;

    @Transactional
    public void deleteName(String name) {
        nameRepository.deleteByName(name);
    }
    public List<Name> getAllNames() {
        return nameRepository.findAll();
    }

    @Transactional
    public void addName(String name) {
        Name newName = new Name();
        newName.setName(name);
        nameRepository.save(newName);
    }
}
