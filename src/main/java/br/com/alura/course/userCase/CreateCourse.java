package br.com.alura.course.userCase;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.GetCourseDTO;
import br.com.alura.core.dto.GetUserDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.course.domain.Course;
import br.com.alura.course.repository.CourseRepository;
import br.com.alura.user.domain.User;
import br.com.alura.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCourse implements UseCase< CreateCourseDTO, GetCourseDTO > {

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	@Override
	public GetCourseDTO execute( CreateCourseDTO dto ) throws Exception {
		Optional< User > userOpt = this.userRepository.findById( dto.instructorId() );
		if ( userOpt.isEmpty() ) {
			throw new NoSuchElementException( "Instructor not found for id " + dto.instructorId() );
		}
		User instructor = userOpt.get();
		Course course = Course.builder()
				.withName( dto.name() )
				.withDescription( dto.description() )
				.withCode( dto.code() )
				.withInstructor( instructor )
				.withActive( true )
				.build();
		course = this.courseRepository.save( course );
		GetUserDTO instructorDTO = new GetUserDTO(
				instructor.getName(),
				instructor.getEmail(),
				null );
		return new GetCourseDTO(
				course.getId(),
				course.getName(),
				course.getDescription(),
				course.getCode(),
				instructorDTO,
				course.getActive(),
				course.getCreatedAt(),
				course.getInactivatedAt() );
	}

}
