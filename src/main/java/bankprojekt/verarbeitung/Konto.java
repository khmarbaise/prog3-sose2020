package bankprojekt.verarbeitung;

/**
 * stellt ein allgemeines Konto dar
 */
public abstract class Konto implements Comparable<Konto>
/*Die Klasse ist abstrakt, d.h. man kann keine Objekte von
Konto erzeugen. Durch das Schl�sselwort hier ist es auch m�glich,
dass die Klasse ein oder mehrere abstrakte Methoden, also solche
ohne Implementierung enth�lt. (hier: abheben)
Sie sehen, die Klasse implementiert Comparable, man kann
Konto-Objekte also sortieren*/
{
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;
	/*private bedeutet, dass dieses Attribut nur innerhalb der Klasse
	verwendet werden darf. Es ist ein wesentlicher Bestandteil des
	Information Hiding.
	Die anderen Sichtbarkeitsmodifizierer sind protected, public oder
	nicht (= packageweite Sichtbarkeit)
	Merke: Attribute macht man private!!!*/

	/**
	 * die Kontonummer
	 */
	private final long nummer;
	/*final bedeutet, dass dieses Attribut nach der ersten Zuweisung
	nicht mehr ver�ndert werden kann. Die erste Zuweisung findet
	entweder hier direkt oder im Konstruktor statt.*/

	/**
	 * der aktuelle Kontostand
	 */
	private double kontostand;

	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		/*Diese Methode ist protected, kann also innerhalb der Klasse
		und in erbenden Klassen (hier Girokonto und Sparbuch)
		aufgerufen werden. (Ganz genau: auch in allen Klassen
		des gleichen Pakets)
		Nat�rlich darf man den Kontostand eines Kontos nicht einfach
		so auf einen beliebigen Wert setzen, deshalb ist er durch
		das private Attribut und die gesch�tzte set-Methode abgesichert.
		Bitte achten Sie bei set- und get-Methoden grunds�tzlich auf
		die Einhaltung der Namenskonventionen.*/
		this.kontostand = kontostand;
	}

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), k�nnen keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers w�ren (abheben, Inhaberwechsel)
	 */
	private boolean gesperrt;

	/**
	 * Währung, in der das Konto geführt wird
	 */
	private Waehrung waehrung;

	public Waehrung getAktuelleWaehrung(){
		return this.waehrung;
	}

	public void waehrungswechsel(Waehrung neu){
		this.kontostand = this.waehrung.umrechnen(this.kontostand, neu);
		this.waehrung = neu;
	}

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anf�ngliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber der Inhaber
	 * @param kontonummer die gew�nschte Kontonummer
	 * @throws IllegalArgumentException wenn der Inhaber null
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		//ein Konstruktor hei�t immer genauso wie die Klasse
		//Konstruktoren machen auch in abstrakten Klassen Sinn, weil
		//sie von den Unterklassen aufgerufen werden, um die Attribute dieser
		//Klasse hier zu initialisieren.
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand = 0;
		this.gesperrt = false;
		this.waehrung = Waehrung.EUR;
	}
	
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		//ein zweiter Konstruktor. Wenn mehrere Methoden (oder Konstruktoren)
		//gleich hei�en, aber unterschiedliche Parameterlisten haben,
		//spricht man von �berladung.
		this(Kunde.MUSTERMANN, 1234567);
		this.waehrung = Waehrung.EUR;
		//Aufruf eines anderen Konstruktors. Der Compiler entscheidet
		//anhand der Parameterliste, welcher Konstruktor gemeint ist
	}

	/**
	 * liefert den Kontoinhaber zur�ck
	 * @return   der Inhaber
	 */
	public final Kunde getInhaber() {
		//final bei einer Methode bedeutet, dass die Methode
		//in Unterklassen nicht �berschrieben werden kann. Hier
		//sorgt das daf�r, dass die Klasse Konto ganz alleine f�r
		//die Verwaltung des Kontoinhabers zust�ndig ist und keine
		//Unterklasse durch �berschreibung ihren Code "hineinschummeln" kann.
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		//Sinn der set-Methode ist es vor allem, von au�en kommende
		//Werte erst einmal zu pr�fen, bevor man sie tats�chlich
		//in einem Attribut speichert. Man muss nicht zwingend
		//eine Exception bei einem falschen Parameterwert werfen,
		//aber es ist nat�rlich eine M�glichkeit.
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(this.gesperrt)
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;
	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   double
	 */
	public final double getKontostand() {
		return kontostand;
	}

	/**
	 * liefert die Kontonummer zur�ck
	 * @return   long
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * liefert zur�ck, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public final boolean isGesperrt() {
		//Achtung Namenskonventionen: bei booleschen Attributen
		//hei�t die get-Methode nicht get..., sondern is...
		return gesperrt;
	}
	
	/**
	 * Erh�ht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0 || Double.isNaN(betrag)) {
			//Denken Sie immer daran, was alles schie� gehen kann!
			throw new IllegalArgumentException("Falscher Betrag");
		}
		setKontostand(getKontostand() + betrag);
	}

	public void einzahlen(double betrag, Waehrung waehrung){
		this.einzahlen(waehrung.umrechnen(betrag, this.waehrung));
	}
	
	/**
	 * Gibt eine Zeichenkettendarstellung der Kontodaten zur�ck.
	 */
	@Override
	/* @Override wurde in Java 7 eingef�hrt und ist beim �berschreiben
	von Methoden zwar nicht Pflicht, aber sinnvoll. Der Compiler
	passt dann auf, dass Sie sich nicht zum Beispiel beim Methodennamen
	vertippen - dann klappt's mit dem �berschreiben n�mlich nicht.
	toString wird von der Klasse Object geerbt, von der alle Klassen erben.*/
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		/* Bitte verwenden Sie nicht "\n". Das ist plattformspezifisch, auf
		Windowssysteme schreibt sich der Zeilenumbruch so "\r\n". 
		System.getProperty("line.separator") und System.lineSeparator()
		liefern den korrekten Zeilenumbruch des aktuellen Betriebssystems */
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + this.getKontostandFormatiert();
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist.
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag) 
								throws GesperrtException;
	/* abstrakte Methode ohne Implementierung. Diese Methode
	muss von Unterklassen implementiert werden, wenn davon
	Objekte erstellt werden sollen.
	GesperrtException ist eine checked Exception (erbt also direkt von Exception,
	nicht von RunTimeException) und muss deshalb angek�ndigt werden. */
	
	/**
	 * Abheben mit anderer Währung
	 * @param betrag 
	 * @param waehrung
	 * @return
	 * @throws GesperrtException
	 */
	public boolean abheben(double betrag, Waehrung waehrung) throws GesperrtException {
		return this.abheben(waehrung.umrechnen(betrag, this.waehrung));
	}

	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr m�glich.
	 */
	public final void sperren() {
		this.gesperrt = true;
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder m�glich.
	 */
	public final void entsperren() {
		this.gesperrt = false;
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public final String getGesperrtText()
	//Man darf nat�rlich auch get-Methoden f�r aus den Attribute berechnete
	//Werte schreiben.
	{
		if (this.gesperrt)
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
		//Am Aufruf �ber den Klassennamen String sehen Sie, dass es
		//sich bei format um eine statische Methode handelt. Nicht-
		//statische Methoden werden immer �ber ein Objekt aufgerufen.
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und W�hrungssymbol �
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f " + this.waehrung.name() , this.getKontostand());
	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other das Vergleichskonto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	//�berschreiben der von Object geerbten equals-Methode
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
		//Verst�ndnis ist hier nicht wichtig. Das ist halt eine
		//M�glichkeit, einen mehr oder weniger sinnvoll verteilten
		//Hashcode aus der Kontonummer zu berechnen.
	}

	@Override
	public int compareTo(Konto other)
	//Die Methode aus dem Comparable-Interface
	{
		if(other.getKontonummer() > this.getKontonummer())
			return -1;
		if(other.getKontonummer() < this.getKontonummer())
			return 1;
		return 0;
	}
	
	/**
	 * gibt dieses Konto auf der Konsole aus
	 */
	public void ausgeben()
	//Diese Methode geh�rt hier eindeutig nicht her, weil die Klasse Konto
	//zur Verarbeitung geh�rt, nicht zu Ein-/Ausgabe (-> goldene Regel)
	//Sie dient der Verdeutlichung der Polymorphie.
	{
		System.out.println(this.toString());
	}
}
