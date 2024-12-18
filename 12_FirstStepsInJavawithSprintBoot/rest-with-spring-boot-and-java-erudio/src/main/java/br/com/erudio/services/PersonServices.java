package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.ErudioMapper;
import br.com.erudio.model.Person;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.repositories.PersonRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public PersonVO findById(Long id) {
		logger.info("Finding one Person!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		var vo = ErudioMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding All people!");
		var persons = ErudioMapper.parseListObjects(repository.findAll(), PersonVO.class);
		
		persons
			.stream()
			.forEach( p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel())); 
		
		return persons;
	}

	public PersonVO create(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one Person!");
		var entity = ErudioMapper.parseObject(person, Person.class);
		var vo =  ErudioMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;		
	}
	
	public PersonVO update(PersonVO person) {
		
		if (person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating one Person!");
		
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo =  ErudioMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());		
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one Person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

		repository.delete(entity);
	}
		
	
	
}
