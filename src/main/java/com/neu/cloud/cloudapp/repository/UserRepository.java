package com.neu.cloud.cloudapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neu.cloud.cloudapp.model.User;

@Repository
//@Qualifier(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {


}
