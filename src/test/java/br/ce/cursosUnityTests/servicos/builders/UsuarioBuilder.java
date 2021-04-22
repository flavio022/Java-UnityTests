package br.ce.cursosUnityTests.servicos.builders;

import br.ce.cursosUnityTests.entidades.Usuario;

public class UsuarioBuilder {
    private Usuario usuario;

    private UsuarioBuilder(){

    }

    public static UsuarioBuilder umUsuario(){
        UsuarioBuilder builder =  new UsuarioBuilder();
        builder.usuario = new Usuario();
        builder.usuario.setNome("Usuario");
        return builder;
    }

    public Usuario agora(){
        return usuario;
    }

    public UsuarioBuilder comNome(String nome) {
        usuario.setNome(nome);
        return this;
    }
}
