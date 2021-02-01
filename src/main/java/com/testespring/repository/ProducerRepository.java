package com.testespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.testespring.model.Producer;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

}
