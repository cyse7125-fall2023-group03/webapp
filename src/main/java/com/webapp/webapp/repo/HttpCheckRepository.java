package com.webapp.webapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.webapp.model.HttpCheck;

@Repository
public interface HttpCheckRepository extends JpaRepository<HttpCheck, String> {

	Optional<HttpCheck> findById(String id);

	void deleteById(String id);

}
