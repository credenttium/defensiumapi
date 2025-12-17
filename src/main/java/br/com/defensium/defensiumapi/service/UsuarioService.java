package br.com.defensium.defensiumapi.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.defensium.defensiumapi.entity.UsuarioEntity;
import br.com.defensium.defensiumapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity cadastrarUsuario(UsuarioEntity usuarioEntity) {
        usuarioEntity.setSenha(this.passwordEncoder.encode(usuarioEntity.getSenha()));
        return this.usuarioRepository.save(usuarioEntity);
    }

    public UsuarioEntity recuperarUsuario(Long codigoUsuario) {
        Optional<UsuarioEntity> usuarioEntityOptional = this.usuarioRepository.findById(codigoUsuario);
        return usuarioEntityOptional.isPresent() ? usuarioEntityOptional.get() : new UsuarioEntity();
    }

}
