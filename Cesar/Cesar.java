import java.util.Scanner;


public class Cesar {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine();
        int tamanho; 
        
        while(!palavra.equals("FIM"))
        {
            tamanho = palavra.length();
            StringBuilder palavraNova = new StringBuilder(); //cria stringbuilder para poder manipular a string
            for(int i = 0; i < tamanho; i ++)
            {
                char letra = (char) (palavra.charAt(i) + 3);
                if((palavra.charAt(i) + 3) > 125 ) //confere se a letra tem valor maior que # na tabela ASCII, se sim, altera o caracter para o caracter de valor invalido usado no verde
                {
                    letra = (char) 65533;
                }
                palavraNova.append(letra);
            }
            System.out.println(palavraNova);
            

            palavra = sc.nextLine();
        }
        sc.close();
    }
    
}