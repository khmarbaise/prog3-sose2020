package uebung1;
import java.util.Arrays;

public class uebung1 {
    public static void main(String[] args){
        Bruch[] b = new Bruch[5];
        b[0] = new Bruch(1, 2);
        b[1] = new Bruch(1, 3);
        b[2] = new Bruch(2, 3);
        b[3] = new Bruch(4, 5);
        b[4] = new Bruch(1, 4);

        System.out.println("Unsorted: ");
        for(int i = 0; i < b.length; i++){
            System.out.println(b[i].toStringPlusDecimal());
        }

        System.out.println();
        System.out.println("Sorted by value:");
        Arrays.sort(b, new WertComparator());
        for(int i = 0; i < b.length; i++){
            System.out.println(b[i].toStringPlusDecimal());
        }

        System.out.println();
        System.out.println("Sorted by numerator:");
        Arrays.sort(b, new ZaehlerComparator());
        for(int i = 0; i < b.length; i++){
            System.out.println(b[i].toStringPlusDecimal());
        }
    }
}
