package br.ce.cursosUnityTests.servicos;

import br.ce.cursosUnityTests.daos.LocacaoDao;
import br.ce.cursosUnityTests.daos.LocacaoDaoFake;
import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;
import br.ce.cursosUnityTests.servicos.builders.FilmeBuilder;
import br.ce.cursosUnityTests.servicos.builders.UsuarioBuilder;
import br.ce.cursosUnityTests.servicos.matcher.MatchersProprios;
import br.ce.cursosUnityTests.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


import static br.ce.cursosUnityTests.servicos.builders.FilmeBuilder.umFilme;
import static br.ce.cursosUnityTests.servicos.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.cursosUnityTests.servicos.builders.UsuarioBuilder.umUsuario;
import static br.ce.cursosUnityTests.servicos.matcher.MatchersProprios.caiEmUmaSegunda;
import static br.ce.cursosUnityTests.servicos.matcher.MatchersProprios.ehHoje;
import static br.ce.cursosUnityTests.servicos.matcher.MatchersProprios.ehHojeComDiferencaDeDias;

import java.util.*;

public class LocacaoServiceTest {
    private LocacaoService service;

    private SPCService spc;
    private LocacaoDao dao;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup(){
        service = new LocacaoService();
        dao = Mockito.mock(LocacaoDao.class);
        service.setLocacaoDao(dao);
        spc = Mockito.mock(SPCService.class);
        service.setSpcService(spc);
    }

    @Test
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(1));
    }

    @Test(expected = FilmesSemEstoqueExceptions.class)
    public void naoDeveAlugarFilmeSemEstoque() throws Exception{
        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueExceptions{
        //cenario
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueExceptions, LocadoraException{
        //cenario
        Usuario usuario = umUsuario().agora();

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao
        service.alugarFilme(usuario, null);
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmesSemEstoqueExceptions, LocadoraException{
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        Usuario usuario = umUsuario().agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(retorno.getDataRetorno(), caiEmUmaSegunda());

    }

    @Test
    public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmesSemEstoqueExceptions, LocadoraException{
        //cenario
        Usuario usuario = umUsuario().agora();
        Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
        List<Filme> filmes = Arrays.asList(umFilme().agora());

        when(spc.possuiNegativacao(usuario)).thenReturn(true);

        exception.expect(LocadoraException.class);
        exception.expectMessage("Usuário Negativado");

        //acao
        service.alugarFilme(usuario, filmes);
    }


}
