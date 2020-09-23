import java.io.*; 

public class Jogador{

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
        this.altura = altura;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public void setAnoNascimento(int anoNascimento){
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

    public void clone (Jogador J){

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

    public static void swap(Jogador ordenadoI, Jogador ordenadoJ) {
        Jogador temp = ordenadoI;
        ordenadoI = ordenadoJ;
        ordenadoJ = temp;
        mov += 3;
     }

    public static void countingsort(int n) {
        //Array para contar o numero de ocorrencias de cada elemento
        int[] count = new int[getMaior(n) + 1];
        Jogador[] ordenado = new Jogador[n];

        //Inicializar cada posicao do array de contagem 
            for (int i = 0; i < count.length; count[i] = 0, i++);

        //Agora, o count[i] contem o numero de elemento iguais a i
            for (int i = 0; i < n; i++){
                count[Jogadores[i].getAltura()]++;
                //MyIO.println(Jogadores[i].getAltura() + "->" + count[Jogadores[i].getAltura()]);
            }

        //Agora, o count[i] contem o numero de elemento menores ou iguais a i
            for(int i = 1; i < count.length; i++){
                count[i] += count[i-1];
            }

        //Ordenando
            for(int i = n-1; i >= 0; i--){
                ordenado[count[Jogadores[i].getAltura()]-1] = new Jogador();
                ordenado[count[Jogadores[i].getAltura()]-1].clone(Jogadores[i]);
                count[Jogadores[i].getAltura()]--;
            }

        //Copiando para o array original
            for(int i = 1; i < n; i++){
                Jogador tmp = new Jogador();
                tmp.clone(ordenado[i]);
                int j = i - 1;

                while (((j >= 0) && ordenado[j].getAltura() == tmp.getAltura() && (ordenado[j].getNome().compareTo(tmp.getNome())) > 0)) {
                    comp+=3;
                    mov++;
                    ordenado[j + 1].clone(ordenado[j]);
                    j--;
                }
                ordenado[j + 1].clone(tmp);
                mov++;
            }
            
            for(int i = 0; i < n; i++){
                Jogadores[i].clone(ordenado[i]);
            }
    }
    
    public static int getMaior(int n) {
        Jogador maior = new Jogador();
        maior.clone(Jogadores[0]);
        
        for (int i = 0; i < n; i++) {
            comp++;
            if(maior.getAltura() < Jogadores[i].getAltura()){
                maior.clone(Jogadores[i]);
                mov++;
            }
        }
        return maior.getAltura();	
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
        countingsort(numEntrada);
        tempo = (System.currentTimeMillis()- tempo);

        for(int i = 0; i < numEntrada; i++)
            Jogadores[i].imprimir();

        Arq.openWrite("matricula_counting.txt");
        Arq.print("694370\t" + comp + "\t" + mov + "\t" + tempo + "ms");
        Arq.close();
    }
}
