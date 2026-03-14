package personagens;

import java.util.Random;

/**
 * Representa um monstro da dungeon.
 * Demonstra: Herança de Personagem, fábrica estática, comportamento de IA básico.
 */
public class Monstro extends Personagem {

    public enum Tipo {
        DRAGAOZINHO, ESQUELETO, ZUMBI, ARANHA, BOSS_DRACOLICH
    }

    private Tipo tipo;
    private int xpRecompensa;
    private int turnosQueimando;   // efeito de estado aplicado pelo Mago

    // Atributos especiais por tipo
    private boolean regenera;      // Zumbi regenera HP por turno
    private boolean ataqueRapido;  // Aranha ataca primeiro

    private final Random random = new Random();

    public Monstro(String nome, int hpMaximo, int ataque, int defesa,
                   Tipo tipo, int xpRecompensa) {
        super(nome, hpMaximo, ataque, defesa);
        this.tipo         = tipo;
        this.xpRecompensa = xpRecompensa;
        this.regenera     = (tipo == Tipo.ZUMBI);
        this.ataqueRapido = (tipo == Tipo.ARANHA);
    }

    // ── Fábrica estática ──────────────────────────────────────────────────────

    /**
     * Cria um monstro aleatório (exceto Boss).
     */
    public static Monstro criarAleatorio() {
        int sorteio = new Random().nextInt(4);
        switch (sorteio) {
            case 0: return criarDragaozinho();
            case 1: return criarEsqueleto();
            case 2: return criarZumbi();
            default: return criarAranha();
        }
    }

    public static Monstro criarDragaozinho() {
        return new Monstro("🐲 Dragãozinho", 60, 12, 3, Tipo.DRAGAOZINHO, 20);
    }

    public static Monstro criarEsqueleto() {
        return new Monstro("💀 Esqueleto",   45,  8, 0, Tipo.ESQUELETO,   15);
    }

    public static Monstro criarZumbi() {
        return new Monstro("🧟 Zumbi Lento", 70,  6, 2, Tipo.ZUMBI,       18);
    }

    public static Monstro criarAranha() {
        return new Monstro("🕷️  Aranha Gigante", 35, 14, 1, Tipo.ARANHA,  22);
    }

    public static Monstro criarBoss() {
        return new Monstro("👹 DRACOLICH",  180, 20, 8, Tipo.BOSS_DRACOLICH, 80);
    }

    // ── Ataque do monstro ─────────────────────────────────────────────────────

    @Override
    public int atacar() {
        // IA adaptativa (bônus B04): se HP < 30%, tenta fugir com 40% de chance
        if ((double) getHpAtual() / getHpMaximo() < 0.30 && tipo != Tipo.BOSS_DRACOLICH) {
            if (random.nextDouble() < 0.40) {
                System.out.printf("  😨 %s está com pouco HP e tenta FUGIR...%n", getNome());
                return -1; // sinal de tentativa de fuga
            }
        }
        return getAtaque();
    }

    // ── Comportamentos especiais ──────────────────────────────────────────────

    /**
     * Zumbi regenera 3 HP por turno. Retorna HP recuperado (0 se não regenera).
     */
    public int regenerar() {
        if (!regenera || !estaVivo()) return 0;
        int hp = 3;
        curar(hp);
        return hp;
    }

    public boolean temAtaqueRapido() { return ataqueRapido; }

    // ── Efeito de queimadura ──────────────────────────────────────────────────
    public int getTurnosQueimando()        { return turnosQueimando; }
    public void setTurnosQueimando(int t)  { this.turnosQueimando = Math.max(0, t); }

    /**
     * Aplica dano de queimadura se ainda estiver queimando. Retorna dano causado.
     */
    public int processarQueimadura() {
        if (turnosQueimando <= 0) return 0;
        turnosQueimando--;
        int dano = 5;
        receberDanoSemDefesa(dano); // queimadura ignora defesa
        return dano;
    }

    /**
     * Dano direto sem desconto de defesa (para efeitos de estado).
     */
    public void receberDanoSemDefesa(int dano) {
        setHpAtual(Math.max(0, getHpAtual() - dano));
    }

    // ── Getters extras ────────────────────────────────────────────────────────
    public Tipo getTipo()          { return tipo; }
    public int getXpRecompensa()   { return xpRecompensa; }
    public boolean isBoss()        { return tipo == Tipo.BOSS_DRACOLICH; }

    public String statusQueimando() {
        return turnosQueimando > 0 ? " 🔥(" + turnosQueimando + " turnos)" : "";
    }

    @Override
    public String toString() {
        return getNome() + statusQueimando() + "  HP: " + barraDeHP();
    }
}
