package br.com.alura.persistence.enroll.useCase;

import java.io.NotActiveException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateEnrollDTO;
import br.com.alura.core.dto.GetEnrollDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.persistence.course.domain.Course;
import br.com.alura.persistence.course.repository.CourseRepository;
import br.com.alura.persistence.enroll.domain.Enroll;
import br.com.alura.persistence.enroll.repository.EnrollRepository;
import br.com.alura.persistence.user.domain.User;
import br.com.alura.persistence.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateEnroll implements UseCase< CreateEnrollDTO, GetEnrollDTO > {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollRepository enrollRepository;

	@Override
	public GetEnrollDTO execute( CreateEnrollDTO dto ) throws NotActiveException, NoSuchElementException {
		User student = this.getStudentUser( dto );
		Course course = this.getCourse( dto );
		if ( this.isDuplicatedEnroll( student, course ) ){
			throw new DuplicateKeyException( "Student already registered" );
		}
		Enroll enroll = Enroll.builder()
				.withStudent( student )
				.withCourse( course )
				.build();
		System.out.println( "\nSTUDENT -> " + student );
		System.out.println( "COURSE -> " + course  + "\n\n");
		enroll = this.enrollRepository.save( enroll );
		return new GetEnrollDTO(
				enroll.getId(),
				enroll.getStudent().getId(),
				enroll.getCourse().getId(),
				enroll.getCreatedAt() );
	}

	private boolean isDuplicatedEnroll( User student, Course course ) {
		Optional< Enroll > enrollOpt = this.enrollRepository.findByStudentAndCourse( student, course );
		return enrollOpt.isPresent();
	}

	private Course getCourse( CreateEnrollDTO dto ) throws NotActiveException, NoSuchElementException {
		Optional < Course > courseOpt = this.courseRepository.findById( dto.courseId() );
		if ( courseOpt.isEmpty() ) {
			throw new NoSuchElementException( "Course not found by id " + dto.courseId() );
		}
		Course course = courseOpt.get();
		if ( !course.getActive() ) {
			throw new NotActiveException( "Cant enroll in an inactive course" );
		}
		return course;
	}

	private User getStudentUser( CreateEnrollDTO dto ) throws NoSuchElementException {
		Optional< User > userOpt = this.userRepository.findById( dto.studentId() );
		if ( userOpt.isEmpty() ) {
			throw new NoSuchElementException( "Student not found by id " + dto.studentId() );
		}
		return userOpt.get();
	}

}
