package br.com.marcotancredo.services;

import br.com.marcotancredo.data.model.Book;
import br.com.marcotancredo.data.vo.v1.BookVO;
import br.com.marcotancredo.exception.ResourceNotFoundException;
import br.com.marcotancredo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.marcotancredo.converter.DozerConverter.parseListObjects;
import static br.com.marcotancredo.converter.DozerConverter.parseObject;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookVO create(BookVO bookVO) {
        Book entity = parseObject(bookVO, Book.class);
        return parseObject(bookRepository.save(entity), BookVO.class);
    }

    public BookVO update(BookVO bookVO) {
        Book entity = bookRepository.findById(bookVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setAuthor(bookVO.getAuthor());
        entity.setLaunchDate(bookVO.getLaunchDate());
        entity.setPrice(bookVO.getPrice());
        entity.setTitle(bookVO.getTitle());
        return parseObject(bookRepository.save(entity), BookVO.class);
    }

    public void delete(Long id) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(entity);
    }

    public BookVO findById(Long id){
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return parseObject(entity, BookVO.class);
    }

    public List<BookVO> findAll(){
        return parseListObjects(bookRepository.findAll(), BookVO.class);
    }
}
