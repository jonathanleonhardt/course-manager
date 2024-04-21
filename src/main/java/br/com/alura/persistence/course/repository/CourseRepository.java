package br.com.alura.persistence.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.persistence.course.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository< Course, String > {

	Optional< Course > findByCode( String code );

	@Query( "SELECT c FROM Course c where c.active = :active" )
	Page< Course > listCourseByStatus( @Param( "active" ) boolean active, Pageable pageable );

	@Query( "SELECT c FROM Course c where c.active is true" )
	List< Course > listActiveCourses();

}
