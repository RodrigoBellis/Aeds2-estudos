import java.util.Scanner;

public class Palindromo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine();
        
        //a cada iteracao, compara se a palavra digitada foi fim, se nao, checa se eh um palindromo.
        while (!palavra.equals("FIM")) {
            
            // se retornar true, printa sim, se nao for, printa nao.
            if (ehPalindromo(palavra)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            palavra = sc.nextLine();
        }

        sc.close();
    }


    //compara para descobrir se eh um palindromo.
    public static boolean ehPalindromo(String palavra) {
        int tamanho = palavra.length();
        for (int i = 0; i < tamanho / 2; i++) {
            if (palavra.charAt(i) != palavra.charAt(tamanho - 1 - i)) {
                return false; 
            }
        }
        
        return true;
    }
}