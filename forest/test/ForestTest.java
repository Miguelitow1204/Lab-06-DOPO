package test;

import domain.*;

import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class ForestTest.
 *
 * @author (Murillo-Rubiano)
 */
public class ForestTest {
    // Pruebas método ticTac() de Forest
    @Test
    public void testTicTacChangeColor() {
        Forest forest = new Forest();
        Tree beard = new Tree(forest, 10, 10);
        Color colorBefore = beard.getColor();
        forest.ticTac();
        assertNotEquals(colorBefore, beard.getColor());
    }

    @Test
    public void testTicTacMultipleTrees() {
        Forest forest = new Forest();
        Tree beard = new Tree(forest, 10, 10);
        Tree soul = new Tree(forest, 15, 15);
        forest.ticTac();
        assertEquals(Color.GREEN, beard.getColor());
        assertEquals(Color.GREEN, soul.getColor());
    }

    @Test
    public void testSaveAsCreatesFile() {
        Forest forest = new Forest();
        // Creamos un archivo temporal en el directorio del proyecto
        java.io.File file = new java.io.File("forestTest.dat");
        try {
            forest.saveAs(file);
            // El archivo debe existir después de guardar
            assertTrue(file.exists());
        } catch (ForestException e) {
            fail("No debería lanzar excepción al guardar correctamente");
        }
        // Limpiamos el archivo creado
        file.delete();
    }

    @Test
    public void testSaveAsInvalidPath() {
        Forest forest = new Forest();
        // Ruta inválida para forzar el error
        java.io.File file = new java.io.File("/ruta/invalida/forestTest.dat");
        try {
            forest.saveAs(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            // Se espera la excepción
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testOpenRestoresState() {
        Forest forest = new Forest();
        // Guardamos el estado inicial
        java.io.File file = new java.io.File("forestOpen.dat");
        try {
            forest.saveAs(file);
            // Creamos un nuevo forest vacío y le abrimos el archivo
            Forest forest2 = new Forest();
            forest2.open(file);
            // El estado debe ser igual al guardado
            for (int r = 0; r < forest.getSize(); r++) {
                for (int c = 0; c < forest.getSize(); c++) {
                    assertEquals(forest.getThing(r, c) == null, forest2.getThing(r, c) == null);
                }
            }
        } catch (ForestException e) {
            fail("No debería lanzar excepción");
        }
        // Limpiamos el archivo creado
        file.delete();
    }

    @Test
    public void testOpenInvalidFile() {
        Forest forest = new Forest();
        // Archivo que no existe
        java.io.File file = new java.io.File("noExiste.dat");
        try {
            forest.open(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            // Se espera la excepción
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testExportAsCreatesFile() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("forestExport.txt");
        try {
            forest.exportAs(file);
            // El archivo debe existir después de exportar
            assertTrue(file.exists());
        } catch (ForestException e) {
            fail("No debería lanzar excepción al exportar correctamente");
        }
        file.delete();
    }

    @Test
    public void testExportAsInvalidPath() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("/ruta/invalida/forestExport.txt");
        try {
            forest.exportAs(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testImportAsRestoresState() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("forestImport.txt");
        try {
            // Exportamos el estado actual
            forest.exportAs(file);
            // Creamos un nuevo forest y le importamos el archivo
            Forest forest2 = new Forest();
            forest2.importAs(file);
            // El estado debe ser igual al exportado
            for (int r = 0; r < forest.getSize(); r++) {
                for (int c = 0; c < forest.getSize(); c++) {
                    assertEquals(forest.getThing(r, c) == null, forest2.getThing(r, c) == null);
                }
            }
        } catch (ForestException e) {
            fail("No debería lanzar excepción al importar correctamente");
        }
        file.delete();
    }

    @Test
    public void testImportAsInvalidFile() {
        Forest forest = new Forest();
        // Archivo que no existe
        java.io.File file = new java.io.File("noExiste.txt");
        try {
            forest.importAs(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testImportAsInvalidType() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("forestInvalid.txt");
        try {
            // Escribimos un archivo con un tipo inválido
            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(file));
            writer.println("TipoInvalido 10, 20");
            writer.close();
            forest.importAs(file);
            fail("Debería lanzar ForestException por tipo inválido");
        } catch (ForestException e) {
            assertNotNull(e.getMessage());
        } catch (java.io.IOException e) {
            fail("Error al crear el archivo de prueba");
        }
        file.delete();
    }

    // Pruebas Parte III - A.2: Excepciones detalladas para Open y SaveAs

    @Test
    public void testOpenFileNotFound() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("noExiste.dat");
        try {
            forest.open(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            assertTrue(e.getMessage().contains("no fue encontrado"));
        }
    }

    @Test
    public void testOpenCorruptedFile() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("corrupto.dat");
        try {
            // Crear un archivo con contenido basura
            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(file));
            writer.println("esto no es un objeto serializado");
            writer.close();
            forest.open(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            assertTrue(e.getMessage().contains("corrupto"));
        } catch (java.io.IOException e) {
            fail("Error al crear archivo de prueba");
        }
        file.delete();
    }

    @Test
    public void testSaveAsInvalidPathDetailed() {
        Forest forest = new Forest();
        java.io.File file = new java.io.File("/ruta/invalida/forest.dat");
        try {
            forest.saveAs(file);
            fail("Debería lanzar ForestException");
        } catch (ForestException e) {
            assertTrue(e.getMessage().contains("Error de escritura"));
        }
    }
}
