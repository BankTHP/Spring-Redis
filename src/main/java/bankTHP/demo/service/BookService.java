package bankTHP.demo.service;

import bankTHP.demo.entity.Book;
import bankTHP.demo.repository.BookRepository;
import com.google.gson.Gson; // Or any lightweight JSON tool
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final StringRedisTemplate redisTemplate;
    private final Gson gson = new Gson(); // Just a tool to turn object -> string

    public Book getBookByIsbn(String isbn) {
        String cacheKey = "books:" + isbn;

        // 1. Try to get the raw String from Redis
        String cachedJson = redisTemplate.opsForValue().get(cacheKey);

        if (cachedJson != null) {
            log.info(">>> SUCCESS: Found raw String in REDIS for ISBN {}", isbn);
            // Manually turn the string back into a Book object
            return gson.fromJson(cachedJson, Book.class);
        }

        // 2. Cache Miss - Go to the Vault
        log.info("--- CACHE MISS: Librarian is walking to the back for ISBN: {}", isbn);

        try { Thread.sleep(3000); } catch (InterruptedException e) { }

        Book bookFromDb = repository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

        // 3. Turn the Book into a String and save it
        String jsonToCache = gson.toJson(bookFromDb);
        redisTemplate.opsForValue().set(cacheKey, jsonToCache, Duration.ofMinutes(1));
        log.info(">>> INFO: Raw String saved to Redis.");

        return bookFromDb;
    }
}