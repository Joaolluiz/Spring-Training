package com.webservice.library.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.webservice.library.controllers.BookController;
import com.webservice.library.data.vo.v1.BookVO;
import com.webservice.library.entities.Book;
import com.webservice.library.exceptions.RequiredObjectIsNullException;
import com.webservice.library.exceptions.ResourceNotFoundException;
import com.webservice.library.mapper.DozzerMapper;
import com.webservice.library.repositories.BookRepository;

@Service
public class BookService {

	private Logger logger = Logger.getLogger(BookService.class.getName());

	@Autowired
	BookRepository bookRepository;

	@Autowired
	PagedResourcesAssembler<BookVO> assembler;

	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

		logger.info("Finding all books!");

		Page<Book> bookPage = bookRepository.findAll(pageable);

		Page<BookVO> bookVosPage = bookPage.map(book -> DozzerMapper.parseObject(book, BookVO.class));

		bookVosPage.map(
				book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel()));

		Link link = linkTo(
				methodOn(BookController.class)
						.findAll(pageable.getPageNumber(),
								pageable.getPageSize(),
								"asc")).withSelfRel();


		return assembler.toModel(bookVosPage, link);
	}

	public BookVO findById(Long id) {

		logger.info("Finding one book!");

		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = DozzerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public BookVO createBook(BookVO book) {

		if (book == null)
			throw new RequiredObjectIsNullException();

		logger.info("Creating one book!");

		var entity = DozzerMapper.parseObject(book, Book.class);
		var vo = DozzerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}

	public BookVO updateBook(BookVO book) {

		if (book == null)
			throw new RequiredObjectIsNullException();

		logger.info("Updating one book!");
				
		var entity = bookRepository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		var vo = DozzerMapper.parseObject(bookRepository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one book!");
		
		var entity = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		bookRepository.delete(entity);
	}
}
