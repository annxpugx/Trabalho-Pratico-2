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

    public void imprimir(Jogador J, int id, String[] saida) throws Exception{
        
        String[] imprime = saida[id].split(",");
        J = new Jogador(id, imprime[1], Integer.parseInt(imprime[2]), Integer.parseInt(imprime[3]), 
        imprime[4], Integer.parseInt(imprime[5]), imprime[6], imprime[7]);
        
        MyIO.println("[" + J.getId() + " ## " + J.getNome() + " ## " + J.getAltura() + " ## " + J.getPeso() + " ## " +  J.getAnoNascimento()
        + " ## " +J.getUniversidade()+ " ## " + J.getCidadeNacimento() + " ## " + J.getEstadoNascimento() + "]");

    }
        
    public static void main(String[] args) throws Exception{
        String[] entrada = new String[100];
        int numEntrada = 0;       
        // Leitura da entrada padrao
        do {
            entrada[numEntrada] = MyIO.readLine();
        } while (isFim(entrada[numEntrada++]) == false);
        numEntrada--; // Desconsiderar ultima linha contendo a palavra FIM

        String[] saida = ler();
        for(int i = 0; i < numEntrada; i++){
            Jogador _Jogador = new Jogador();
            _Jogador.imprimir(_Jogador, Integer.parseInt(entrada[i]), saida);
        }
    }
}
