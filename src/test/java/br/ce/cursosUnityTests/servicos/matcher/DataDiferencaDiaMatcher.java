package br.ce.cursosUnityTests.servicos.matcher;

import br.ce.cursosUnityTests.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DataDiferencaDiaMatcher extends TypeSafeMatcher <Date> {
    private Integer qtdDias;

    public DataDiferencaDiaMatcher(Integer qtdDias){
        this.qtdDias = qtdDias;
    }
    @Override
    public void describeTo(Description description) {

    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.isMesmaData(date,DataUtils.obterDataComDiferencaDias(qtdDias));
    }
}
