package br.com.defensium.defensiumapi.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.defensium.defensiumapi.DateUtility;
import br.com.defensium.defensiumapi.transfer.RespostaRequisicaoTransfer;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaRequisicaoTransfer> handleValidation(MethodArgumentNotValidException methodArgumentNotValidException) {

        List<String> erroList = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        RespostaRequisicaoTransfer respostaRequisicaoTransfer = new RespostaRequisicaoTransfer();
            respostaRequisicaoTransfer.setMensagem("Erro ao tentar realizar as validações do recurso enviado!");
            respostaRequisicaoTransfer.setSituacao(HttpStatus.BAD_REQUEST.getReasonPhrase());
            respostaRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));
            respostaRequisicaoTransfer.setErroList(erroList);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respostaRequisicaoTransfer);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespostaRequisicaoTransfer> dataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {

        RespostaRequisicaoTransfer respostaRequisicaoTransfer = new RespostaRequisicaoTransfer();
            respostaRequisicaoTransfer.setMensagem("Erro de integridade no banco de dados!");
            respostaRequisicaoTransfer.setSituacao(String.valueOf(HttpStatus.CONFLICT.value()).concat(" - ").concat(HttpStatus.CONFLICT.getReasonPhrase()));
            respostaRequisicaoTransfer.setDataHoraRequisicao(DateUtility.getDataAtualFormatada(DateUtility.getFormato01()));

            recuperarMensagemExcecao(dataIntegrityViolationException, respostaRequisicaoTransfer);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(respostaRequisicaoTransfer);
    }

    private void recuperarMensagemExcecao(
            DataIntegrityViolationException dataIntegrityViolationException,
            RespostaRequisicaoTransfer respostaRequisicaoTransfer) {

        Throwable rootCause = dataIntegrityViolationException.getRootCause();

        String detalheMensagem;

        if (rootCause != null) {
            String mensagem = rootCause.getMessage().toLowerCase();
            if (mensagem.contains("duplicate key") || mensagem.contains("already exists")) {
                detalheMensagem = "O registro informado já existe no banco de dados!";
            } else {
                detalheMensagem = rootCause.getMessage();
            }
        } else {
            detalheMensagem = dataIntegrityViolationException.getMessage();
        }

        respostaRequisicaoTransfer.setErroList(List.of(detalheMensagem));
    }

}
