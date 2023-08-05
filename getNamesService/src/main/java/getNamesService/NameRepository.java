package getNamesService;

import getNamesService.Name;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NameRepository extends JpaRepository<Name, Long> {
    List<Name> findByName(String name);
    void deleteByName(String name);
}
