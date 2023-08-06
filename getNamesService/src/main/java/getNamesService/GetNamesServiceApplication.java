package getNamesService;

import java.util.List;
import getNamesService.Name;
import getNamesService.NameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GetNamesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetNamesServiceApplication.class, args);
	}
	public void PrintName(String name)
	{
		System.out.println(name);
	}

	public void PrintAllNames(NameRepository repository)
	{
		// Fetch and log the names
		List<Name> fetchedNames = repository.findAll();
		fetchedNames.forEach(name -> { PrintName(name.getName());});
	}

	public void RemoveAllByName(String name, NameRepository repository)
	{
		// Fetch by name and delete
		List<Name> namesToDelete = repository.findByName(name);
		repository.deleteAll(namesToDelete);
	}

	public void AddName(String name, NameRepository repository)
	{
		repository.save(new Name(name));
	}

}
