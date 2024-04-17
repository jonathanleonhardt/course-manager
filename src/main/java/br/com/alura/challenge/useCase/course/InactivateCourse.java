package br.com.alura.challenge.useCase.course;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.challenge.domain.Course;
import br.com.alura.challenge.repository.CourseRepository;
import br.com.alura.challenge.shared.UseCase;
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
