package br.com.defensium.defensiumapi.transfer;

import java.util.List;

public class RespostaRequisicaoTransfer {

    private String situacao;

    private String dataHoraRequisicao;

    private List<String> erroList;

    public RespostaRequisicaoTransfer() {}

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getDataHoraRequisicao() {
        return dataHoraRequisicao;
    }

    public void setDataHoraRequisicao(String dataHoraRequisicao) {
        this.dataHoraRequisicao = dataHoraRequisicao;
    }

    public List<String> getErroList() {
        return erroList;
    }

    public void setErroList(List<String> erroList) {
        this.erroList = erroList;
    }

}
