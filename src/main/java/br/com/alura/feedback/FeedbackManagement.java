package br.com.alura.feedback;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateFeedbackDTO;
import br.com.alura.persistence.feedback.useCase.CreateFeedback;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackManagement implements IFeedbackManagement {

	private final CreateFeedback createFeedback;

	@Override
	public Void createFeedback( CreateFeedbackDTO dto ) throws EntityNotFoundException {
		return this.createFeedback.execute( dto );
	}
	
}
