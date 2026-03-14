package dungeon;

import batalha.Batalha;
import personagens.Heroi;
import personagens.Monstro;
import util.EntradaUsuario;

/**
 * Gerencia a progressão pela dungeon: salas, boss e placar final.
 * Demonstra: Composição com Batalha, controle de fluxo, estado do jogo.
 */
public class Dungeon {

    private static final int SALAS_ANTES_DO_BOSS = 3;

    private final Heroi heroi;
    private int sala;
    private int monstrosVencidos;
    private int fugas;
    private boolean jogoAtivo;

    public Dungeon(Heroi heroi) {
        this.heroi           = heroi;
        this.sala            = 1;
        this.monstrosVencidos = 0;
        this.fugas            = 0;
        this.jogoAtivo        = true;
    }

    // ── Loop principal da dungeon ─────────────────────────────────────────────

    public void iniciar() {
        System.out.println();
        System.out.printf("  ✨ %s, o %s, adentra a dungeon com determinação!%n",
                heroi.getNome(), heroi.getClass().getSimpleName());

        while (jogoAtivo && heroi.estaVivo()) {

            boolean isBoss = (monstrosVencidos >= SALAS_ANTES_DO_BOSS);

            exibirEntradaDaSala(isBoss);
            EntradaUsuario.aguardarEnter();

            Monstro monstro = isBoss ? Monstro.criarBoss() : Monstro.criarAleatorio();
            exibirMonstroAparece(monstro);

            Batalha batalha = new Batalha(heroi, monstro);
            Batalha.Resultado resultado = batalha.iniciar();

            switch (resultado) {
                case VITORIA:
                    monstrosVencidos++;
                    if (isBoss) {
                        exibirFimEpico();
                        jogoAtivo = false;
                    } else {
                        System.out.println();
                        System.out.printf("  Monstros vencidos: %d/%d para o boss!%n",
                                monstrosVencidos, SALAS_ANTES_DO_BOSS);
                        EntradaUsuario.aguardarEnter();
                        sala++;
                    }
                    break;

                case DERROTA:
                    jogoAtivo = false;
                    break;

                case FUGA:
                    fugas++;
                    System.out.println("  Você fugiu mas ainda está na dungeon...");
                    EntradaUsuario.aguardarEnter();
                    // Herói não avança de sala ao fugir
                    break;
            }
        }

        exibirPlacarFinal();
        salvarPlacar(); // bônus B03
    }

    // ── Exibição de salas ─────────────────────────────────────────────────────

    private void exibirEntradaDaSala(boolean isBoss) {
        System.out.println();
        imprimirSeparador('═');
        if (isBoss) {
            System.out.println();
            System.out.println("  ██████████████████████████████████████████");
            System.out.println("  ██                                        ██");
            System.out.println("  ██   ⚠️  CÂMARA DO BOSS FINAL  ⚠️          ██");
            System.out.println("  ██                                        ██");
            System.out.println("  ██████████████████████████████████████████");
            System.out.println();
        } else {
            System.out.printf("  🚪 [ SALA %d ] Você avança pelos corredores sombrios...%n", sala);
        }
        imprimirSeparador('═');
        System.out.printf("%n  Estado: %s%n", heroi.resumo());
    }

    private void exibirMonstroAparece(Monstro monstro) {
        System.out.println();
        if (monstro.isBoss()) {
            System.out.println("  A terra treme. Um rugido ensurdecedor ecoa na câmara...");
            System.out.println();
            System.out.println("       .-=-.");
            System.out.println("      /|6 6|\\");
            System.out.println("     | | \\/ | |");
            System.out.println("      \\|    |/");
            System.out.println("    ___\\|  |/___");
            System.out.println("   /  DRACOLICH  \\");
            System.out.println();
        } else {
            System.out.printf("  👁️  Das sombras surge: %s!%n", monstro.getNome());
        }
        System.out.printf("  %s  HP: %d/%d  |  ATK: %d  |  DEF: %d%n",
                monstro.getNome(), monstro.getHpAtual(), monstro.getHpMaximo(),
                monstro.getAtaque(), monstro.getDefesa());
    }

    // ── Fim de jogo ───────────────────────────────────────────────────────────

    private void exibirFimEpico() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║                                              ║");
        System.out.println("  ║   🏆  VOCÊ CONQUISTOU A DUNGEON!  🏆         ║");
        System.out.println("  ║                                              ║");
        System.out.println("  ║   O Dracolich foi derrotado. A paz voltou.  ║");
        System.out.println("  ║                                              ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
    }

    private void exibirPlacarFinal() {
        System.out.println();
        imprimirSeparador('─');
        System.out.println("  📊  PLACAR FINAL");
        imprimirSeparador('─');
        System.out.printf("  Herói        : %s (%s)%n", heroi.getNome(), heroi.getClass().getSimpleName());
        System.out.printf("  Nível final  : %d%n", heroi.getNivel());
        System.out.printf("  HP restante  : %d/%d%n", heroi.getHpAtual(), heroi.getHpMaximo());
        System.out.printf("  Monstros     : %d vencidos%n", monstrosVencidos);
        System.out.printf("  Fugas        : %d%n", fugas);
        System.out.printf("  Desfecho     : %s%n",
                heroi.estaVivo()
                        ? (monstrosVencidos >= SALAS_ANTES_DO_BOSS + 1 ? "🏆 Herói Lendário!" : "🛡️ Sobrevivente")
                        : "💀 Caído em batalha");
        imprimirSeparador('─');
    }

    // ── Bônus B03: salvar placar em arquivo ───────────────────────────────────

    private void salvarPlacar() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("placar.txt", true);
            java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
            bw.write("=== PokéDungeon — " + new java.util.Date() + " ===");
            bw.newLine();
            bw.write("Herói: " + heroi.getNome() + " | Classe: " + heroi.getClass().getSimpleName());
            bw.newLine();
            bw.write("Nível: " + heroi.getNivel() + " | Monstros vencidos: " + monstrosVencidos);
            bw.newLine();
            bw.write("Desfecho: " + (heroi.estaVivo() ? "Sobrevivente" : "Derrotado"));
            bw.newLine();
            bw.newLine();
            bw.close();
            System.out.println("  💾 Placar salvo em 'placar.txt'");
        } catch (java.io.IOException e) {
            System.out.println("  ⚠️  Não foi possível salvar o placar: " + e.getMessage());
        }
    }

    // ── Utilitários ───────────────────────────────────────────────────────────

    private void imprimirSeparador(char c) {
        StringBuilder separador = new StringBuilder(48);
        for (int i = 0; i < 48; i++) {
            separador.append(c);
        }
        System.out.println("  " + separador);
    }
}
