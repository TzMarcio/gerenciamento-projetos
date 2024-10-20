package br.com.tzmarcio.gerenciamento_projetos.enums;

public enum Status {

    EM_ANALISE("EM ANALISE"),
    ANALISE_REALIZADA("ANALISE REALIZADA"),
    ANALISE_APROVADA("ANALISE APROVADA"),
    INICIADO("INICIADO"),
    PLANEJADO("PLANEJADO"),
    EM_ANDAMENTO("EM ANDAMENTO"),
    ENCERRADO("ENCERRADO"),
    CANCELADO("CANCELADO");

    private String descricao;

    Status(final String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

}
