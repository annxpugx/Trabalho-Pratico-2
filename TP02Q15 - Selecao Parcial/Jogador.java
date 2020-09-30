import java.io.*; 

public class Jogador{

    public static Jogador[] Jogadores;
    public static int k = 10;
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

    //CONSTRUTORES
    Jogador(){}
    Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento , String cidadeNascimento, String estadoNascimento){
        this.setId(id);
        this.setNome(nome);
        this.setAltura(altura);
        this.setPeso(peso);
        this.setAnoNascimento(anoNascimento);
        this.setUniversidade(universidade);
        this.setCidadeNacimento(cidadeNascimento);
        this.setEstadoNascimento(estadoNascimento);
    }
    //SETTERS
    public void setId(int id){
        this.id = id;
    }

    public void setAltura(int altura){
        if(altura > 1)
            this.altura = altura;
    }

    public void setPeso(int peso){
        if(peso > 0)
            this.peso = peso;
    }

    public void setAnoNascimento(int anoNascimento){
        if(anoNascimento > 1900)
            this.anoNascimento = anoNascimento;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setUniversidade(String universidade){
        this.universidade = universidade;
    }

    public void setCidadeNacimento(String cidadeNascimento){
        this.cidadeNascimento = cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento){
        this.estadoNascimento = estadoNascimento;
    }
    //GETTERS
    public String getNome(){
        return this.nome;
    }

    public String getUniversidade(){
        return this.universidade;
    }

    public String getCidadeNacimento(){
        return this.cidadeNascimento;
    }

    public String getEstadoNascimento(){
        return this.estadoNascimento;
    }

    public int getId(){
        return this.id;
    }

    public int getAltura(){
        return this.altura;
    }

    public int getPeso(){
        return this.peso;
    }

    public int getAnoNascimento(){
        return this.anoNascimento;
    }

    //METHODS

    // public static Jogador clone (Jogador J){
    //     Jogador clone = new Jogador();

    //     clone.setAltura(getAltura());
    //     clone.setPeso(getPeso());
    //     clone.setAnoNascimento(getAnoNascimento());
    //     return clone;
    // }

    public static boolean isFim(String s) {
        return (s.length() >= 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static String[] ler() throws Exception{
    
        String[] entrada = new String[4000];
        int numEntrada = 0;
        File file = new File("/tmp/players.csv"); 
  
        BufferedReader br = new BufferedReader(new FileReader(file));
        // Leitura da entrada padrao
        String lixo = br.readLine();
        do {
            entrada[numEntrada] = br.readLine();
        } while (entrada[numEntrada++] != null);
        numEntrada --;

        for(int i = 1; i < numEntrada; i++){
            if(entrada[i].contains(",,")){
                entrada[i] = entrada[i].replaceAll(",,", ",nao informado,");
                
            }
            int tam = entrada[i].length()-1;
            if(entrada[i].charAt(tam) == ','){
                entrada[i] += "nao informado";   
            }
        }

        br.close();
        return entrada;
    }

    public void salvar(int index, String[] saida) throws Exception{
        
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

    public void imprimir(){
        MyIO.println("[" + getId() + " ## " + getNome() + " ## " + getAltura() + " ## " + getPeso() + " ## " +  getAnoNascimento()
        + " ## " +getUniversidade()+ " ## " + getCidadeNacimento() + " ## " + getEstadoNascimento() + "]");
    }

    public static void swap(int i, int j) {
        Jogador temp = Jogadores[i];
        Jogadores[i] = Jogadores[j];
        Jogadores[j] = temp;
        mov += 3;
    }

    public static void selecao(int n) {
        for (int i = 0; i < k; i++) {
           int menor = i;
            for (int j = (i + 1); j < n; j++){
                comp++;
                if (Jogadores[menor].getNome().compareTo(Jogadores[j].getNome()) > 0){
                    menor = j;
                }
            }
            swap(menor, i);
        }
    }

        
    public static void main(String[] args) throws Exception{
        String[] entrada = new String[1000];
        int numEntrada = 0;       
        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        String[] saida = ler();

        Jogadores = new Jogador[numEntrada];

        for(int i = 0; i < numEntrada; i++){
            Jogadores[i] = new Jogador();
            Jogadores[i].salvar(Integer.parseInt(entrada[i]), saida);
        }

        long tempo = System.currentTimeMillis();
        selecao(numEntrada);
        tempo = (System.currentTimeMillis()- tempo);

        for(int i = 0; i < k; i++)
            Jogadores[i].imprimir();

        Arq.openWrite("matricula_selecao.txt");
        Arq.print("694370\t" + comp + "\t" + mov + "\t" + tempo + "ms");
        Arq.close();
    }
}
