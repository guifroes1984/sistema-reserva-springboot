package br.com.guifroes1984.SistemaReservaServico.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guifroes1984.SistemaReservaServico.dto.InscreverRequestDTO;
import br.com.guifroes1984.SistemaReservaServico.dto.UsuarioDto;
import br.com.guifroes1984.SistemaReservaServico.entity.Usuario;
import br.com.guifroes1984.SistemaReservaServico.enums.UsuarioRole;
import br.com.guifroes1984.SistemaReservaServico.repository.UsuarioRepository;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UsuarioDto inscricaoCliente(InscreverRequestDTO inscreverRequestDTO) {
		Usuario usuario = new Usuario();
		
		usuario.setNome(inscreverRequestDTO.getNome());
		usuario.setSobreNome(inscreverRequestDTO.getSobreNome());
		usuario.setEmail(inscreverRequestDTO.getEmail());
		usuario.setTelefone(inscreverRequestDTO.getTelefone());
		usuario.setPassword(inscreverRequestDTO.getPassword());
		
		usuario.setRole(UsuarioRole.CLIENTE);
		
		return usuarioRepository.save(usuario).getDto();
	}
	
	public Boolean presentePorEmail(String email) {
		return usuarioRepository.findFirstByEmail(email) != null;
	}
	
	public UsuarioDto inscricaoEmpresa(InscreverRequestDTO inscreverRequestDTO) {
		Usuario usuario = new Usuario();
		
		usuario.setNome(inscreverRequestDTO.getNome());
		usuario.setEmail(inscreverRequestDTO.getEmail());
		usuario.setTelefone(inscreverRequestDTO.getTelefone());
		usuario.setPassword(inscreverRequestDTO.getPassword());
		
		usuario.setRole(UsuarioRole.EMPRESA);
		
		return usuarioRepository.save(usuario).getDto();
	}

}
