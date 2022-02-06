package br.com.marcotancredo.converter.mocks;

import br.com.marcotancredo.data.model.Person;
import br.com.marcotancredo.data.vo.v1.PersonVO;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }

    public PersonVO mockVO() {
        return mockVO(0);
    }

    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> personsVO = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            personsVO.add(mockVO(i));
        }
        return personsVO;
    }

    private Person mockEntity(Integer number) {
        Person person = new Person();
        person.setAddress("Address " + number);
        person.setFirstName("First Name " + number);
        person.setLastName("Last Name " + number);
        person.setGender(((number % 2) == 0) ? "Male" : "Female");
        person.setId(number.longValue());
        return person;
    }

    private PersonVO mockVO(Integer number) {
        PersonVO personVO = new PersonVO();
        personVO.setAddress("Address " + number);
        personVO.setFirstName("First Name " + number);
        personVO.setLastName("Last Name " + number);
        personVO.setGender(((number % 2) == 0) ? "Male" : "Female");
        personVO.setKey(number.longValue());
        return personVO;
    }
}

