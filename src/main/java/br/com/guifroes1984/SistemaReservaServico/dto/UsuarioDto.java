package br.com.guifroes1984.SistemaReservaServico.dto;

import br.com.guifroes1984.SistemaReservaServico.enums.UsuarioRole;
import lombok.Data;

@Data
public class UsuarioDto {
	
private Long id;
	
	private String email;
	
	private String password;
	
	private String nome;
	
	private String sobreNome;
	
	private String telefone;
	
	private UsuarioRole role;

}
