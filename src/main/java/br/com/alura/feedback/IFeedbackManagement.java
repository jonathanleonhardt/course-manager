package br.com.alura.feedback;

import br.com.alura.core.dto.CreateFeedbackDTO;
import jakarta.persistence.EntityNotFoundException;

public interface IFeedbackManagement {

	Void createFeedback( CreateFeedbackDTO dto ) throws EntityNotFoundException;

}
