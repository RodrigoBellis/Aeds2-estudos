import java.io.*;
import java.util.*;

public class Sort {
    static int M; // usado no comparador

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        while (true) {
            Integer Ni = fs.nextIntOrNull(); // pode vir null no EOF
            if (Ni == null) break;
            int N = Ni;
            int m = fs.nextInt();

            if (N == 0 && m == 0) break;

            M = m;
            Integer[] v = new Integer[N];
            for (int i = 0; i < N; i++) v[i] = fs.nextInt();

            Arrays.sort(v, (a, b) -> {
                int ra = a % M; // em Java, -100 % 3 == -1
                int rb = b % M;

                // 1) (n % M) ascendente
                if (ra != rb) return Integer.compare(ra, rb);

                boolean oddA = (a & 1) != 0;
                boolean oddB = (b & 1) != 0;

                // 2) Ímpares antes dos pares
                if (oddA != oddB) return oddA ? -1 : 1;

                // 3) Ambos ímpares: ordem decrescente
                if (oddA) return Integer.compare(b, a);

                // 4) Ambos pares: ordem crescente
                return Integer.compare(a, b);
            });

            out.append(N).append(' ').append(m).append('\n');
            for (int x : v) out.append(x).append('\n');
        }

        // "Imprima os dois últimos zeros da entrada para a saída padrão."
        out.append("0 0\n");
        System.out.print(out.toString());
    }

    // Leitor rápido
    static final class FastScanner {
        BufferedReader br;
        StringTokenizer st;
        FastScanner(InputStream is) { br = new BufferedReader(new InputStreamReader(is)); }
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        Integer nextIntOrNull() throws IOException {
            String s = next();
            if (s == null) return null;
            return Integer.parseInt(s);
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
