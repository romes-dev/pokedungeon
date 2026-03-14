package personagens;

/**
 * Guerreiro — tanque resistente com habilidade de dano duplo.
 * Demonstra: Herança de Heroi, sobrescrita de usarHabilidade().
 */
public class Guerreiro extends Heroi {

    private static final int HP_BASE  = 120;
    private static final int ATK_BASE = 15;
    private static final int DEF_BASE = 10;

    public Guerreiro(String nome) {
        super(nome, HP_BASE, ATK_BASE, DEF_BASE);
    }

    /**
     * Ataque normal do Guerreiro.
     */
    @Override
    public int atacar() {
        System.out.printf("  ⚔️  %s desfere um golpe certeiro!%n", getNome());
        return getAtaque();
    }

    /**
     * Golpe Devastador — causa 2× o dano de ataque normal.
     */
    @Override
    public int usarHabilidade() {
        int dano = getAtaque() * 2;
        System.out.printf("  💥 %s usa GOLPE DEVASTADOR! Dano: %d!%n", getNome(), dano);
        return dano;
    }

    @Override
    public String toString() {
        return "⚔️  Guerreiro | " + super.toString();
    }
}
