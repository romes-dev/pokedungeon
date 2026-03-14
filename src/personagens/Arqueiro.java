package personagens;

import java.util.Random;

/**
 * Arqueiro — equilibrado, habilidade com chance de crítico 3×.
 * Demonstra: Herança, aleatoriedade no comportamento de habilidade.
 */
public class Arqueiro extends Heroi {

    private static final int HP_BASE  = 100;
    private static final int ATK_BASE = 18;
    private static final int DEF_BASE = 6;

    private static final double CHANCE_CRITICO = 0.80; // 80%
    private final Random random = new Random();

    public Arqueiro(String nome) {
        super(nome, HP_BASE, ATK_BASE, DEF_BASE);
    }

    @Override
    public int atacar() {
        System.out.printf("  🏹 %s dispara uma flecha!%n", getNome());
        return getAtaque();
    }

    /**
     * Tiro Certeiro — 80% de chance de crítico (3× dano), 20% dano normal.
     */
    @Override
    public int usarHabilidade() {
        boolean critico = random.nextDouble() < CHANCE_CRITICO;
        if (critico) {
            int dano = getAtaque() * 3;
            System.out.printf("  🎯 %s usa TIRO CERTEIRO — CRÍTICO! Dano: %d!%n", getNome(), dano);
            return dano;
        } else {
            int dano = getAtaque();
            System.out.printf("  🏹 %s usa Tiro Certeiro, mas errou o ponto vital. Dano: %d%n", getNome(), dano);
            return dano;
        }
    }

    @Override
    public String toString() {
        return "🏹 Arqueiro | " + super.toString();
    }
}
