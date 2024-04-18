package br.com.alura.user.domain;

import java.util.Calendar;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import br.com.alura.core.enums.UserRolesEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder( setterPrefix = "with" )
@Entity
@Table( name = "alura_user" )
public class User {

	@Id
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	private String id;

	@Column( name = "name", columnDefinition = "text", nullable = false )
	private String name;

	@Column( name = "username", length = 20, unique = true )
	private String username;

	@Column( name = "password", columnDefinition = "text", nullable = false )
	private String password;

	@Column( name = "roles" )
	@ElementCollection( targetClass = UserRolesEnum.class )
	@Enumerated( EnumType.STRING )
	private Set< UserRolesEnum > roles;

	@Column( name = "email", columnDefinition = "text", unique = true )
	private String email;

	@Builder.Default
	@Column( name = "created_at", columnDefinition = "TIMESTAMP" )
	private Calendar createdAt = Calendar.getInstance();

}
