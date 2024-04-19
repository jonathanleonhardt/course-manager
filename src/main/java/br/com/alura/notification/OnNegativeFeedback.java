package br.com.alura.notification;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import br.com.alura.core.event.NotifyFeedbackEvent;
import br.com.alura.core.shared.UseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OnNegativeFeedback implements UseCase< NotifyFeedbackEvent, Void > {

	@ApplicationModuleListener
	public Void execute( NotifyFeedbackEvent event ) {
		String recipient = this.getRecipient( event );
		String subject = this.getSubject( event );
		String body = this.getBody( event );
		EmailSender.send( recipient, subject, body );
		return null;
	}

	private String getRecipient( NotifyFeedbackEvent event ) {
		return "%s - %s [ %s ]".formatted( event.instructorId(), event.instructorName(),
				event.instructorEmail() );
	}

	private String getSubject( NotifyFeedbackEvent event ) {
		return ".:: Negative feedback - %s ::.".formatted( event.courseCode() );
	}

	private String getBody( NotifyFeedbackEvent event ) {
		return """

					New negative feedback of %l in %s

					STUDENT: %s - %s

					DESCRIPTION: %s

				""".formatted( event.rating(), event.courseCode(), event.studentId(),
				event.studentId(), event.description() );
	}

}
