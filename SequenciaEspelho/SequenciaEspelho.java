import java.util.*;

public class SequenciaEspelho {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;

        int C = sc.nextInt(); // número de casos
        StringBuilder out = new StringBuilder();

        for (int t = 0; t < C; t++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            int ini = Math.min(a, b);
            int fim = Math.max(a, b);

            StringBuilder seq = new StringBuilder();
            for (int i = ini; i <= fim; i++) seq.append(i);

            out.append(seq);
            out.append(new StringBuilder(seq).reverse());
            if (t + 1 < C) out.append('\n');
        }

        System.out.print(out.toString());
    }
}
