
import java.util.Scanner;

public class InversaoStrings{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine();
        while(!palavra.equals("FIM"))
        {
            char letra;
            StringBuilder palavraNova = new StringBuilder();
            for(int i = 0; i < palavra.length(); i++)
            {
                letra = palavra.charAt(palavra.length() - i -1 );
                palavraNova.append(letra);
            }
            System.out.println(palavraNova);
            palavra = sc.nextLine();
        }
    }
}