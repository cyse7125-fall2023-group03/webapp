package repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.User;

@Repository
//@Qualifier(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

}
