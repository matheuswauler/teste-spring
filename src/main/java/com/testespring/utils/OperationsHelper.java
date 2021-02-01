package com.testespring.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class OperationsHelper {
	
	public List<String> explodeProducers(String producers) {
		String[] pieces = producers.split(",| and ");
		
		
		return Arrays.asList(pieces);
	}

}
