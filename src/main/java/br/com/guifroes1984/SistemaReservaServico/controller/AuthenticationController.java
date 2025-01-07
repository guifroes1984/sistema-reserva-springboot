package br.com.guifroes1984.SistemaReservaServico.controller;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.guifroes1984.SistemaReservaServico.dto.AuthenticationRequest;
import br.com.guifroes1984.SistemaReservaServico.dto.InscreverRequestDTO;
import br.com.guifroes1984.SistemaReservaServico.dto.UsuarioDto;
import br.com.guifroes1984.SistemaReservaServico.entity.Usuario;
import br.com.guifroes1984.SistemaReservaServico.repository.UsuarioRepository;
import br.com.guifroes1984.SistemaReservaServico.services.authentication.AuthService;
import br.com.guifroes1984.SistemaReservaServico.services.jwt.UserDetailsServiceImpl;
import br.com.guifroes1984.SistemaReservaServico.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String HEADER_STRING = "Authorization";
	
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
		
		UsuarioDto usuarioCriado = authService.inscricaoEmpresa(inscreverRequestDTO);
		
		return new ResponseEntity<>(usuarioCriado, HttpStatus.OK);
	}
	
	@PostMapping({"/autenticar"})
	public void criarAutenticacaoToken(@RequestBody AuthenticationRequest authenticationRequest, 
									   HttpServletResponse response) throws IOException, JSONException {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Nome de usuário ou senha incorretos", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		Usuario usuario = usuarioRepository.findFirstByEmail(authenticationRequest.getUserName());
		
		 response.getWriter().write(new JSONObject()
		            .put("usuarioId", usuario.getId())
		            .put("role", usuario.getRole())
		            .toString()
		    );
		 
		 response.addHeader("Access-Control-Expose-Headers", "Authorization");
		 response.addHeader("Access-Control-Allow-Headers", "Authorization, "
		 		+ " X-PINGOTHER, Origin, X-Request-With, Content-Type, Accept, X-Custom-header");
		 
		 response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
	}

}
