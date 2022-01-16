package br.com.marcotancredo.controllers;

import br.com.marcotancredo.model.Person;
import br.com.marcotancredo.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    @GetMapping("/findAll")
    public List<Person> findAll() {
        return personService.findAll();
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return personService.update(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
