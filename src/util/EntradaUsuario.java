package util;

import java.util.Scanner;

/**
 * Encapsula o Scanner para leitura de input do usuário via CLI.
 * Demonstra: Encapsulamento de recurso externo, validação de entrada.
 */
public class EntradaUsuario {

    private static final Scanner scanner = new Scanner(System.in);

    private EntradaUsuario() {}

    /**
     * Lê uma linha de texto digitada pelo usuário.
     */
    public static String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Lê um inteiro dentro do intervalo [min, max].
     * Fica em loop até receber um valor válido — demonstra validação de input.
     */
    public static int lerOpcao(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.printf("  ⚠️  Opção inválida. Digite um número entre %d e %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Entrada inválida. Digite apenas números.");
            }
        }
    }

    /**
     * Aguarda o usuário pressionar Enter para continuar.
     */
    public static void aguardarEnter() {
        System.out.print("\n  [ Pressione ENTER para continuar... ]");
        scanner.nextLine();
    }

    /**
     * Fecha o Scanner. Deve ser chamado ao fim do programa.
     */
    public static void fechar() {
        scanner.close();
    }
}
