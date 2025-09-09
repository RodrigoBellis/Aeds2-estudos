import java.util.Scanner;

public class Largada {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        while (entrada.hasNextInt()) {
            int numeroCompetidores = entrada.nextInt();

            int[] largada = new int[numeroCompetidores];
            for (int i = 0; i < numeroCompetidores; i++) {
                largada[i] = entrada.nextInt();
            }

            int[] chegada = new int[numeroCompetidores];
            for (int i = 0; i < numeroCompetidores; i++) {
                chegada[i] = entrada.nextInt();
            }

            int[] posicaoChegada = new int[numeroCompetidores + 1]; 
            for (int i = 0; i < numeroCompetidores; i++) {
                posicaoChegada[chegada[i]] = i;
            }

            int ultrapassagens = 0;
            for (int i = 0; i < numeroCompetidores; i++) {
                for (int j = i + 1; j < numeroCompetidores; j++) {
                    int competidorA = largada[i];
                    int competidorB = largada[j];
                    if (posicaoChegada[competidorA] > posicaoChegada[competidorB]) {
                        ultrapassagens++;
                    }
                }
            }

            System.out.println(ultrapassagens);
        }

        entrada.close();
    }
}
