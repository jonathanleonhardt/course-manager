package br.com.alura.persistence.course.useCase;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.alura.core.shared.UseCase;
import br.com.alura.persistence.course.domain.Course;
import br.com.alura.persistence.course.repository.CourseRepository;
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
		Course course = courseOpt.get();
		course.setActive( false );
		course.setInactivatedAt(  Calendar.getInstance() );
		this.courseRepository.save( course );
		return null;
	}

}
