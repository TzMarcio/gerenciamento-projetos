package br.com.tzmarcio.gerenciamento_projetos.enums;

public enum Risco {

    BAIXO_RISCO("Baixo Risco"),
    MEDIO_RISCO("Medio Risco"),
    ALTO_RISCO("Alto Risco");

    private String descricao;

    Risco(final String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

}
