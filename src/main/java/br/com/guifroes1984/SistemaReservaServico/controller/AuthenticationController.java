package br.com.guifroes1984.SistemaReservaServico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guifroes1984.SistemaReservaServico.dto.InscreverRequestDTO;
import br.com.guifroes1984.SistemaReservaServico.dto.UsuarioDto;
import br.com.guifroes1984.SistemaReservaServico.services.authentication.AuthService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/cliente/inscrever")
	public ResponseEntity<?> inscreverCliente(@RequestBody InscreverRequestDTO inscreverRequestDTO) {
		
		if (authService.presentePorEmail(inscreverRequestDTO.getEmail())) {
			return new ResponseEntity<>("Já existe um cliente com este E-mail!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UsuarioDto usuarioCriado = authService.inscricaoCliente(inscreverRequestDTO);
		
		return new ResponseEntity<>(usuarioCriado, HttpStatus.OK);
	}
	
	@PostMapping("/empresa/inscrever")
	public ResponseEntity<?> inscreverEmpresa(@RequestBody InscreverRequestDTO inscreverRequestDTO) {
		
		if (authService.presentePorEmail(inscreverRequestDTO.getEmail())) {
			return new ResponseEntity<>("Já existe uma empresa com este E-mail!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UsuarioDto usuarioCriado = authService.inscricaoCliente(inscreverRequestDTO);
		
		return new ResponseEntity<>(usuarioCriado, HttpStatus.OK);
	}

}
