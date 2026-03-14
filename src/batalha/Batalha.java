package batalha;

import personagens.Heroi;
import personagens.Mago;
import personagens.Monstro;
import util.EntradaUsuario;

/**
 * Gerencia o loop de batalha por turnos entre herói e monstro.
 * Demonstra: Composição, polimorfismo em ação, controle de fluxo.
 */
public class Batalha {

    // Resultado da batalha
    public enum Resultado { VITORIA, DERROTA, FUGA }

    private final Heroi heroi;
    private final Monstro monstro;
    private int turno;
    private int turnosQueimandoMonstro;

    public Batalha(Heroi heroi, Monstro monstro) {
        this.heroi                 = heroi;
        this.monstro               = monstro;
        this.turno                 = 1;
        this.turnosQueimandoMonstro = 0;
    }

    // ── Entrada na batalha ────────────────────────────────────────────────────

    public Resultado iniciar() {
        System.out.println();
        imprimirSeparador();
        System.out.printf("  ⚔️  BATALHA INICIADA: %s  VS  %s%n", heroi.getNome(), monstro.getNome());
        imprimirSeparador();

        // Aranha tem ataque rápido: ataca primeiro no turno 1
        if (monstro.temAtaqueRapido()) {
            System.out.printf("%n  ⚡ %s é rápida e ataca primeiro!%n", monstro.getNome());
            procesarAtaqueMonstro();
            if (!heroi.estaVivo()) return Resultado.DERROTA;
        }

        while (heroi.estaVivo() && monstro.estaVivo()) {
            exibirStatus();
            Resultado acao = processarTurnoHeroi();
            if (acao == Resultado.FUGA) return Resultado.FUGA;
            if (!monstro.estaVivo()) break;

            procesarAtaqueMonstro();
            if (!heroi.estaVivo()) return Resultado.DERROTA;

            processarEfeitosDoTurno();
            turno++;
        }

        if (heroi.estaVivo()) {
            exibirVitoria();
            return Resultado.VITORIA;
        }
        exibirDerrota();
        return Resultado.DERROTA;
    }

    // ── Turno do herói ────────────────────────────────────────────────────────

    private Resultado processarTurnoHeroi() {
        System.out.printf("%n  === TURNO %d ===%n", turno);
        System.out.println();
        exibirMenuAcoes();

        int opcaoMax = heroi.getPocoes() > 0 ? 4 : 3;
        int acao = EntradaUsuario.lerOpcao("  >> ", 1, opcaoMax);

        System.out.println();

        switch (acao) {
            case 1: // Atacar
                int danoAtaque = heroi.atacar();
                System.out.printf("     Dano causado: %d (DEF do monstro: %d)%n",
                        Math.max(0, danoAtaque - monstro.getDefesa()), monstro.getDefesa());
                monstro.receberDano(danoAtaque);
                break;

            case 2: // Habilidade especial
                int danoHabilidade = heroi.usarHabilidade();
                monstro.receberDano(danoHabilidade);
                // Mago: aplicar queimadura
                if (heroi instanceof Mago) {
                    turnosQueimandoMonstro = Mago.TURNOS_QUEIMADURA;
                    monstro.setTurnosQueimando(turnosQueimandoMonstro);
                }
                break;

            case 3: // Fugir
                System.out.printf("  🏃 %s foge da batalha!%n", heroi.getNome());
                return Resultado.FUGA;

            case 4: // Usar poção (bônus)
                if (!heroi.usarPocao()) {
                    System.out.println("  ❌ Sem poções!");
                    return processarTurnoHeroi(); // tenta novamente
                }
                break;
        }

        return null; // batalha continua
    }

    // ── Turno do monstro ──────────────────────────────────────────────────────

    private void procesarAtaqueMonstro() {
        System.out.println();
        int resultado = monstro.atacar();

        if (resultado == -1) {
            // Tentativa de fuga do monstro (IA adaptativa)
            System.out.printf("  ... %s consegue escapar e foge da dungeon!%n", monstro.getNome());
            monstro.setHpAtual(0); // monstro "morre" fugindo — herói vence
            return;
        }

        System.out.printf("  👊 %s ataca %s!%n", monstro.getNome(), heroi.getNome());
        int danoReal = Math.max(0, resultado - heroi.getDefesa());
        heroi.receberDano(resultado);
        System.out.printf("     Dano sofrido: %d (sua DEF: %d)%n", danoReal, heroi.getDefesa());
    }

    // ── Efeitos ao fim do turno ───────────────────────────────────────────────

    private void processarEfeitosDoTurno() {
        // Queimadura no monstro
        if (monstro.getTurnosQueimando() > 0) {
            int danoBrasa = monstro.processarQueimadura();
            System.out.printf("%n  🔥 %s sofre %d de dano de queimadura! (%d turnos restantes)%n",
                    monstro.getNome(), danoBrasa, monstro.getTurnosQueimando());
        }

        // Regeneração do Zumbi
        int hpRegen = monstro.regenerar();
        if (hpRegen > 0) {
            System.out.printf("  🟢 %s regenera %d HP!%n", monstro.getNome(), hpRegen);
        }
    }

    // ── Exibição ──────────────────────────────────────────────────────────────

    private void exibirStatus() {
        System.out.println();
        System.out.printf("  %-14s %s%n", heroi.getNome() + ":", heroi.barraDeHP());
        System.out.printf("  %-14s %s%s%n",
                monstro.getNome().trim() + ":", monstro.barraDeHP(), monstro.statusQueimando());
    }

    private void exibirMenuAcoes() {
        System.out.println("  Sua ação:");
        System.out.printf("    [1] ⚔️  Atacar         (ATK: %d)%n", heroi.getAtaque());
        System.out.println("    [2] ✨ Usar Habilidade (especial!)");
        System.out.println("    [3] 🏃 Fugir");
        if (heroi.getPocoes() > 0) {
            System.out.printf("    [4] 🧪 Usar Poção      (%d restantes)%n", heroi.getPocoes());
        }
    }

    private void exibirVitoria() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════╗");
        System.out.printf ("  ║  🏆 VITÓRIA em %2d turno(s)!       ║%n", turno);
        System.out.println("  ╚══════════════════════════════════╝");
        heroi.ganharXP(monstro.getXpRecompensa());

        // Bônus B02: drop de poção após batalha (50% de chance)
        if (Math.random() < 0.50) {
            heroi.setPocoes(heroi.getPocoes() + 1);
            System.out.println("  🧪 Você encontrou uma poção de cura!");
        }
    }

    private void exibirDerrota() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════╗");
        System.out.println("  ║  💀 VOCÊ FOI DERROTADO...         ║");
        System.out.println("  ╚══════════════════════════════════╝");
    }

    private void imprimirSeparador() {
        System.out.println("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
