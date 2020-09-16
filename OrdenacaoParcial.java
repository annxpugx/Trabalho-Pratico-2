public class OrdenacaoParcial {

    public static int n = 14;
    public static int k = 7;
    public static int[] array = {1,12,3,6,27,9,16,2,5,7,13,14,76,23};

    public void selecao() {
        for (int i = 0; i < k; i++) {
           int menor = i;
            for (int j = (i + 1); j < n; j++){
                if (array[menor] > array[j]){
                    menor = j;
                }
            }
            swap(menor, i);
        }
    }

    public void insercao() {
		for (int i = 1; i < n; i++) {
			int tmp = array[i];
            int j = (i < k)? i - 1: k - 1;
            while ((j >= 0) && (array[j] > tmp)) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = tmp;  
        }
    }

    public void quicksort(int esq, int dir) {
        int i = esq, j = dir;
        int pivo = array[(dir+esq)/2];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j)  quicksort(esq, j);
        if (i < dir && i < k)  quicksort(i, dir);
        
    }
    
    public void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args){
        OrdenacaoParcial objeto = new OrdenacaoParcial();
        int k = 7;
        //objeto.selecao();
        //objeto.insercao();
        objeto.quicksort(0, n-1);

        for(int i = 0; i < n; i++){
            MyIO.print(array[i] + " ");
        }
        MyIO.println();
    } 

}
