import java.util.*;

public class Medalhas {
    static class Pais {
        String nome;
        int ouro, prata, bronze;
        Pais(String nome, int ouro, int prata, int bronze) {
            this.nome = nome;
            this.ouro = ouro;
            this.prata = prata;
            this.bronze = bronze;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        List<Pais> lista = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String nome = sc.next();
            int ouro = sc.nextInt();
            int prata = sc.nextInt();
            int bronze = sc.nextInt();
            lista.add(new Pais(nome, ouro, prata, bronze));
        }

        lista.sort((a, b) -> {
            if (a.ouro != b.ouro) return b.ouro - a.ouro;
            if (a.prata != b.prata) return b.prata - a.prata;
            if (a.bronze != b.bronze) return b.bronze - a.bronze;
            return a.nome.compareTo(b.nome);
        });

        for (Pais p : lista) {
            System.out.println(p.nome + " " + p.ouro + " " + p.prata + " " + p.bronze);
        }
    }
}
