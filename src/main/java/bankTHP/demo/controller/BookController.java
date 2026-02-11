package bankTHP.demo.controller;


import bankTHP.demo.entity.Book;
import bankTHP.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @GetMapping()
    public ResponseEntity health(){
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{isbn}")
    public Book getBook(@PathVariable String isbn) {
        return service.getBookByIsbn(isbn);
    }
}