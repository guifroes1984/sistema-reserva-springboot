package br.com.guifroes1984.SistemaReservaServico.entity;

import br.com.guifroes1984.SistemaReservaServico.dto.UsuarioDto;
import br.com.guifroes1984.SistemaReservaServico.enums.UsuarioRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TBL_USUARIO")
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String password;
	
	private String nome;
	
	private String sobreNome;
	
	private String telefone;
	
	private UsuarioRole role;
	
	public UsuarioDto getDto() {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(id);
		usuarioDto.setNome(nome);
		usuarioDto.setEmail(email);
		usuarioDto.setRole(role);
		
		return usuarioDto;
	}

}
