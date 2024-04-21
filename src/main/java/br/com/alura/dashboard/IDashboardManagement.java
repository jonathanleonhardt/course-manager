package br.com.alura.dashboard;

import java.util.List;

import br.com.alura.core.dto.CourseNpsDTO;

public interface IDashboardManagement {

	List< CourseNpsDTO > generateCurrentNps();

}
