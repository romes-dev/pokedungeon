package personagens;

/**
 * Mago — alto dano, baixa defesa. Habilidade aplica queimadura por 3 turnos.
 * Demonstra: Herança, sobrescrita, estado persistente (queimadura).
 */
public class Mago extends Heroi {

    private static final int HP_BASE  = 80;
    private static final int ATK_BASE = 25;
    private static final int DEF_BASE = 4;

    public static final int DANO_QUEIMADURA  = 5;
    public static final int TURNOS_QUEIMADURA = 3;

    public Mago(String nome) {
        super(nome, HP_BASE, ATK_BASE, DEF_BASE);
    }

    @Override
    public int atacar() {
        System.out.printf("  🔮 %s lança uma faísca mágica!%n", getNome());
        return getAtaque();
    }

    /**
     * Bola de Fogo — dano imediato + aplica queimadura no alvo.
     * Retorna o dano DIRETO da habilidade; a queimadura é controlada em Batalha.java.
     */
    @Override
    public int usarHabilidade() {
        int dano = getAtaque();
        System.out.printf("  🔥 %s lança BOLA DE FOGO! Dano: %d%n", getNome(), dano);
        System.out.printf("     O alvo foi incendiado! (+%d dano/turno por %d turnos)%n",
                DANO_QUEIMADURA, TURNOS_QUEIMADURA);
        return dano;
    }

    @Override
    public String toString() {
        return "🔮 Mago | " + super.toString();
    }
}
