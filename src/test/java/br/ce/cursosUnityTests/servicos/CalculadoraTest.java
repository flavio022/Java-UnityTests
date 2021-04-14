package br.ce.cursosUnityTests.servicos;

import br.ce.cursosUnityTests.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
    private Calculadora calc;

    @Before
    public void setup(){
        calc = new Calculadora();
    }
    @Test
    public void deveSomarDoisValores(){
        int a = 5;
        int b = 4;

        int resultado = calc.somar(a,b);

        Assert.assertEquals(9,resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){
        int a = 4;
        int b = 1;


        int resultado = calc.subtrair(a,b);

        Assert.assertEquals(3,resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        int a = 6;
        int b = 2;

        int resultado = calc.dividir(a,b);

        Assert.assertEquals(3,resultado);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
        int a = 10;
        int b = 0;

        int resultado = calc.dividir(a,b);

    }
}
