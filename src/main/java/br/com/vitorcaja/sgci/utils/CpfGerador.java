package br.com.vitorcaja.sgci.utils;

import java.util.Random;

public class CpfGerador {
    public static String gerarCpf() {
        Random random = new Random();

        // Gera os 9 primeiros dígitos aleatórios
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpf.append(random.nextInt(10));
        }

        // Calcula os dois dígitos verificadores
        int digito1 = calcularDigitoVerificador(cpf.toString(), 10);
        int digito2 = calcularDigitoVerificador(cpf.toString() + digito1, 11);

        // Adiciona os dois dígitos verificadores ao CPF
        cpf.append(digito1).append(digito2);

        return cpf.toString();
    }

    private static int calcularDigitoVerificador(String cpfBase, int peso) {
        int soma = 0;
        for (int i = 0; i < cpfBase.length(); i++) {
            soma += Character.getNumericValue(cpfBase.charAt(i)) * peso--;
        }
        int resto = soma % 11;
        if (resto < 2) {
            return 0;
        } else {
            return 11 - resto;
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        System.out.println(gerarCpf());
    }
}
