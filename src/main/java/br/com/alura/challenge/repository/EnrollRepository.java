package br.com.alura.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.challenge.domain.Enroll;

@Repository
public interface EnrollRepository extends JpaRepository< Enroll, String > {
	
}
