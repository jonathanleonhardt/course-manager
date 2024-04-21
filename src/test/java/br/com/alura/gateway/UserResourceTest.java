package br.com.alura.gateway;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.AluraModulith;
import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.enums.UserRolesEnum;
import br.com.alura.persistence.user.domain.Role;
import br.com.alura.persistence.user.repository.RoleRepository;
import br.com.alura.persistence.user.repository.UserRepository;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AluraModulith.class )
@AutoConfigureMockMvc
@TestPropertySource( locations = "classpath:application-test.properties" )
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
@TestInstance( Lifecycle.PER_CLASS )
class UserResourceTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	private CreateUserDTO userDTO;

	@BeforeAll
	void configure() {
		this.createRoles();
		userDTO = new CreateUserDTO(
				"name",
				"username",
				"email@mail.com",
				"pass",
				UserRolesEnum.ADMIN );
	}

	@Test
	@Order( 1 )
	void giveStatus200_whenCreateNewUser_withExistentRole() throws Exception {
		
		mvc.perform( post( "/api/user/create" )
					.contentType( MediaType.APPLICATION_JSON )
					.content( new ObjectMapper().writeValueAsString( userDTO ) ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) );
	}

	@Test
	@Order( 2 )
	@WithMockUser( username = "name", password = "pass", roles = "ADMIN" )
	void giveStatus200AndBody_WHENgetUser_withFindByUsernameValid() throws Exception {
		mvc.perform( get( "/api/user/info/" + userDTO.username() )
					.contentType( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
				.andExpect( jsonPath( "$.name" ).value( userDTO.name() ) );
	}

	@AfterAll
	void clean() {
		this.userRepository.deleteAll();
		this.roleRepository.deleteAll();
	}

	private void createRoles() {
		Role adminRole = Role.builder().withName( UserRolesEnum.ADMIN.name() ).build();
		Role studentRole = Role.builder().withName( UserRolesEnum.ESTUDANTE.name() ).build();
		Role instructorRole = Role.builder().withName( UserRolesEnum.INSTRUTOR.name() ).build();
		roleRepository.save( adminRole );
		roleRepository.save( studentRole );
		roleRepository.save( instructorRole );
	}

}
