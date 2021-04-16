package br.ce.cursosUnityTests.servicos.matcher;

import java.util.Calendar;

public class MatchersProprios {

    public static DiaSemanaMatcher caiEm(Integer diaSemana){
        return new DiaSemanaMatcher(diaSemana);
    }

    public static DiaSemanaMatcher caiEmUmaSegunda(){
        return new DiaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataDiferencaDiaMatcher ehHojeComDiferencaDeDias(Integer qtdDias){
        return new DataDiferencaDiaMatcher(qtdDias);
    }
    public static DataDiferencaDiaMatcher ehHoje(){
        return new DataDiferencaDiaMatcher(0);
    }
}
