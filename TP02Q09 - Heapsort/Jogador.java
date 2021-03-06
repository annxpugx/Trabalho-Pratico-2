import java.io.*;

public class Jogador {

    public static Jogador[] Jogadores;
    public static int comp = 0;
    public static int mov = 0;

    private int id;
    private int altura;
    private int peso;
    private int anoNascimento;
    private String nome;
    private String universidade;
    private String cidadeNascimento;
    private String estadoNascimento;

    // CONSTRUTORES
    Jogador() {
    }

    Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento, String cidadeNascimento,
            String estadoNascimento) {
        this.setId(id);
        this.setNome(nome);
        this.setAltura(altura);
        this.setPeso(peso);
        this.setAnoNascimento(anoNascimento);
        this.setUniversidade(universidade);
        this.setCidadeNacimento(cidadeNascimento);
        this.setEstadoNascimento(estadoNascimento);
    }

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setAltura(int altura) {
        if (altura > 1)
            this.altura = altura;
    }

    public void setPeso(int peso) {
        if (peso > 0)
            this.peso = peso;
    }

    public void setAnoNascimento(int anoNascimento) {
        if (anoNascimento > 1900)
            this.anoNascimento = anoNascimento;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public void setCidadeNacimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    // GETTERS
    public String getNome() {
        return this.nome;
    }

    public String getUniversidade() {
        return this.universidade;
    }

    public String getCidadeNacimento() {
        return this.cidadeNascimento;
    }

    public String getEstadoNascimento() {
        return this.estadoNascimento;
    }

    public int getId() {
        return this.id;
    }

    public int getAltura() {
        return this.altura;
    }

    public int getPeso() {
        return this.peso;
    }

    public int getAnoNascimento() {
        return this.anoNascimento;
    }

    // METHODS

    public void clone(Jogador J) {

        this.setId(J.getId());
        this.setCidadeNacimento(J.getCidadeNacimento());
        this.setEstadoNascimento(J.getEstadoNascimento());
        this.setNome(J.getNome());
        this.setAltura(J.getAltura());
        this.setPeso(J.getPeso());
        this.setAnoNascimento(J.getAnoNascimento());
        this.setUniversidade(J.getUniversidade());

    }

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String[] ler() throws Exception {

        String[] entrada = new String[4000];
        int numEntrada = 0;
        File file = new File("/tmp/players.csv");

        BufferedReader br = new BufferedReader(new FileReader(file));
        // Leitura da entrada padrao
        String lixo = br.readLine();
        do {
            entrada[numEntrada] = br.readLine();
        } while (entrada[numEntrada++] != null);
        numEntrada--;

        for (int i = 1; i < numEntrada; i++) {
            if (entrada[i].contains(",,")) {
                entrada[i] = entrada[i].replaceAll(",,", ",nao informado,");

            }
            int tam = entrada[i].length() - 1;
            if (entrada[i].charAt(tam) == ',') {
                entrada[i] += "nao informado";
            }
        }

        br.close();
        return entrada;
    }

    public void salvar(int index, String[] saida) throws Exception {

        String[] salvar = saida[index].split(",");
        this.setId(Integer.parseInt(salvar[0]));
        this.setNome(salvar[1]);
        this.setAltura(Integer.parseInt(salvar[2]));
        this.setPeso(Integer.parseInt(salvar[3]));
        this.setUniversidade(salvar[4]);
        this.setAnoNascimento(Integer.parseInt(salvar[5]));
        this.setCidadeNacimento(salvar[6]);
        this.setEstadoNascimento(salvar[7]);

    }

    public void imprimir() {
        MyIO.println("[" + getId() + " ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## "
                + getAnoNascimento() + " ## " + getUniversidade() + " ## " + getCidadeNacimento() + " ## "
                + getEstadoNascimento() + "]");
    }

    public static void swap(Jogador[] copia, int i, int j) {
        Jogador temp = copia[i];
        copia[i] = copia[j];
        copia[j] = temp;
        mov += 3;
    }

    public static int getMaiorFilho(Jogador[] copia, int i, int tam){
        int filho;
        comp+=3;
        if (2*i == tam || copia[2*i].getAltura() > copia[2*i+ 1].getAltura() || (copia[2*i].getAltura() == copia[2*i+ 1].getAltura() && copia[2*i].getNome().compareTo(copia[2*i+ 1].getNome()) > 0)) {
            filho = 2*i;
        } else {
            filho = 2*i + 1;
        }
        return filho;
    }

    public static void reconstruir(Jogador[] copia, int tam, int n){
        int i = 1, filho;
        while (i <= (tam/2)){
            filho = getMaiorFilho(copia, i, tam);
            comp+=3;
            if (copia[i].getAltura() < copia[filho].getAltura() || (copia[i].getAltura() == copia[filho].getAltura() && copia[i].getNome().compareTo(copia[filho].getNome()) < 0)) {
                swap(copia, i, filho);
                i = filho;
            } else {
                i = tam;
            }
        }
    }

    public static void construir(Jogador[] copia, int tam){
        comp+=3;
        for (int i = tam; (i > 1 && copia[i].getAltura() > copia[i/2].getAltura()) || (copia[i].getAltura() == copia[i/2].getAltura() && copia[i].getNome().compareTo(copia[i/2].getNome()) > 0); i /= 2){
            swap(copia, i, i/2);
        }
    }

    public static void heapsort(Jogador[] copia, int n) {
        //Contrução do heap
        for (int tam = 2; tam <= n; tam++){
            construir(copia, tam);
        }
        //Ordenacao propriamente dita
        int tam = n;
        while (tam > 1){
            swap(copia, 1, tam--);
            reconstruir(copia, tam, n);
        }
    }

    public static void main(String[] args) throws Exception {
        String[] entrada = new String[1000];
        int numEntrada = 0;
        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        String[] saida = ler();

        Jogadores = new Jogador[numEntrada];

        for (int i = 0; i < numEntrada; i++) {
            Jogadores[i] = new Jogador();
            Jogadores[i].salvar(Integer.parseInt(entrada[i]), saida);
        }

        long tempo = System.currentTimeMillis();

        Jogador[] copia = new Jogador[numEntrada+1];
        copia[0] = new Jogador();

        for(int i = 0; i < numEntrada; i++){
            copia[i+1] = new Jogador();
            copia[i+1].clone(Jogadores[i]);
        }

        heapsort(copia, numEntrada);

        for(int i = 0; i < numEntrada; i++){
            Jogadores[i] = copia[i+1];
        }
        tempo = (System.currentTimeMillis() - tempo);

        for (int i = 0; i < numEntrada; i++)
            Jogadores[i].imprimir();

        Arq.openWrite("694370_heapsort.txt");
        Arq.print("694370\t" + comp + "\t" + mov + "\t" + tempo + "ms");
        Arq.close();
    }
}
