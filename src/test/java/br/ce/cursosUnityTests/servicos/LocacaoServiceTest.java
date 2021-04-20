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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;

    @Before
    public void setup(){
        locacaoService = new LocacaoService();
        LocacaoDao locacaoDao = new LocacaoDaoFake();
        locacaoService.setLocacaoDao(locacaoDao);
    }

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void deveAlugarFilme() throws Exception {

        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));
        //cenario

        Usuario usuario = UsuarioBuilder.umUsuario().agora();

        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

        //acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //verificacao

        error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),CoreMatchers.is(true));
        error.checkThat(locacao.getDataRetorno(),MatchersProprios.ehHoje());
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),CoreMatchers.is(true));
        error.checkThat(locacao.getDataRetorno(),MatchersProprios.ehHojeComDiferencaDeDias(1));
    }

    @Test(expected = FilmesSemEstoqueExceptions.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception{
        //cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().agora());

        //acao
        locacaoService.alugarFilme(usuario, filmes);
    }
    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmesSemEstoqueExceptions{

        //Cenario
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        //Acao
        try {
            locacaoService.alugarFilme(null,filmes);
            Assert.fail();
        }
        catch (LocadoraException e){
            Assert.assertThat(e.getMessage(),CoreMatchers.is("Usuario vazio"));
        }
        System.out.println("Forma robusta");
    }

    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmesSemEstoqueExceptions,LocadoraException{
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");
        locacaoService.alugarFilme(usuario,null);

        System.out.println("Forma Nova");
    }


    @Test
    public void devePagar75PctNoFilme3() throws FilmesSemEstoqueExceptions, LocadoraException{
        //Cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0),
                new Filme("Filme 3",2,4.0));

        //Acao
        Locacao resultado = locacaoService.alugarFilme(usuario,filmes);

        //Verificacao
        Assert.assertThat(resultado.getValor(),CoreMatchers.is(11.0));

    }

    @Test
    public void devePagar50PctNoFilme4() throws FilmesSemEstoqueExceptions,LocadoraException{
        //Cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0),
                new Filme("Filme 3",2,4.0),
                new Filme("Filme 3",2,4.0));

        //Acao
        Locacao resultado = locacaoService.alugarFilme(usuario,filmes);

        //Verificacao
        Assert.assertThat(resultado.getValor(),CoreMatchers.is(13.0));
    }

    @Test
    public void devePagar25PctNoFilme5() throws FilmesSemEstoqueExceptions,LocadoraException{
        //Cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();

        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0),
                new Filme("Filme 3",2,4.0),
                new Filme("Filme 4",2,4.0),
                new Filme("Filme 5",2,4.0));

        //Acao
        Locacao resultado = locacaoService.alugarFilme(usuario,filmes);

        //Verificacao
        Assert.assertThat(resultado.getValor(),CoreMatchers.is(14.0));
    }


    @Test
    public void devePagar0PctNoFilme6() throws FilmesSemEstoqueExceptions,LocadoraException{
        //Cenario
        Usuario usuario = UsuarioBuilder.umUsuario().agora();

        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0),
                new Filme("Filme 3",2,4.0),
                new Filme("Filme 4",2,4.0),
                new Filme("Filme 5",2,4.0),
                new Filme("Filme 6",2,4.0));


        //Acao
        Locacao resultado = locacaoService.alugarFilme(usuario,filmes);

        //Verificacao
        Assert.assertThat(resultado.getValor(),CoreMatchers.is(14.0));
    }

    @Test
    public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmesSemEstoqueExceptions, LocadoraException{
        //Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

        //Cenario
        Usuario usuario = new Usuario("Usuario");
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

        //Acao
        Locacao retorno = locacaoService.alugarFilme(usuario,filmes);

        //Verificacao

        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        Assert.assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
        Assert.assertThat(retorno.getDataRetorno(),MatchersProprios.caiEmUmaSegunda());
        // Assert.assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
        // Assert.assertTrue(ehSegunda);
    }
}
