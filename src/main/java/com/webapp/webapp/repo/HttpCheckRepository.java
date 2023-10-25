package com.webapp.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.webapp.model.HttpCheck;

@Repository
public interface HttpCheckRepository extends JpaRepository<HttpCheck, Integer> {

	HttpCheck findById(String id);

	HttpCheck deleteById(String id);

}
