package br.com.alura.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CourseNpsDTO;
import br.com.alura.core.shared.UseCase;
import br.com.alura.course.domain.Course;
import br.com.alura.course.repository.CourseRepository;
import br.com.alura.enroll.repository.EnrollRepository;
import br.com.alura.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenerateCurrentNPS implements UseCase< Void, List< CourseNpsDTO > > {

	private final CourseRepository courseRepository;
	private final FeedbackRepository feedbackRepository;
	private final EnrollRepository enrollRepository;

	public List< CourseNpsDTO > execute() {
		return this.execute( null );
	}

	@Override
	public List< CourseNpsDTO > execute( Void noParam ) {
		List< Course > courses = this.courseRepository.listByActive( true );
		return courses.stream()
				.filter( course -> this.enrollRepository.countByCourse( course ) > 5 )
				.map( course -> new CourseNpsDTO( course.getCode(), this.getCourseNPS( course ) ) )
				.collect( Collectors.toList() );
	}

	private double getCourseNPS( Course course ) {
		List< Long > ratings = this.getCourseRatings( course );
		int promoters = 0;
		int detractors = 0;
		for ( Long rating : ratings ) {
			if ( rating >= 9 ) {
				promoters++;
			} else if ( rating <= 6 ) {
				detractors++;
			}
		}
		double nps = ( double ) ( promoters - detractors ) / ratings.size() * 100;
		return nps;
	}

	private List< Long > getCourseRatings( Course course ) {
		return this.feedbackRepository.listByCourse( course ).stream()
				.map( feedback -> feedback.getRating() )
				.collect( Collectors.toList() );
	}

}
