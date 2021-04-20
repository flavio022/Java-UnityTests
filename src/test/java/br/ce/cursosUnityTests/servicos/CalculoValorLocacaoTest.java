package br.ce.cursosUnityTests.servicos;

import br.ce.cursosUnityTests.daos.LocacaoDao;
import br.ce.cursosUnityTests.daos.LocacaoDaoFake;
import br.ce.cursosUnityTests.entidades.Filme;
import br.ce.cursosUnityTests.entidades.Locacao;
import br.ce.cursosUnityTests.entidades.Usuario;
import br.ce.cursosUnityTests.exceptions.FilmesSemEstoqueExceptions;
import br.ce.cursosUnityTests.exceptions.LocadoraException;
import br.ce.cursosUnityTests.servicos.builders.FilmeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService service;

    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
        public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    @Before
    public void setup() {

        service = new LocacaoService();
        LocacaoDao locacaoDao = new LocacaoDaoFake();
        service.setLocacaoDao(locacaoDao);
    }

    private static Filme filme1 = FilmeBuilder.umFilme().agora();
    private static Filme filme2 = FilmeBuilder.umFilme().agora();
    private static Filme filme3 = FilmeBuilder.umFilme().agora();
    private static Filme filme4 = FilmeBuilder.umFilme().agora();
    private static Filme filme5 = FilmeBuilder.umFilme().agora();
    private static Filme filme6 = FilmeBuilder.umFilme().agora();
    private static Filme filme7 = FilmeBuilder.umFilme().agora();


    @Parameterized.Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmesSemEstoqueExceptions, LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Usuario 1");

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(valorLocacao));
    }
}


