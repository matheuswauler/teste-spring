package com.testespring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testespring.repository.NomineeRepository;

@RestController
@RequestMapping("/winners")
public class WinnersController {
	
	@Autowired
	private NomineeRepository nomineeRepository;
	
	@GetMapping
	public Map<String,List<Integer>> listar() {
		 return getProducersMap();
	}

	@GetMapping(value = {"/biggest-interval"})
	public String biggestInterval() {
		Map<String,List<Integer>> mapProducers =  getProducersMap();
		
		String returnProducer = null;
		int biggestInterval = 0;
		
		for (String producer : mapProducers.keySet()) {
			int producerDiff = 0;
			int producerLastYear = 0;
			for(int year : mapProducers.get(producer)) {
				if(producerLastYear > 0) {
					int newProducerYearDiff = year - producerLastYear;
					producerDiff = newProducerYearDiff > producerDiff ?
							newProducerYearDiff : producerDiff;
				}
				producerLastYear = year;
			}
			if(producerDiff > biggestInterval) {
				biggestInterval = producerDiff;
				returnProducer = producer;
			}
		}
		
		return returnProducer;
	}
	
	@GetMapping(value = {"/smallest-interval"})
	public String smallestInterval() {
		Map<String,List<Integer>> mapProducers =  getProducersMap();
		
		String returnProducer = null;
		int biggestInterval = Integer.MAX_VALUE;
		
		for (String producer : mapProducers.keySet()) {
			int producerDiff = Integer.MAX_VALUE;
			int producerLastYear = 0;
			for(int year : mapProducers.get(producer)) {
				if(producerLastYear > 0) {
					int newProducerYearDiff = year - producerLastYear;
					producerDiff = newProducerYearDiff < producerDiff ?
							newProducerYearDiff : producerDiff;
				}
				producerLastYear = year;
			}
			if(producerDiff < biggestInterval) {
				biggestInterval = producerDiff;
				returnProducer = producer;
			}
		}
		
		return returnProducer;
	}
	
	public Map<String,List<Integer>> getProducersMap() {
		List<Map<String,Object>> producers = nomineeRepository.producerWithMoreThanTwoPrizes();
		
		Map<String,List<Integer>> mapProducers = new HashMap<String, List<Integer>>();
		for(Map<String, Object> producer : producers) {
			if(!mapProducers.containsKey(producer.get("NAME"))) {
				ArrayList<Integer> years = new ArrayList<Integer>();
				years.add((int)producer.get("YEAR"));
				mapProducers.put(producer.get("NAME").toString(), years);
			} else {
				mapProducers.get(producer.get("NAME").toString())
				.add((int)producer.get("YEAR"));
			}
		}
		
		return mapProducers;
	}
}
