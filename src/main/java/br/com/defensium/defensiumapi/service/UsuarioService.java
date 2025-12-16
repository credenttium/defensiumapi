package br.com.defensium.defensiumapi.service;

import org.springframework.stereotype.Service;

import br.com.defensium.defensiumapi.entity.UsuarioEntity;
import br.com.defensium.defensiumapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
        return this.usuarioRepository.save(usuarioEntity);
    }

}
