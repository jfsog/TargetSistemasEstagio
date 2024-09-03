package org.jfsog;

import io.vavr.Tuple;

import java.math.BigInteger;
import java.util.stream.Stream;

public class Estagio_RibeiraoPreto {
    private static final String str = "-".repeat(75);
    public static void main(String[] args) {
        Tecnica1(BigInteger.valueOf(133));
        System.out.println(str);
        Tecnica1(BigInteger.valueOf(144));
        System.out.println(str);
        Tecnica1(BigInteger.valueOf(89));
        Tecnica2("Teste da nossa string que conta a quantidade de A");
        System.out.println(str);
        Tecnica2("Teste da nossa string que conta A quantidade de A");
        System.out.println(str);
        Tecnica3();
        System.out.println(str);
        Tecnica4();
        System.out.println(str);
        Tecnica5();
    }
    private static void Tecnica1(BigInteger numero) {
        boolean isFib = switch (numero.compareTo(BigInteger.ZERO)) {
            case -1 -> false;
            case 0 -> true;
            default -> Stream.iterate(Tuple.of(BigInteger.ZERO, BigInteger.ONE), f -> Tuple.of(f._2, f._1.add(f._2)))
//                                  .peek(System.out::println)
                             .dropWhile(fac -> fac._2().compareTo(numero) < 0)
                             .findFirst()
                             .map(f -> f._2)
                             .map(bi -> bi.compareTo(numero) == 0)
                             .orElse(false);
        };
        System.out.printf("O número %s %s a sequência de Fibonacci%n", numero, isFib ? "pertence" : "não pertence");
    }
    private static void Tecnica2(String string) {
        var count = string.codePoints().filter(c -> c == 'a' || c == 'A').count();
        System.out.printf("O número de vezes que a|A aparece em %s é igual a %d%n", string, count);
    }
    private static void Tecnica3() {
        int INDICE = 12, SOMA = 0, K = 1;
        while (K < INDICE) {
            K = K + 1;
            SOMA = SOMA + K;
        }
        System.out.printf("Soma = %d%n", SOMA); //77
    }
    private static void Tecnica4() {
        // a) 1, 3, 5, 7, ___
        System.out.println("O Próximo na sequência: 1, 3, 5, 7,___ é 9, pois a sequência segue um incremento de 2");
        // b) 2, 4, 8, 16, 32, 64, ____
        System.out.println(
                "O Próximo na sequência: 2, 4, 8, 16, 32, 64, ____ é 128, pois a sequência segue a potências de 2");
        // c) 0, 1, 4, 9, 16, 25, 36, ____
        System.out.println(
                "O Próximo na sequência:  0, 1, 4, 9, 16, 25, 36, ____é 49, pois a sequência segue incrementos que " +
                "por si só incrementam em 2 a partir de 1");
        // d) 4, 16, 36, 64, ____
        System.out.println(
                "O Próximo na sequência: 4, 16, 36, 64, ____é 100, pois a sequência segue incrementos que por si só " +
                "incrementam em 8 com valor inicial de 12 ");
        // e) 1, 1, 2, 3, 5, 8, ____
        System.out.println(
                "O Próximo na sequência: 1, 1, 2, 3, 5, 8, ____ é 13, pois a sequência segue conforme a soma dos 2 " +
                "antecessores, essa é a sequência de fibonnaci");
        // f) 2,10, 12, 16, 17, 18, 19, ____
        System.out.println(
                "O Próximo na sequência: 2,10, 12, 16, 17, 18, 19, ____ parece ser 200, não achei lógica de numerica," +
                " mas é o próximo que começa com a letra \"D\"");
    }
    private static void Tecnica5() {
        System.out.printf("""
                5) Você está em uma sala com três interruptores, cada um conectado a uma lâmpada em salas diferentes\
                . Você não pode ver as lâmpadas da sala em que está, mas pode ligar e desligar os interruptores quantas vezes quiser\
                . Seu objetivo é descobrir qual interruptor controla qual lâmpada. Como você faria para descobrir, usando apenas duas\
                 idas até uma das salas das lâmpadas, qual interruptor controla cada lâmpada?%n""");
        System.out.println("""
                Resposta: O desafio que achei foi transformar o sistema, que considera apenas dois estados: \
                ligado/desligado em um que possua 3 estados. A maneira que percebi é que posso \
                diferenciar entre lampada ligada, desligada e fria e desligada e quente.\
                Dessa forma a forma resolver o problena é acender um interruptor por tempo suficiente para \
                aquece-la, desliga-lo e acender outro interruptor. dessa forma, é possível, ao visitar a \
                primeira sala, distinguir imediatamente 1 dos interruptores e ao visitar o 2ª sala \
                distingui-se os outros 2 interruptores. Dessa forma a lampada apagada e quente é o \
                1º interruptor ligado, a acesa é o 2º interruptor e a apagada e fria é o interruptor restante.""");
    }
}
/*
Técnica:

1) Dado a sequência de Fibonacci, onde se inicia por 0 e 1 e o próximo valor sempre será a soma dos 2 valores
anteriores (exemplo: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34...), escreva um programa na linguagem que desejar onde,
informado um número, ele calcule a sequência de Fibonacci e retorne uma mensagem avisando se o número informado
pertence ou não a sequência.

IMPORTANTE: Esse número pode ser informado através de qualquer entrada de sua preferência ou pode ser previamente
definido no código;

2) Escreva um programa que verifique, em uma string, a existência da letra ‘a’, seja maiúscula ou minúscula, além de
informar a quantidade de vezes em que ela ocorre.

IMPORTANTE: Essa string pode ser informada através de qualquer entrada de sua preferência ou pode ser previamente
definida no código;

3) Observe o trecho de código abaixo: int INDICE = 12, SOMA = 0, K = 1; enquanto K < INDICE faça { K = K + 1; SOMA =
SOMA + K; } imprimir(SOMA);

Ao final do processamento, qual será o valor da variável SOMA?

4) Descubra a lógica e complete o próximo elemento:
a) 1, 3, 5, 7, ___
b) 2, 4, 8, 16, 32, 64, ____
c) 0, 1, 4, 9, 16, 25, 36, ____
d) 4, 16, 36, 64, ____
e) 1, 1, 2, 3, 5, 8, ____
f) 2,10, 12, 16, 17, 18, 19, ____


5) Você está em uma sala com três interruptores, cada um conectado a uma lâmpada em salas diferentes. Você não pode
ver as lâmpadas da sala em que está, mas pode ligar e desligar os interruptores quantas vezes quiser. Seu objetivo é
descobrir qual interruptor controla qual lâmpada. Como você faria para descobrir, usando apenas duas idas até uma das
 salas das lâmpadas, qual interruptor controla cada lâmpada?
* */