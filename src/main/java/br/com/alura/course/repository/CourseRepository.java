package br.com.alura.course.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.course.domain.Course;

@Repository
public interface CourseRepository extends JpaRepository< Course, String > {

	Optional< Course > findByCode( String code );

	@Query( "SELECT c FROM Course c where c.active = :active" )
	Page< Course > listCourseByStatus( @Param( "active" ) Boolean status, Pageable pageable );

	@Modifying
	@Query( "UPDATE Course c "
			+ "SET c.inactivatedAt = CURRENT_TIMESTAMP, c.active = false "
			+ "WHERE c.code = :code" )
	void setCourseInactive( @Param( "code" ) String code );

}
