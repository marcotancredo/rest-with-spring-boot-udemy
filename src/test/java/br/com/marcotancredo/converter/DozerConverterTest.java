package br.com.marcotancredo.converter;

import br.com.marcotancredo.converter.mocks.MockPerson;
import br.com.marcotancredo.data.model.Person;
import br.com.marcotancredo.data.vo.v1.PersonVO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static br.com.marcotancredo.converter.DozerConverter.parseListObjects;
import static br.com.marcotancredo.converter.DozerConverter.parseObject;
import static org.junit.Assert.assertEquals;

public class DozerConverterTest {

    MockPerson inputObject;

    @Before
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO output = parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0), output.getKey());
        assertEquals("Address 0", output.getAddress());
        assertEquals("First Name 0", output.getFirstName());
        assertEquals("Last Name 0", output.getLastName());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = parseListObjects(inputObject.mockEntityList(), PersonVO.class);
        PersonVO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0), outputZero.getKey());
        assertEquals("Address 0", outputZero.getAddress());
        assertEquals("First Name 0", outputZero.getFirstName());
        assertEquals("Last Name 0", outputZero.getLastName());
        assertEquals("Male", outputZero.getGender());

        PersonVO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7), outputSeven.getKey());
        assertEquals("Address 7", outputSeven.getAddress());
        assertEquals("First Name 7", outputSeven.getFirstName());
        assertEquals("Last Name 7", outputSeven.getLastName());
        assertEquals("Female", outputSeven.getGender());

        PersonVO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getKey());
        assertEquals("Address 12", outputTwelve.getAddress());
        assertEquals("First Name 12", outputTwelve.getFirstName());
        assertEquals("Last Name 12", outputTwelve.getLastName());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        Person output = parseObject(inputObject.mockVO(), Person.class);
        assertEquals(Long.valueOf(0), output.getId());
        assertEquals("Address 0", output.getAddress());
        assertEquals("First Name 0", output.getFirstName());
        assertEquals("Last Name 0", output.getLastName());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseVOListToEntityListTest() {
        List<Person> outputList = parseListObjects(inputObject.mockVOList(), Person.class);
        Person outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0), outputZero.getId());
        assertEquals("Address 0", outputZero.getAddress());
        assertEquals("First Name 0", outputZero.getFirstName());
        assertEquals("Last Name 0", outputZero.getLastName());
        assertEquals("Male", outputZero.getGender());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7), outputSeven.getId());
        assertEquals("Address 7", outputSeven.getAddress());
        assertEquals("First Name 7", outputSeven.getFirstName());
        assertEquals("Last Name 7", outputSeven.getLastName());
        assertEquals("Female", outputSeven.getGender());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12), outputTwelve.getId());
        assertEquals("Address 12", outputTwelve.getAddress());
        assertEquals("First Name 12", outputTwelve.getFirstName());
        assertEquals("Last Name 12", outputTwelve.getLastName());
        assertEquals("Male", outputTwelve.getGender());
    }
}
