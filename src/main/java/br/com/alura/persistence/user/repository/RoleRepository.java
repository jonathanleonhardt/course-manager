package br.com.alura.persistence.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.persistence.user.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository< Role, String > {

	Optional< Role > findByName( String name );

}
