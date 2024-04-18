package br.com.alura.enroll.useCase;

import java.io.NotActiveException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.alura.core.shared.UseCase;
import br.com.alura.course.domain.Course;
import br.com.alura.course.repository.CourseRepository;
import br.com.alura.enroll.domain.Enroll;
import br.com.alura.enroll.repository.EnrollRepository;
import br.com.alura.enroll.useCase.dto.CreateEnrollDTO;
import br.com.alura.enroll.useCase.dto.GetEnrollDTO;
import br.com.alura.user.domain.User;
import br.com.alura.user.repository.UserRepository;
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
				.withCourse( course )
				.withStudent( student )
				.build();
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
		Optional < Course > courseOpt = this.courseRepository.findByCode( dto.courseId() );
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
