package br.com.alura;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AluraModulithTests {

	ApplicationModules modules = ApplicationModules.of( AluraModulith.class );

	@Test
	void createApplicationModuleModel() {
		modules.forEach( System.out::println );
	}

	@Test
	void writeDocumentationSnippets() {
		new Documenter( modules )
				.writeModuleCanvases()
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}

	// @Test
	// void shouldBeCompliant() {
	// 	modules.verify();
	// }

}
