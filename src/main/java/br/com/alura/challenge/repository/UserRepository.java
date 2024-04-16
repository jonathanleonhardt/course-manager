package br.com.alura.challenge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.challenge.domain.User;

@Repository
public interface UserRepository extends JpaRepository< User, String > {

	Optional< User > findByUsername( String username );

}
