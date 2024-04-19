package br.com.alura.enroll;

import java.io.NotActiveException;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CreateEnrollDTO;
import br.com.alura.core.dto.GetEnrollDTO;
import br.com.alura.persistence.enroll.useCase.CreateEnroll;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollManagement implements IEnrollManagement {

	private final CreateEnroll createEnroll;

	@Override
	public GetEnrollDTO createEnroll( CreateEnrollDTO dto )
			throws NotActiveException, NoSuchElementException {
		return this.createEnroll.execute( dto );
	}
	
}
