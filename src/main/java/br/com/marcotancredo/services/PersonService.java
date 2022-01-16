package br.com.marcotancredo.services;

import br.com.marcotancredo.exception.ResourceNotFounException;
import br.com.marcotancredo.model.Person;
import br.com.marcotancredo.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }


    public Person update(Person person) {
        Person entity = personRepository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFounException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return personRepository.save(entity);
    }

    public void delete(Long id) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFounException("No records found for this ID"));
        personRepository.delete(entity);
    }

    public Person findById(Long id){
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFounException("No records found for this ID"));
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }
}
