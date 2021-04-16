package br.ce.cursosUnityTests.servicos.suites;

import br.ce.cursosUnityTests.servicos.Calculadora;
import br.ce.cursosUnityTests.servicos.CalculadoraTest;
import br.ce.cursosUnityTests.servicos.CalculoValorLocacaoTest;
import br.ce.cursosUnityTests.servicos.LocacaoServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CalculadoraTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class})
public class SuiteExecucao {

}
