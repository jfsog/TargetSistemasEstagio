package org.jfsog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Tecnica1();
        Tecnica2(1500);
        Tecnica2(1597);
        Tecnica3();
    }
    private static void Tecnica1() {
        int INDICE = 13, SOMA = 0, K = 0;
        while (K < INDICE) {
            K++;
            SOMA += K;
        }
        System.out.println("SOMA = " + SOMA); // 91
    }
    private static void Tecnica2(long numero) {
        long f1 = 0, f2 = 1;
        if (numero < 0) {
            System.out.printf("O número %s não pertence a sequência de Fibonacci%n", numero);
        } else if (f1 == numero || f2 == numero) {
            System.out.printf("O número %s pertence a sequência de Fibonacci%n", numero);
        } else {
            long temp;
            System.out.println("0,");
            System.out.println("1,");
            while (f2 < numero) {
                temp = f1;
                f1 = f2;
                f2 += temp;
                if (f2 == numero) {
                    System.out.printf("O número %s pertence a sequência de Fibonacci%n", numero);
                    return;
                }
            }
            System.out.printf("O número %s não pertence a sequência de Fibonacci%n", numero);
        }
    }
    private static void Tecnica3() {
        try (var input = com.sun.tools.javac.Main.class.getClassLoader().getResourceAsStream("faturamento.json")) {
            var obj = new ObjectMapper();
            var root = obj.readTree(input);
            List<Double> valores = new ArrayList<>();
            for (JsonNode node : root.get("dias")) {
                var val = node.get("valor").asDouble();
                if (val > 0.0) valores.add(val);
            }
            var sumario = valores.stream().collect(Collectors.summarizingDouble(Double::doubleValue));
            var maioresQueAMedia = valores.stream().filter(v -> v > sumario.getAverage()).count();
            System.out.printf("O menor valor de faturamento ocorrido em um dia do mês foi de %f%n", sumario.getMin());
            System.out.printf("O maior valor de faturamento ocorrido em um dia do mês foi de %f%n", sumario.getMax());
            System.out.printf(
                    "Número de dias no mês em que o valor de faturamento diário foi superior à média mensal: %d",
                    maioresQueAMedia);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}