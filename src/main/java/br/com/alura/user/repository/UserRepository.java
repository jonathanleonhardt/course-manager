package br.com.alura.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository< User, String > {

	Optional< User > findByUsername( String username );

}
