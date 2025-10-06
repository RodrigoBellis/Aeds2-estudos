import java.io.*;
import java.text.*;
import java.util.*;

public class Game {
    private int id;
    private String name, releaseDate;
    private int estimatedOwners, metacriticScore, achievements;
    private float price, userScore;
    private String[] supportedLanguages, publishers, developers, categories, genres, tags;

    public Game() {
        name = "NaN";
        releaseDate = "01/01/1970";
        supportedLanguages = publishers = developers = categories = genres = tags = new String[]{"NaN"};
        metacriticScore = -1;
        userScore = -1f;
    }

    /** Divide linha CSV respeitando aspas e colchetes **/
    private String[] splitCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false, insideBracket = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') insideQuotes = !insideQuotes;
            else if (c == '[') insideBracket = true;
            else if (c == ']') insideBracket = false;
            else if (c == ',' && !insideQuotes && !insideBracket) {
                parts.add(current.toString());
                current.setLength(0);
                continue;
            }
            current.append(c);
        }
        parts.add(current.toString());
        return parts.toArray(new String[0]);
    }

    /** Converte datas em formatos diversos (yyyy, MMM yyyy, MMM dd, yyyy) **/
    private String normalizaData(String s) {
        if (s == null || s.isEmpty()) return "01/01/1970";
        s = s.replace("\"", "").trim();

        String[] formatos = {"MMM dd, yyyy", "MMM yyyy", "yyyy-MM-dd", "dd/MM/yyyy", "yyyy"};
        for (String f : formatos) {
            try {
                DateFormat in = new SimpleDateFormat(f, Locale.ENGLISH);
                Date d = in.parse(s);
                return new SimpleDateFormat("dd/MM/yyyy").format(d);
            } catch (Exception ignored) {}
        }

        // formato "Aug 2018" (sem dia)
        if (s.matches("[A-Za-z]{3,9} \\d{4}")) {
            try {
                DateFormat in = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                Date d = in.parse(s);
                return new SimpleDateFormat("01/MM/yyyy").format(d);
            } catch (Exception ignored) {}
        }

        // formato apenas ano
        if (s.matches("\\d{4}")) return "01/01/" + s;

        return "01/01/1970";
    }

    private String[] parseList(String s) {
        if (s == null || s.isEmpty()) return new String[]{"NaN"};
        s = s.replaceAll("[\\[\\]'\"']", "").trim();
        if (s.isEmpty()) return new String[]{"NaN"};
        String[] v = s.split(",");
        for (int i = 0; i < v.length; i++) v[i] = v[i].trim();
        return v;
    }

    private int parseIntSafe(String s, int def) {
        if (s == null || s.isEmpty()) return def;
        s = s.replaceAll("[^0-9]", "");
        try { return s.isEmpty() ? def : Integer.parseInt(s); }
        catch (Exception e) { return def; }
    }

    private float parseFloatSafe(String s, float def) {
        if (s == null || s.isEmpty()) return def;
        s = s.replace(",", ".").trim().toLowerCase();
        if (s.contains("free")) return 0f;
        if (s.equals("tbd") || s.isEmpty()) return -1f;
        try { return Float.parseFloat(s); }
        catch (Exception e) { return def; }
    }

    public void ler(String line) {
        String[] x = splitCsvLine(line);
        int i = 0;
        id = parseIntSafe(x[i++], 0);
        name = (i < x.length ? x[i++] : "NaN").replace("\"", "").trim();
        releaseDate = (i < x.length ? normalizaData(x[i++]) : "01/01/1970");
        estimatedOwners = (i < x.length ? parseIntSafe(x[i++], 0) : 0);
        price = (i < x.length ? parseFloatSafe(x[i++], 0f) : 0f);
        supportedLanguages = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
        metacriticScore = (i < x.length ? parseIntSafe(x[i++], -1) : -1);
        userScore = (i < x.length ? parseFloatSafe(x[i++], -1f) : -1f);
        achievements = (i < x.length ? parseIntSafe(x[i++], 0) : 0);
        publishers = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
        developers = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
        categories = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
        genres = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
        tags = (i < x.length ? parseList(x[i++]) : new String[]{"NaN"});
    }

    public void imprimir() {
        String mScore = (metacriticScore == -1 ? "NaN" : String.valueOf(metacriticScore));
        String uScore = (userScore == -1f ? "NaN" : String.format(Locale.US, "%.1f", userScore));
        System.out.println("=> " + id + " ## " + name + " ## " + releaseDate + " ## " + estimatedOwners + " ## " +
                String.format(Locale.US, "%.2f", price) + " ## " + Arrays.toString(supportedLanguages) + " ## " +
                mScore + " ## " + uScore + " ## " + achievements + " ## " +
                Arrays.toString(publishers) + " ## " + Arrays.toString(developers) + " ## " +
                Arrays.toString(categories) + " ## " + Arrays.toString(genres) + " ## " +
                Arrays.toString(tags) + " ##");
    }

    public static void main(String[] args) throws Exception {
        String arquivo = "/tmp/games.csv"; // ajuste o caminho conforme o seu
        Scanner sc = new Scanner(System.in, "UTF-8");

        while (true) {
            String entrada = sc.nextLine().trim();
            if (entrada.equalsIgnoreCase("FIM")) break;
            int idBusca = Integer.parseInt(entrada.replaceAll("\\D", ""));
            boolean achou = false;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(arquivo), "UTF-8"))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith(idBusca + ",")) {
                        Game g = new Game();
                        g.ler(linha);
                        g.imprimir();
                        achou = true;
                        break;
                    }
                }
            }
            if (!achou) System.out.println("Jogo com ID " + idBusca + " não encontrado.");
        }
        sc.close();
    }
}
