package br.com.guifroes1984.SistemaReservaServico.services.authentication;

import br.com.guifroes1984.SistemaReservaServico.dto.InscreverRequestDTO;
import br.com.guifroes1984.SistemaReservaServico.dto.UsuarioDto;

public interface AuthService {
	
	UsuarioDto inscricaoCliente(InscreverRequestDTO inscreverRequestDTO);

}
