package bankprojekt;

import bankprojekt.verarbeitung.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GirokontoTest {
    Girokonto gk;

    @BeforeEach
    void setup(){
        gk = new Girokonto();
        gk.einzahlen(500);
    }

    @AfterEach
    void teardown(){
        gk = null;
    }

    @Test
    void abhebenMitWaehrungswechsel(){
        try{
            gk.abheben(100, Waehrung.BGN);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        assertEquals((500 - 51.12918811962185), gk.getKontostand());
    }

    @Test
    void einzahlenMitWaehrungswechsel(){
        gk.einzahlen(195.583, Waehrung.BGN);

        assertEquals(600, gk.getKontostand());
    }
}
