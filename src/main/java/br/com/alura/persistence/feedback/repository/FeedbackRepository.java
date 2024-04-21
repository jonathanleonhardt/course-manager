package br.com.alura.persistence.feedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.persistence.feedback.domain.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository< Feedback, String > {

	@Query( "SELECT f FROM Feedback f where f.course.id = :courseId" )
	List< Feedback > listByCourseId( @Param( "courseId" ) String courseId );

}
