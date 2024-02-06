package com.questionpro.assesment.repository;

import com.questionpro.assesment.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
  Optional<Users> findByUsername(String username);
}
