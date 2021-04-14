package br.ce.cursosUnityTests.servicos;

import static br.ce.cursosUnityTests.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;


public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueExceptions, LocadoraException {

		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}
		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme Vazio");
		}
		Locacao locacao = new Locacao();

		for(Filme filme : filmes ) {
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueExceptions();
			}
			locacao.setValor(filme.getPrecoLocacao());
		}


		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

}