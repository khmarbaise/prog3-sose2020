package bankprojekt.verarbeitung;

public enum Waehrung {
    EUR(1), BGN(1.95583), KM(1.95583), LTL(3.4528);

    private double eurValue;

    private Waehrung(double eurValue){
        this.eurValue = eurValue;
    }

    public double euroInWaehrungUmrechnen(double betrag){
        return betrag * this.eurValue;
    }

    public double waehrungInEuroUmrechnen(double betrag){
        return betrag / this.eurValue;
    }

    public double umrechnen(double betrag, Waehrung waehrung){
        return waehrung.euroInWaehrungUmrechnen(this.waehrungInEuroUmrechnen(betrag));
    }
}