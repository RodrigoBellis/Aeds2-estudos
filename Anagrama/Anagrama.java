import java.util.Arrays;
import java.util.Scanner;

class Anagrama {
    public static boolean anagrama(String p1, String p2) {
        boolean res = false;
        int n = p1.length();
        int m = p2.length();

        if (!(n != m)) {
            char[] arr1 = p1.toCharArray();
            char[] arr2 = p2.toCharArray();

            quicksort(arr1, 0, n - 1);
            quicksort(arr2, 0, m - 1);

            if (Arrays.equals(arr1, arr2)) {
                res = true;
            }
        }

        return res;
    }

    public static void quicksort(char[] array, int left, int right) {
        if (left < right) {
            int pivot = partition(array, left, right);
            quicksort(array, left, pivot - 1);
            quicksort(array, pivot + 1, right);
        }
    }
    
    public static int partition(char[] array, int left, int right) {
        char pivot = array[right];
        
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] <= pivot) {
                i++;
                char temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        
        char temp = array[i + 1];
        array[i + 1] = array[right];
        array[right] = temp;
        
        return i + 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String linha = sc.nextLine();

        while (!linha.equals("FIM")) {
            String[] parts = linha.split(" - ");
            String p1 = parts[0].toLowerCase();
            String p2 = parts[1].toLowerCase();
    
            if (anagrama(p1, p2)) {
                System.out.println("SIM");
            } else {
                MyIO.println("NÃO");
            }
            
            linha = sc.nextLine();
        }

        sc.close();
    }
}