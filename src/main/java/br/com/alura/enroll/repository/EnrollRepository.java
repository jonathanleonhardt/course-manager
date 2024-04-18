package br.com.alura.enroll.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.course.domain.Course;
import br.com.alura.enroll.domain.Enroll;
import br.com.alura.user.domain.User;

@Repository
public interface EnrollRepository extends JpaRepository< Enroll, String > {

	Optional< Enroll > findByStudentAndCourse( User student, Course course );

}
