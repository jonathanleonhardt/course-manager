package br.com.alura.feedback.useCase;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateFeedbackDTO;
import br.com.alura.core.event.NotifyFeedbackEvent;
import br.com.alura.core.shared.UseCase;
import br.com.alura.course.domain.Course;
import br.com.alura.course.repository.CourseRepository;
import br.com.alura.feedback.domain.Feedback;
import br.com.alura.feedback.repository.FeedbackRepository;
import br.com.alura.user.domain.User;
import br.com.alura.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateFeedback implements UseCase< CreateFeedbackDTO, Void > {

	private final FeedbackRepository feedbackRepository;
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	private final ApplicationEventPublisher events;

	@Override
	public Void execute( CreateFeedbackDTO dto ) throws EntityNotFoundException {
		User student = this.userRepository.getReferenceById( dto.student_id() );
		Course course = this.courseRepository.getReferenceById( dto.couse_id() );
		Feedback feedback = Feedback.builder()
				.withRating( dto.rating() )
				.withDescription( dto.description() )
				.withCourse( course )
				.withStudent( student )
				.build();
		this.feedbackRepository.save( feedback );
		this.notifyFeedback( feedback );
		return null;
	}

	public void notifyFeedback( Feedback feedback ) {
		if ( feedback.getRating() > 6L ) {
			return;
		}
		Course course = feedback.getCourse();
		User student = feedback.getStudent();
		User instructor = course.getInstructor();
		NotifyFeedbackEvent feedbackEvent = new NotifyFeedbackEvent(
				feedback.getRating(),
				feedback.getDescription(),
				course.getCode(),
				student.getId(),
				student.getName(),
				instructor.getId(),
				instructor.getName(),
				instructor.getEmail() );
		this.events.publishEvent( feedbackEvent );
	}

}
