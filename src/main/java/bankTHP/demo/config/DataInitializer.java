package bankTHP.demo.config;

import bankTHP.demo.entity.Book;
import bankTHP.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Book(null, "978-01", "The Big Boss", "Hideo Kojima"));
            repository.save(new Book(null, "978-02", "Post-Apocalyptic Noir", "BANK!!!"));
            repository.save(new Book(null, "978-03", "Java 25 Mastery", "J"));
            System.out.println(">> Vault seeded with 3 books for the demo!");
        }
    }
}