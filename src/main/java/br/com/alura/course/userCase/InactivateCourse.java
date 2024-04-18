package br.com.alura.course.userCase;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.core.shared.UseCase;
import br.com.alura.course.domain.Course;
import br.com.alura.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InactivateCourse implements UseCase< String, Void > {

	private final CourseRepository courseRepository;

	@Override
	public Void execute( String code ) throws NoSuchElementException {
		Optional < Course > courseOpt = this.courseRepository.findByCode( code );
		if ( courseOpt.isEmpty() ) {
			throw new NoSuchElementException( "Course not found" );
		}
		this.courseRepository.setCourseInactive( code );
		return null;
	}

}
