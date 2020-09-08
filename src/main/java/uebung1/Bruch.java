package uebung1;

import java.math.BigInteger;
import java.util.Comparator;

public class Bruch implements Comparable<Bruch> {
    private int z;
    private int n;

    // Getters
    public int getZ() {
        return this.z;
    }
    
    public int getN() {
        return this.n;
    }

    /**
     * Bruch-Konstruktor
     * @param z Zähler
     * @param n Nenner
     * @throws IllegalArgumentException Nenner darf nicht 0 sein
     */
    public Bruch(int z, int n) throws IllegalArgumentException{
        if(n == 0){
            throw new IllegalArgumentException("Nenner darf nicht 0 sein");
        }

        this.z = z;
        this.n = n;
    }

    @Override
    public int compareTo(Bruch b){
        if(this.ausrechnen() < b.ausrechnen()){
            return -1;
        } else if(this.ausrechnen() == b.ausrechnen()){
            return 0;
        } else return 1;
    }

    /**
     * Generiert einen String aus dem Bruch
     * @return String im Format "(Zähler/Nenner)"  
     */
    public String toString(){
        return "(" + this.z + "/" + this.n + ")";
    }

    public String toStringPlusDecimal(){
        return "(" + this.z + "/" + this.n + ")" + " (" + this.ausrechnen() + ")";
    }

    /**
     * Multipliziert zwei Brüche.
     * @param b Bruch mit dem multipliziert wird
     * @return Neuer Bruch
     */
    public Bruch multiplizieren(Bruch b){
        return new Bruch(this.z * b.z, this.n * b.n);
    }

    public float ausrechnen(){
        return (float)this.z/this.n;
    }

    public Bruch kuerzen(){
        BigInteger biZ = BigInteger.valueOf(this.z);
        BigInteger biN = BigInteger.valueOf(this.n);
        int gcd = biZ.gcd(biN).intValue();
        return new Bruch(this.z/gcd, this.n/gcd);
    }

    public Bruch kehrwert(){
        return new Bruch(this.n, this.z);
    }

    public Bruch dividieren(Bruch b){
        return this.multiplizieren(b.kehrwert());
    }
}

/**
 * Comparator-Implementierung für Vergleich der Dezimalwerte von Brüchen
 */
class WertComparator implements Comparator<Bruch> {
    @Override
    public int compare(Bruch a, Bruch b){
        if(a.ausrechnen() < b.ausrechnen()){
            return -1;
        }
        else if(a.ausrechnen() == b.ausrechnen()){
            return 0;
        }
        else{
            return 1;
        }
    }
}

/**
 * Comparator-Implementierung für Vergleich der Zähler von Brüchen
 */
class ZaehlerComparator implements Comparator<Bruch> {
    @Override
    public int compare(Bruch a, Bruch b){
        if(a.getZ() < b.getZ()){
            return -1;
        }
        else if(a.getZ() == b.getZ()){
            return 0;
        }
        else{
            return 1;
        }
    }
}