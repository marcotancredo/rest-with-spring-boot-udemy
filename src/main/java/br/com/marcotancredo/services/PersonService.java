package br.com.marcotancredo.services;

import br.com.marcotancredo.converter.DozerConverter;
import br.com.marcotancredo.data.model.Person;
import br.com.marcotancredo.data.vo.v1.PersonVO;
import br.com.marcotancredo.exception.ResourceNotFoundException;
import br.com.marcotancredo.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.marcotancredo.converter.DozerConverter.parseObject;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonVO create(PersonVO personVO) {
        Person entity = parseObject(personVO, Person.class);
        return parseObject(personRepository.save(entity), PersonVO.class);
    }

    public PersonVO update(PersonVO personVO) {
        Person entity = personRepository.findById(personVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddress(personVO.getAddress());
        entity.setGender(personVO.getGender());
        return parseObject(personRepository.save(entity), PersonVO.class);
    }

    public void delete(Long id) {
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }

    @Transactional
    public PersonVO disablePerson(Long id){
        personRepository.disablePersons(id);

        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return parseObject(entity, PersonVO.class);
    }

    public PersonVO findById(Long id){
        Person entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        return parseObject(entity, PersonVO.class);
    }

    public Page<PersonVO> findAll(PageRequest of){
        Page<Person> page = personRepository.findAll(of);
        return page.map(this::convertToPersonVO);
    }

    public Page<PersonVO> findPersonByName(String firstName, PageRequest of){
        Page<Person> page = personRepository.findPersonByName(firstName, of);
        return page.map(this::convertToPersonVO);
    }

    private PersonVO convertToPersonVO(Person person) {
        return DozerConverter.parseObject(person, PersonVO.class);
    }
}
