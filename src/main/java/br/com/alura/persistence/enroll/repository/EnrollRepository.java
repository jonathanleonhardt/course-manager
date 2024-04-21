package br.com.alura.persistence.enroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.persistence.course.domain.Course;
import br.com.alura.persistence.enroll.domain.Enroll;
import br.com.alura.persistence.user.domain.User;

@Repository
public interface EnrollRepository extends JpaRepository< Enroll, String > {

	Optional< Enroll > findByStudentAndCourse( User student, Course course );
	
	@Query( "SELECT COUNT(e) FROM Enroll e WHERE e.course = :course" )
	long countByCourse( Course course );

}
