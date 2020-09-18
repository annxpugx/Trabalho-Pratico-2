import java.io.*; 

public class Jogador{
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

    public static Jogador clone (Jogador J){
        Jogador clone = new Jogador();

        clone.setAltura(J.getAltura());
        clone.setPeso(J.getPeso());
        clone.setAnoNascimento(J.getAnoNascimento());
        return clone;
    }

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

        for(int i = 1; i < numEntrada; i++)
            if(entrada[i].contains(",,")){
                entrada[i] = entrada[i].replaceAll(",,", ",nao informado,");
                int tam = entrada[i].length()-1;
                if(entrada[i].charAt(tam) == ','){
                    entrada[i] += "nao informado";   
                }
            }

        br.close();
        return entrada;
    }

    public void salvar(int id, String[] saida) throws Exception{
        
        String[] salvar = saida[id].split(",");

        this.setId(id);
        this.setNome(salvar[1]);
        this.setAltura(Integer.parseInt(salvar[2]));
        this.setPeso(Integer.parseInt(salvar[3]));
        this.setUniversidade(salvar[4]);
        this.setAnoNascimento(Integer.parseInt(salvar[5]));
        this.setCidadeNacimento(salvar[6]);
        this.setEstadoNascimento(salvar[7]);
        // MyIO.println("[" + J.getId() + " ## " + J.getNome() + " ## " + J.getAltura() + " ## " + J.getPeso() + " ## " +  J.getAnoNascimento()
        // + " ## " +J.getUniversidade()+ " ## " + J.getCidadeNacimento() + " ## " + J.getEstadoNascimento() + "]");

    }

        
    public static void main(String[] args) throws Exception{
        String[] entrada_id = new String[100];
        int numEntrada_id = 0;       
        // Leitura da entrada padrao
        do {
            entrada_id[numEntrada_id] = MyIO.readLine();
        } while (isFim(entrada_id[numEntrada_id++]) == false);
        numEntrada_id--;

        int numEntrada_nome = 0; // Desconsiderar ultima linha contendo a palavra FIM

        String[] saida = ler();

        String[] entrada_nome = new String[200];
   
        // Leitura da entrada padrao
        do {
            entrada_nome[numEntrada_nome] = MyIO.readLine();
        } while (isFim(entrada_nome[numEntrada_nome++]) == false);
        numEntrada_nome--; // Desconsiderar ultima linha contendo a palavra FIM

        Jogador _Jogadores[] = new Jogador[200];

        for(int i = 0; i < numEntrada_id; i++){
            _Jogadores[i] = new Jogador();
            _Jogadores[i].salvar(Integer.parseInt(entrada_id[i]), saida);
        }

        String nome;
        int comp = 0;
        long tempoInicio = System.currentTimeMillis();
        for(int i = 0; i < numEntrada_nome; i++){
            for(int j = 0; j < numEntrada_id; j++){
                nome = _Jogadores[j].getNome();
                // MyIO.println(nome);
                // MyIO.println(entrada_nome[i])
                comp++;
                if(nome.contains(entrada_nome[i])){
                    MyIO.println("SIM");
                    j = numEntrada_id;
                }
                else if(j == numEntrada_id - 1)
                    MyIO.println("NAO");
            }
        }
        Arq.openWrite("matricula_sequencial.txt");
        Arq.print("694370\t" + comp + "\t" + (System.currentTimeMillis()-tempoInicio) + "ms");
        Arq.close();
    }
}
