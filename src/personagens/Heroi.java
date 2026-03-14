package personagens;

/**
 * Herói base do jogador.
 * Demonstra: Herança, campos adicionais, habilidade especial sobrescrita pelas subclasses.
 */
public class Heroi extends Personagem {

    private int nivel;
    private int xpAtual;
    private int xpParaProximoNivel;
    private int pocoes;              // bônus: itens de cura

    // Controle de efeitos ativos
    private int turnosQueimando;     // usado pelo Mago (queimadura no alvo)

    public Heroi(String nome, int hpMaximo, int ataque, int defesa) {
        super(nome, hpMaximo, ataque, defesa);
        this.nivel              = 1;
        this.xpAtual            = 0;
        this.xpParaProximoNivel = 50;
        this.pocoes             = 0;
    }

    // ── Getters/Setters extras ────────────────────────────────────────────────
    public int getNivel()    { return nivel; }
    public int getXpAtual()  { return xpAtual; }
    public int getPocoes()   { return pocoes; }
    public void setPocoes(int pocoes) { this.pocoes = pocoes; }

    // ── Ataque base ───────────────────────────────────────────────────────────
    @Override
    public int atacar() {
        return getAtaque();
    }

    /**
     * Habilidade especial — sobrescrita em cada subclasse.
     * @return dano ou 0 quando não causa dano direto (apenas efeito).
     */
    public int usarHabilidade() {
        System.out.println("  (Sem habilidade especial definida.)");
        return 0;
    }

    /**
     * Usa uma poção de cura (+30 HP).
     * @return true se conseguiu usar, false se não tinha poções.
     */
    public boolean usarPocao() {
        if (pocoes <= 0) return false;
        pocoes--;
        curar(30);
        System.out.printf("  🧪 %s usa uma poção e recupera 30 HP!%n", getNome());
        return true;
    }

    // ── Sistema de XP / Nível ─────────────────────────────────────────────────

    /**
     * Concede XP ao herói. Sobe de nível automaticamente se atingir o limiar.
     */
    public void ganharXP(int xp) {
        xpAtual += xp;
        System.out.printf("  ✨ %s ganhou %d XP! (%d/%d)%n", getNome(), xp, xpAtual, xpParaProximoNivel);
        if (xpAtual >= xpParaProximoNivel) {
            subirDeNivel();
        }
    }

    private void subirDeNivel() {
        nivel++;
        xpAtual -= xpParaProximoNivel;
        xpParaProximoNivel = (int) (xpParaProximoNivel * 1.5);

        int bonusAtk = 5;
        int bonusHp  = 10;
        setAtaque(getAtaque() + bonusAtk);
        setHpMaximo(getHpMaximo() + bonusHp);
        curar(bonusHp);

        System.out.println();
        System.out.println("  ╔══════════════════════════════╗");
        System.out.printf ("  ║  🌟 LEVEL UP! Nível %2d!      ║%n", nivel);
        System.out.printf ("  ║  ATK +%d  | HP Máx +%d        ║%n", bonusAtk, bonusHp);
        System.out.println("  ╚══════════════════════════════╝");
        System.out.println();
    }

    // ── Efeitos de estado ─────────────────────────────────────────────────────
    public int getTurnosQueimando()       { return turnosQueimando; }
    public void setTurnosQueimando(int t) { this.turnosQueimando = t; }

    /**
     * Aplica queimadura ao ALVO (monstro). Chamado pelo Mago.
     * Retorna os turnos de queimadura aplicados ao alvo — a lógica de dano
     * por turno fica em Batalha.java para não criar dependência circular.
     */
    public int aplicarQueimadura() {
        return 3; // 3 turnos, 5 de dano por turno
    }

    // ── Exibição ──────────────────────────────────────────────────────────────
    public String resumo() {
        return String.format("Nome: %s | Classe: %s | Nível: %d | HP: %d/%d | ATK: %d | DEF: %d | Poções: %d",
            getNome(), getClass().getSimpleName(), nivel,
            getHpAtual(), getHpMaximo(), getAtaque(), getDefesa(), pocoes);
    }
}
