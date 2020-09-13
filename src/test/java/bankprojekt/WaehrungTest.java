package bankprojekt;

import bankprojekt.verarbeitung.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class WaehrungTest {
    @Test
    void inEuroTest(){
        double x = 20;
        Waehrung bgn = Waehrung.BGN;
        double result = bgn.waehrungInEuroUmrechnen(x);
        assertEquals(10.22583762392437, result);
    }

    @Test
    void inWaehrungTest(){
        double x = 20;
        Waehrung bgn = Waehrung.BGN;
        double result = bgn.euroInWaehrungUmrechnen(x);
        assertEquals(39.1166, result);
    }

    @Test
    void umrechnenTest(){
        double x = 20;
        Waehrung bgn = Waehrung.BGN;
        double result = bgn.umrechnen(x, Waehrung.LTL);
        assertEquals(35.30777214788606, result);
    }
}
