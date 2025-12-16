package br.com.defensium.defensiumapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.defensium.defensiumapi.entity.UsuarioEntity;
import br.com.defensium.defensiumapi.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public UsuarioEntity cadastrarUsuario(@RequestBody UsuarioEntity usuarioEntity) {
        return this.usuarioService.cadastrarUsuario(usuarioEntity);
    }

    @GetMapping("/{codigoUsuario}")
    public UsuarioEntity recuperarUsuario(@RequestParam Long codigoUsuario) {
        return this.usuarioService.recuperarUsuario(codigoUsuario);
    }

}
