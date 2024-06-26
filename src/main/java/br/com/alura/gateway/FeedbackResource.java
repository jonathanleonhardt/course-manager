package br.com.alura.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.core.dto.CreateFeedbackDTO;
import br.com.alura.feedback.IFeedbackManagement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/feedback" )
public class FeedbackResource {

	private final IFeedbackManagement feedbackManagement;

	@PostMapping( "/create" )
	@PreAuthorize("hasRole('ESTUDANTE')")
	public ResponseEntity< Void > createFeedback( @Valid @RequestBody CreateFeedbackDTO feedback ) {
		try {
			return ResponseEntity.ok( this.feedbackManagement.createFeedback( feedback ) );
		} catch ( Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
