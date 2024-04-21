package br.com.alura.gateway;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.core.dto.CourseNpsDTO;
import br.com.alura.dashboard.IDashboardManagement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/dashboard" )
public class DashboardResource {

	private final IDashboardManagement dashboardManagement;
	
	@GetMapping( "/nps" )
	public ResponseEntity< List< CourseNpsDTO > > listNPS() {
		try {
			return ResponseEntity.ok( this.dashboardManagement.generateCurrentNps() );
		} catch ( Exception e) {
			return ResponseEntity.badRequest().build();
		}
		
	}

}
