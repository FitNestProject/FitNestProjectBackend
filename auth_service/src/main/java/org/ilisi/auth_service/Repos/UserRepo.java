package org.ilisi.auth_service.Repos;

import org.ilisi.auth_service.entities.User;
import org.ilisi.auth_service.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByAccount(Account account);
}
