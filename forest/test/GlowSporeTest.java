package test;

import domain.*;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class GlowSporeTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GlowSporeTest
{
    // Pruebas GlowSpore - Ciclo 5

    @Test
    public void testGlowSporeInitialColor() {
        Forest forest = new Forest();
        GlowSpore murillo2 = new GlowSpore(forest, 7, 7);
        // Debe iniciar en fase morada oscura (purplePhase = true)
        assertEquals(new Color(148, 0, 211), murillo2.getColor());
    }

    @Test
    public void testGlowSporeDiesAfter5TicTacs() {
        Forest forest = new Forest();
        GlowSpore rubiano2 = new GlowSpore(forest, 7, 7);
        for (int i = 0; i < 5; i++) {
            rubiano2.ticTac(); // llamamos directo a la espora, no al forest
        }
        // Verificar que ya no existe en ninguna celda del bosque
        boolean found = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == rubiano2) {
                    found = true;
                }
            }
        }
        assertFalse(found);
    }

    @Test
    public void testGlowSporeSpread() {
        // Intencion: verificar que después de 3 tic-tacs la GlowSpore
        // dispersa una espora y existe al menos una nueva en el bosque
        Forest forest = new Forest();
        GlowSpore murillo2 = new GlowSpore(forest, 7, 7);
        for (int i = 0; i < 3; i++) {
            forest.ticTac();
        }
        int count = 0;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) instanceof GlowSpore) {
                    count++;
                }
            }
        }
        // Debe haber al menos 2 GlowSpores: la original + 1 espora
        assertTrue(count >= 2);
    }
}