package br.com.defensium.defensiumapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import br.com.defensium.defensiumapi.exception.AcessoNaoPermitidoException;

@Configuration
@EnableWebSecurity
public class SegurancaConfiguration {

    private final AcessoNaoPermitidoException acessoNaoPermitidoException;

    public SegurancaConfiguration(AcessoNaoPermitidoException acessoNaoPermitidoException) {
        this.acessoNaoPermitidoException = acessoNaoPermitidoException;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/usuario/registrar").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception
                    .accessDeniedHandler(acessoNaoPermitidoException)
                );
        return httpSecurity.build();
    }

}
