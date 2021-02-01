package com.testespring.initializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.testespring.model.Nominee;
import com.testespring.model.Producer;
import com.testespring.repository.NomineeRepository;
import com.testespring.repository.ProducerRepository;
import com.testespring.utils.OperationsHelper;

@Component
public class Initiate{
	
	@Autowired
	private NomineeRepository nomineeRepository;
	
	@Autowired
	private ProducerRepository producerRepository;

	@Autowired
	private OperationsHelper operationsHelper;

	@PostConstruct
	public void init() {
		List<List<String>> records = new ArrayList<List<String>>();
		try {
		    String[] values = null;
		    CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		    CSVReader csvReader = new CSVReaderBuilder(new FileReader("data-source/movielist.csv")).withCSVParser(csvParser).build();
		    while ((values = csvReader.readNext()) != null) {
		        records.add(Arrays.asList(values));
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		records.stream().forEach(row -> {
			Nominee newNominee = new Nominee();
			try{
				Integer.parseInt(row.get(0));
				newNominee.setYear(Integer.parseInt(row.get(0)));
				newNominee.setTitle(row.get(1));
				newNominee.setStudio(row.get(2));
				newNominee.setProducer(row.get(3));
				newNominee.setWinner("yes".equals(row.get(4)) ? true : false);
				newNominee = nomineeRepository.save(newNominee);
				
				List<String> producers = operationsHelper.explodeProducers(newNominee.getProducer());
				for(String p : producers) {
					Producer producer = new Producer();
					producer.setName(p.trim());
					producer.setNomineeId(newNominee.getId());
					producerRepository.save(producer);
				}
			} catch (NumberFormatException e) {
				
			}
		});
	}
}
