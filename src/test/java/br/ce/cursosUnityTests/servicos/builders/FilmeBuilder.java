package br.ce.cursosUnityTests.servicos.builders;

import br.ce.cursosUnityTests.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;
    private FilmeBuilder(){}

    public static FilmeBuilder umFilme(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("Vingadores");
        builder.filme.setEstoque(2);
        builder.filme.setPrecoLocacao(4.0);

        return builder;
    }

    public static FilmeBuilder umFilmeSemEstoque(){
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setNome("Vingadores");
        builder.filme.setEstoque(0);
        builder.filme.setPrecoLocacao(4.0);

        return builder;
    }
    public FilmeBuilder semEstoque(){
        filme.setEstoque(0);
        return this;
    }
    public FilmeBuilder comValor(Double valor){
        filme.setPrecoLocacao(valor);
        return this;
    }

    public Filme agora(){
        return filme;
    }

}
