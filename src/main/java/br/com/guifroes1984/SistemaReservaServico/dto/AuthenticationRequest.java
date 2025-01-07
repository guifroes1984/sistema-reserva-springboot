package br.com.guifroes1984.SistemaReservaServico.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
	
	private String userName;
	
	private String password;

}
