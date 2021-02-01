package com.testespring.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.testespring.model.Nominee;

@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long> {


	@Query(value = "SELECT p.name AS name, n.year AS year "
			+ "FROM Producer p, Nominee n "
			+ "WHERE n.id = p.nominee_id AND n.winner = true AND "
			+ "p.name IN (SELECT p2.name FROM Producer p2, Nominee n2 WHERE n2.id = p2.nominee_id AND n2.winner = true GROUP BY p2.name HAVING COUNT(p2.name) > 1) "
			+ "GROUP BY p.name, n.year "
			+ "ORDER BY p.name, n.year ",
			nativeQuery = true) 
	List<Map<String,Object>> producerWithMoreThanTwoPrizes();
}
