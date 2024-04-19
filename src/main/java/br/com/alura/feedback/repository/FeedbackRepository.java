package br.com.alura.feedback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.feedback.domain.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository< Feedback, String > {
	
}
