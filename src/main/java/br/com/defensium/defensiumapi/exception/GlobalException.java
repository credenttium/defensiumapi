package br.com.defensium.defensiumapi.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.defensium.defensiumapi.DateUtility;
import br.com.defensium.defensiumapi.transfer.RespostaRequisicaoTransfer;

@RestControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaRequisicaoTransfer> handleValidation(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<String> erroList = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RespostaRequisicaoTransfer respostaRequisicaoTransfer = new RespostaRequisicaoTransfer();
            respostaRequisicaoTransfer.setSituacao(String.valueOf(HttpStatus.BAD_REQUEST.value()).concat(" - ").concat(HttpStatus.BAD_REQUEST.getReasonPhrase()));
            respostaRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));
            respostaRequisicaoTransfer.setErroList(erroList);

            log.error("{}", imprimirLog(respostaRequisicaoTransfer));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaRequisicaoTransfer);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespostaRequisicaoTransfer> dataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {

        RespostaRequisicaoTransfer respostaRequisicaoTransfer = new RespostaRequisicaoTransfer();
            respostaRequisicaoTransfer.setSituacao(String.valueOf(HttpStatus.CONFLICT.value()).concat(" - ").concat(HttpStatus.CONFLICT.getReasonPhrase()));
            respostaRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));
            respostaRequisicaoTransfer.setErroList(List.of("Erro de integridade no banco de dados!"));

            recuperarMensagemExcecao(dataIntegrityViolationException, respostaRequisicaoTransfer);
            
            log.error("{}", imprimirLog(respostaRequisicaoTransfer));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(respostaRequisicaoTransfer);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaRequisicaoTransfer> httpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {

        RespostaRequisicaoTransfer respostaRequisicaoTransfer = new RespostaRequisicaoTransfer();
            respostaRequisicaoTransfer.setSituacao(String.valueOf(HttpStatus.BAD_REQUEST.value()).concat(" - ").concat(HttpStatus.BAD_REQUEST.getReasonPhrase()));
            respostaRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));
            respostaRequisicaoTransfer.setErroList(List.of("Estrutura errada. Verifique o formato do arquivo JSON!"));

            log.error("{}", imprimirLog(respostaRequisicaoTransfer));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaRequisicaoTransfer);
    }

    private void recuperarMensagemExcecao(
            DataIntegrityViolationException dataIntegrityViolationException,
            RespostaRequisicaoTransfer respostaRequisicaoTransfer) {

        Throwable rootCause = dataIntegrityViolationException.getRootCause();

        String detalheMensagem;

        if (rootCause != null) {
            String mensagem = rootCause.getMessage().toLowerCase();
            if (mensagem.contains("duplicate key") || mensagem.contains("already exists")) {
                detalheMensagem = "O registro informado já existe na Base de Dados!";
            } else {
                detalheMensagem = rootCause.getMessage();
            }
        } else {
            detalheMensagem = dataIntegrityViolationException.getMessage();
        }

        respostaRequisicaoTransfer.setErroList(List.of(detalheMensagem));
    }

    private String imprimirLog(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "[Erro ao converter objeto para JSON]";
        }
    }

}
