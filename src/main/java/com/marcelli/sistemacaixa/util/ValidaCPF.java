package com.marcelli.sistemacaixa.util;

public class ValidaCPF {

    public static boolean isCPF(String CPF){

        if(CPF.length() != 11 ||
           CPF.matches("(\\d)\\1{10}")){
            return false;
        }

        int dig10 = Character.getNumericValue(CPF.charAt(9));
        int dig11 = Character.getNumericValue(CPF.charAt(10));

        int sm, peso, primeiroDigito, segundoDigito, resto;

        //calculo 1º digito

        peso = 10;
        sm = 0;
        for(int digitoAtual = 0; digitoAtual <9; digitoAtual++){
            int digito = Character.getNumericValue(CPF.charAt(digitoAtual));
            sm += digito * peso;
            peso--;
        }
        resto = sm%11;

        if(resto == 10 || resto == 11) primeiroDigito = 0;
        else primeiroDigito = 11 - resto;

        //calculo 2º digito

        peso = 11;
        sm = 0;
        for(int digitoAtual = 0; digitoAtual <10; digitoAtual++){
            sm += Character.getNumericValue(CPF.charAt(digitoAtual)) * peso;
            peso--;
        }
        resto = sm%11;
        if(resto == 10 || resto == 11) segundoDigito = 0;
        else segundoDigito = 11 - resto;

        return dig10 == primeiroDigito && dig11 == segundoDigito;
    }


}
