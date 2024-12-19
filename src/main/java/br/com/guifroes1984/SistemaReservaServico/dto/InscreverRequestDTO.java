package br.com.guifroes1984.SistemaReservaServico.dto;

import lombok.Data;

@Data
public class InscreverRequestDTO {

	private Long id;

	private String email;

	private String password;

	private String nome;

	private String sobreNome;

	private String telefone;

}
