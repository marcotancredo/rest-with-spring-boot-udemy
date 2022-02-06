package br.com.marcotancredo.controllers;

import br.com.marcotancredo.data.vo.v1.BookVO;
import br.com.marcotancredo.services.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/book/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO findById(@PathVariable("id") Long id) {
        BookVO bookVO = bookService.findById(id);
        bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookVO;
    }

    @GetMapping(value ="/findAll", produces = {"application/json", "application/xml", "application/x-yaml"})
    public List<BookVO> findAll() {
        List<BookVO> booksVO = bookService.findAll();
        booksVO.forEach(p -> p.add(linkTo(methodOn(BookController.class)
                .findById(p.getKey()))
                .withSelfRel())
        );
        return booksVO;
    }

    @PostMapping(produces = {"application/json", "application/xml","application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO create(@RequestBody BookVO entity) {
        BookVO bookVO = bookService.create(entity);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public BookVO update(@RequestBody BookVO entity) {
        BookVO bookVO = bookService.update(entity);
        bookVO.add(linkTo(methodOn(BookController.class).findById(entity.getKey())).withSelfRel());
        return bookVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }
}
