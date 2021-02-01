package com.testespring.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.testespring.model.Nominee;
import com.testespring.repository.NomineeRepository;

@RestController
@RequestMapping("/nominee")
public class NomineesController {
	
	@Autowired
	private NomineeRepository nomineeRepository;

	@GetMapping(value = {"", "/{nomId}"})
	@ResponseBody
	public List<Nominee> list(@PathVariable(required = false) String nomId) {
		if(nomId == null || nomId.isEmpty()) {
			return nomineeRepository.findAll();
		} else {
			return Arrays.asList(nomineeRepository.findById(Long.parseLong(nomId)).get());
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Nominee add(@RequestBody Nominee nominee) {
		return nomineeRepository.save(nominee);
	}
	
	@DeleteMapping("/{nomId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable String nomId) {
		try {
			nomineeRepository.deleteById(Long.parseLong(nomId));
		} catch(EmptyResultDataAccessException e) {
			
		}
	}
}
