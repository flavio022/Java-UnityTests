package br.ce.cursosUnityTests.servicos;

import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;
import br.ce.cursosUnityTests.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;

    @Before
    public void setup(){
        locacaoService = new LocacaoService();
    }

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void teste() {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme",1,5.0));
        //acao
        Locacao locacao;
        try{
            locacao = locacaoService.alugarFilme(usuario, filmes);

            //verificacao
            error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
            error.checkThat(locacao.getValor(),CoreMatchers.is(CoreMatchers.not(6.0)));
            error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),CoreMatchers.is(true));
            error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),CoreMatchers.is(true));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test(expected = FilmesSemEstoqueExceptions.class)
    public void testLocacao_filmeSemEstoque() throws Exception{
        List<Filme> filmes = Arrays.asList(new Filme("Filme",0,4.0));

        //cenario
        Usuario usuario = new Usuario("Usuario 1");

        //acao
        locacaoService.alugarFilme(usuario, filmes);

        System.out.println("Forma Elegante");
    }
    @Test
    public void testLocacao_usuarioVazio() throws FilmesSemEstoqueExceptions{

        //Cenario
        List<Filme> filmes = Arrays.asList(new Filme("Filme",1,4.0));

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
    public void testLocacao_FilmeVazio() throws FilmesSemEstoqueExceptions,LocadoraException{
        Usuario usuario = new Usuario("Usuario");
        List<Filme> filmes = Arrays.asList(new Filme("Filme",1,4.0));

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme Vazio");
        locacaoService.alugarFilme(usuario,null);

        System.out.println("Forma Nova");
    }
}
