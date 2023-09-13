package com.webservice.library.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservice.library.data.vo.v1.PersonVO;
import com.webservice.library.exceptions.ResourceNotFoundException;
import com.webservice.library.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository personRepository;

	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return personRepository.findAll();
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one person!");

		return personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	}

	public PersonVO createPerson(PersonVO person) {

		logger.info("Creating one person!");

		return personRepository.save(person);
	}

	public PersonVO updatePerson(PersonVO person) {

		logger.info("Updating one person!");

		PersonVO entity = personRepository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return personRepository.save(entity); 
	}

	public void deletePerson(Long id) {

		logger.info("Deleting one person!");

		PersonVO entity = personRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		personRepository.delete(entity);
	}

}
