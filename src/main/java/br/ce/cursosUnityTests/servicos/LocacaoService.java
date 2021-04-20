package br.ce.cursosUnityTests.servicos;

import static br.ce.cursosUnityTests.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.cursosUnityTests.daos.LocacaoDao;
import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;
import br.ce.cursosUnityTests.utils.DataUtils;


public class LocacaoService {

	private LocacaoDao dao;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmesSemEstoqueExceptions, LocadoraException {

		if(usuario == null){
			throw new LocadoraException("Usuario vazio");
		}
		if(filmes == null || filmes.isEmpty()){
			throw new LocadoraException("Filme Vazio");
		}

		for(Filme filme : filmes ) {
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueExceptions();
			}
		}

		for(Filme filme: filmes){
			if(filme.getEstoque() == 0){
				throw new FilmesSemEstoqueExceptions();
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;

		for(int i=0;i< filmes.size();i++){
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();
			switch (i){
				case 2: valorFilme = valorFilme*0.75; break;
				case 3: valorFilme = valorFilme*0.5; break;
				case 4: valorFilme = valorFilme*0.25; break;
				case 5: valorFilme = valorFilme*0; break;

			}
			valorTotal += valorFilme;
		}
		locacao.setValor(valorTotal);
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
			dataEntrega = adicionarDias(dataEntrega,1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		dao.salvar(locacao);

		return locacao;
	}
	public void setLocacaoDao(LocacaoDao dao){
		this.dao = dao;
	}

}