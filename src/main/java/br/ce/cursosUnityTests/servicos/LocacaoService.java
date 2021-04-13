package br.ce.cursosUnityTests.servicos;

import static br.ce.cursosUnityTests.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws FilmesSemEstoqueExceptions, LocadoraException {

		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}
		if(filme == null){
			throw new LocadoraException("Filme Vazio");
		}
		if(filme.getEstoque() == 0) {
			throw new FilmesSemEstoqueExceptions();
		}

		Locacao locacao = new Locacao();

		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}