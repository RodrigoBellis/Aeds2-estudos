import java.util.Random;
import java.util.Scanner;


public class AlteracaoAleatoria {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char alterar1, alterar2;
      
        Random gerador = new Random();
        gerador.setSeed(4);
        String frase = sc.nextLine();
        
        while(!frase.equals("FIM"))
        {
            alterar1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            alterar2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            int tamanho = frase.length();
            StringBuilder palavraNova = new StringBuilder();
            for(int i =0; i < tamanho; i++)
            {
                char letra = (char) (frase.charAt(i));
                if(letra == alterar1)
                {
                    letra = alterar2;
                }
                
                palavraNova.append(letra);
            }
            System.out.println(palavraNova);
            frase = sc.nextLine();
        }

        sc.close();
    }
}