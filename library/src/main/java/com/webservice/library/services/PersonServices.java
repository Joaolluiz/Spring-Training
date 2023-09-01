package com.webservice.library.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.library.entities.Person;
import com.webservice.library.exceptions.ResourceNotFoundException;
import com.webservice.library.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	public List<Person> findAll() {

		logger.info("Finding all people!");

		return personRepository.findAll();
	}

	public Person findById(Long id) {

		logger.info("Finding one person!");

		return personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}

	public Person createPerson(Person person) {

		logger.info("Creating one person!");

		return personRepository.save(person);
	}

	public Person updatePerson(Person person) {

		logger.info("Updating one person!");

		Person entity = personRepository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return personRepository.save(entity);
	}

	public void deletePerson(Long id) {

		logger.info("Deleting one person!");

		Person entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(entity);
	}

}
