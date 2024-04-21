package br.com.alura.dashboard;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.core.dto.CourseNpsDTO;
import br.com.alura.persistence.course.useCase.GenerateCurrentNPS;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardManagement implements IDashboardManagement {

	private final GenerateCurrentNPS generateCurrentNPS;

	@Override
	public List< CourseNpsDTO > generateCurrentNps() {
		return this.generateCurrentNPS.execute( null );
	}
}
