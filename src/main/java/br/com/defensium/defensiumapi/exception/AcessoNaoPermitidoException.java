package br.com.defensium.defensiumapi.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.defensium.defensiumapi.DateUtility;
import br.com.defensium.defensiumapi.transfer.RespostaRequisicaoTransfer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AcessoNaoPermitidoException implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
        HttpServletRequest httpServletRequest, 
        HttpServletResponse httpServletResponse,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        httpServletResponse.setStatus(httpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        RespostaRequisicaoTransfer mensagemRequisicaoTransfer = new RespostaRequisicaoTransfer();
            mensagemRequisicaoTransfer.setMensagem("Acesso Negado: Usuário sem permissão para acessar essa funcionalidade!");
            mensagemRequisicaoTransfer.setSituacao(HttpStatus.FORBIDDEN.toString());
            mensagemRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(mensagemRequisicaoTransfer));
        httpServletResponse.getWriter().flush();
    }

}
