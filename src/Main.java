import dungeon.Dungeon;
import personagens.Arqueiro;
import personagens.Guerreiro;
import personagens.Heroi;
import personagens.Mago;
import util.EntradaUsuario;

/**
 * Ponto de entrada do PokéDungeon.
 * Demonstra: Composição de todas as classes, fluxo completo da aplicação CLI.
 *
 * Para compilar:
 *   javac -d out -sourcepath src src/Main.java
 *
 * Para executar:
 *   java -cp out Main
 */
public class Main {

    public static void main(String[] args) {

        exibirTelaInicial();
        EntradaUsuario.aguardarEnter();

        // ── Criação do herói ──────────────────────────────────────────────────
        String nomeHeroi = EntradaUsuario.lerTexto("\n  Digite o nome do seu herói: ");
        if (nomeHeroi.isEmpty()) nomeHeroi = "Aventureiro";

        System.out.println();
        System.out.println("  Escolha sua classe:");
        System.out.println("    [1] ⚔️  Guerreiro  — HP: 120 | ATK: 15 | DEF: 10  →  Golpe Devastador (2× dano)");
        System.out.println("    [2] 🔮  Mago       — HP:  80 | ATK: 25 | DEF:  4  →  Bola de Fogo (queimadura 3 turnos)");
        System.out.println("    [3] 🏹  Arqueiro   — HP: 100 | ATK: 18 | DEF:  6  →  Tiro Certeiro (80% crítico 3×)");
        int classeEscolhida = EntradaUsuario.lerOpcao("  >> ", 1, 3);

        Heroi heroi = criarHeroi(nomeHeroi, classeEscolhida);
        System.out.printf("%n  ✨ %s, o %s, está pronto para a aventura!%n",
                heroi.getNome(), heroi.getClass().getSimpleName());
        System.out.println("  Dica: vença 3 monstros para desafiar o Boss final.");
        EntradaUsuario.aguardarEnter();

        // ── Loop da dungeon ───────────────────────────────────────────────────
        Dungeon dungeon = new Dungeon(heroi);
        dungeon.iniciar();

        // ── Encerramento ──────────────────────────────────────────────────────
        System.out.println();
        System.out.println("  Obrigado por jogar PokéDungeon! Até a próxima aventura.");
        EntradaUsuario.fechar();
    }

    // ── Fábrica de herói baseada na escolha do jogador ────────────────────────

    private static Heroi criarHeroi(String nome, int opcao) {
        switch (opcao) {
            case 1:
                return new Guerreiro(nome);
            case 2:
                return new Mago(nome);
            case 3:
                return new Arqueiro(nome);
            default:
                return new Guerreiro(nome); // fallback
        }
    }

    // ── Arte ASCII da tela de abertura ────────────────────────────────────────

    private static void exibirTelaInicial() {
        System.out.println();
        System.out.println("  ██████╗  ██████╗ ██╗  ██╗███████╗");
        System.out.println("  ██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝");
        System.out.println("  ██████╔╝██║   ██║█████╔╝ █████╗  ");
        System.out.println("  ██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ");
        System.out.println("  ██║     ╚██████╔╝██║  ██╗███████╗ ");
        System.out.println("  ╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝");
        System.out.println();
        System.out.println("  ██████╗ ██╗   ██╗███╗   ██╗ ██████╗ ███████╗ ██████╗ ███╗   ██╗");
        System.out.println("  ██╔══██╗██║   ██║████╗  ██║██╔════╝ ██╔════╝██╔═══██╗████╗  ██║");
        System.out.println("  ██║  ██║██║   ██║██╔██╗ ██║██║  ███╗█████╗  ██║   ██║██╔██╗ ██║");
        System.out.println("  ██║  ██║██║   ██║██║╚██╗██║██║   ██║██╔══╝  ██║   ██║██║╚██╗██║");
        System.out.println("  ██████╔╝╚██████╔╝██║ ╚████║╚██████╔╝███████╗╚██████╔╝██║ ╚████║");
        System.out.println("  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚═╝  ╚═══╝");
        System.out.println();
        System.out.println("         ~~~ Um RPG de batalha por turnos no terminal ~~~");
        System.out.println("         ~~~     Você conseguirá vencer o Boss?       ~~~");
        System.out.println();
        System.out.println("                  🐲  💀  🧟  🕷️   👹");
        System.out.println();
    }
}
