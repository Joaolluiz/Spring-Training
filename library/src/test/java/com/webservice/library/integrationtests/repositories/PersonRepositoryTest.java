package com.webservice.library.integrationtests.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.webservice.library.entities.Person;
import com.webservice.library.integrationtests.testcontainers.AbstractIntegrationTest;
import com.webservice.library.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    public PersonRepository personRepository;

    private static Person person;

    @BeforeAll
    public static void setup() {
        person = new Person();
    }

    @Test
    @Order(0)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "firstName"));
        person = personRepository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertFalse(person.getEnabled());

        assertEquals(133, person.getId());

        assertEquals("Sayres", person.getFirstName());
        assertEquals("Styche", person.getLastName());
        assertEquals("053 Marquette Street", person.getAddress());
        assertEquals("Male", person.getGender());

    }

    @Test
    @Order(1)
    public void testDisablePerson() throws JsonMappingException, JsonProcessingException {

        person.setEnabled(true);

        personRepository.disablePerson(person.getId());

        Pageable pageable = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "firstName"));
        person = personRepository.findPersonsByName("ayr", pageable).getContent().get(0);

        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());
        assertFalse(person.getEnabled());

        assertEquals(133, person.getId());

        assertEquals("Sayres", person.getFirstName());
        assertEquals("Styche", person.getLastName());
        assertEquals("053 Marquette Street", person.getAddress());
        assertEquals("Male", person.getGender());

    }
}
