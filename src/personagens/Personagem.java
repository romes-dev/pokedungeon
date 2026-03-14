package personagens;

/**
 * Classe abstrata base para todos os personagens do jogo.
 * Demonstra: Abstração, Encapsulamento, Template Method pattern básico.
 */
public abstract class Personagem {

    private String nome;
    private int hpAtual;
    private int hpMaximo;
    private int ataque;
    private int defesa;

    public Personagem(String nome, int hpMaximo, int ataque, int defesa) {
        this.nome    = nome;
        this.hpMaximo = hpMaximo;
        this.hpAtual  = hpMaximo;
        this.ataque   = ataque;
        this.defesa   = defesa;
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getNome()    { return nome; }
    public int getHpAtual()    { return hpAtual; }
    public int getHpMaximo()   { return hpMaximo; }
    public int getAtaque()     { return ataque; }
    public int getDefesa()     { return defesa; }

    // ── Setters ──────────────────────────────────────────────────────────────
    public void setNome(String nome)       { this.nome = nome; }
    public void setHpAtual(int hpAtual)    { this.hpAtual = Math.max(0, hpAtual); }
    public void setHpMaximo(int hpMaximo)  { this.hpMaximo = hpMaximo; }
    public void setAtaque(int ataque)      { this.ataque = ataque; }
    public void setDefesa(int defesa)      { this.defesa = defesa; }

    // ── Lógica de combate ────────────────────────────────────────────────────

    /**
     * Método abstrato — cada subclasse define sua forma de ataque.
     * Demonstra: Polimorfismo.
     */
    public abstract int atacar();

    /**
     * Recebe dano, descontando a defesa. HP não fica negativo.
     */
    public void receberDano(int dano) {
        int danoEfetivo = Math.max(0, dano - defesa);
        this.hpAtual    = Math.max(0, this.hpAtual - danoEfetivo);
    }

    /**
     * Cura o personagem. HP não ultrapassa o máximo.
     */
    public void curar(int quantidade) {
        this.hpAtual = Math.min(hpMaximo, this.hpAtual + quantidade);
    }

    public boolean estaVivo() {
        return hpAtual > 0;
    }

    // ── Utilitários de exibição ───────────────────────────────────────────────

    /**
     * Gera uma barra visual de HP no terminal.
     * Ex.:  [ ████████░░  64/80 ]
     */
    public String barraDeHP() {
        int totalBlocos = 12;
        int blocosPreenchidos = (int) Math.round((double) hpAtual / hpMaximo * totalBlocos);
        blocosPreenchidos = Math.max(0, Math.min(totalBlocos, blocosPreenchidos));

        StringBuilder barra = new StringBuilder("[ ");
        for (int i = 0; i < totalBlocos; i++) {
            barra.append(i < blocosPreenchidos ? "█" : "░");
        }
        barra.append(String.format("  %d/%d ]", hpAtual, hpMaximo));
        return barra.toString();
    }

    @Override
    public String toString() {
        return String.format("%-12s HP: %s", nome, barraDeHP());
    }
}
