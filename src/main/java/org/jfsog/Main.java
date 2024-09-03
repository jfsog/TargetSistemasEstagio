package org.jfsog;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var espaco = "-".repeat(75);
        Tecnica1();
        System.out.println(espaco);
        Tecnica2(1500);
        System.out.println(espaco);
        Tecnica2(1597);
        System.out.println(espaco);
        Tecnica3();
        System.out.println(espaco);
        Tecnica4();
        System.out.println(espaco);
        Tecnica5();
        System.out.println(espaco);
        Tecnica5("Outra string para inverter");
    }
    private static void Tecnica1() {
        int INDICE = 13, SOMA = 0, K = 0;
        while (K < INDICE) {
            K++;
            SOMA += K;
        }
        System.out.printf("SOMA = %d%n", SOMA); // 91
    }
    private static void Tecnica2(long numero) {
        long f1 = 0, f2 = 1;
        if (numero < 0) {
            System.out.printf("O número %s não pertence a sequência de Fibonacci%n", numero);
        } else if (f1 == numero || f2 == numero) {
            System.out.printf("O número %s pertence a sequência de Fibonacci%n", numero);
        } else {
            long temp;
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
        Gson gson = new Gson();
        var classLoader = Main.class.getClassLoader();
        var dadosJsonUrl = classLoader.getResource("dados.json");
        var jsonFileName = Optional.ofNullable(dadosJsonUrl).map(URL::getPath).orElse("");
        System.out.println("Lidos a partir do json:");
        try (var reader = new FileReader(jsonFileName)) {
            Type fluxoDeCaixa = new TypeToken<ArrayList<CaixaDiario>>() {}.getType();
            List<CaixaDiario> floxoJson = gson.fromJson(reader, fluxoDeCaixa);
            PrintFluxoTecnica3(floxoJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        XmlMapper mapper = new XmlMapper();
        var DadosXmlUrl = classLoader.getResource("dados.xml");
        var xmlFileName = Optional.ofNullable(DadosXmlUrl).map(URL::getPath).orElse("");
        System.out.println("Lidos a partir do xml:");
        try {
            File xmlFile = new File(xmlFileName);
            List<CaixaDiario> fluxoXml = mapper.readValue(xmlFile,
                    mapper.getTypeFactory().constructCollectionType(List.class, CaixaDiario.class));
            PrintFluxoTecnica3(fluxoXml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void Tecnica4() {
        List<Faturamento> faturamentos = List.of(new Faturamento("SP", 67836.43),
                new Faturamento("RJ", 36678.66),
                new Faturamento("MG", 29229.88),
                new Faturamento("ES", 27165.48),
                new Faturamento("Outros", 19849.53));
        faturamentos.forEach(System.out::println);
        var total = faturamentos.stream().mapToDouble(Faturamento::faturamento).sum();
        System.out.println("Valor total: " + total);
        faturamentos.forEach(f -> System.out.printf("Estado: %s, Percentual relativo: %.2f%%%n",
                f.estado(),
                f.faturamento() / total));
    }
    private static void Tecnica5() {
        Tecnica5("Teste de string para reverter");
    }
    private static void Tecnica5(String string) {
        var codePoints = string.codePoints().toArray();
        for (int init = 0, end = codePoints.length - 1; init < end; init++, end--) {
            var t = codePoints[init];
            codePoints[init] = codePoints[end];
            codePoints[end] = t;
        }
        var stringFinal = new String(codePoints, 0, codePoints.length);
        System.out.println("string normal   :   " + string);
        System.out.println("string invertida:   " + stringFinal);
        assert stringFinal.contentEquals(new StringBuilder(string).reverse());
    }
    private static void PrintFluxoTecnica3(List<CaixaDiario> fluxoDeCaixa) {
        var sumario = fluxoDeCaixa.stream()
                                  .map(CaixaDiario::getValor)
                                  .filter(v -> v > 0.0)
                                  .collect(Collectors.summarizingDouble(Double::doubleValue));
        var maioresQueAMedia = fluxoDeCaixa.stream().filter(v -> v.getValor() > sumario.getAverage()).count();
        System.out.printf("O menor valor de faturamento ocorrido em um dia do mês foi de %f%n", sumario.getMin());
        System.out.printf("O maior valor de faturamento ocorrido em um dia do mês foi de %f%n", sumario.getMax());
        System.out.printf("Média mensal de faturamento :%f%n", sumario.getAverage());
        System.out.printf("Dias com faturamento diário superior à média mensal: %d%n", maioresQueAMedia);
    }
}

record Faturamento(String estado, double faturamento) {}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CaixaDiario {
    @JacksonXmlProperty(localName = "dia")
    private int dia;
    @JacksonXmlProperty(localName = "valor")
    private double valor;
}
