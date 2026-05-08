package domain;

import java.util.*;
import java.io.Serializable;

/**
 * Representa un bosque discreto de tamaño fijo donde se sitúan objetos de tipo
 * {@code Thing}. Proporciona utilidades para la simulación (pasos de tiempo),
 * acceso a celdas, y operaciones de entrada/salida (serialización e
 * import/export
 * en formato de texto simple).
 *
 * Notas de implementación:
 * - La grilla interna es una matriz de referencias {@code places} de tamaño
 * {@code SIZE}x{@code SIZE} donde una celda nula indica ausencia de objeto.
 * - Las operaciones de serialización guardan/recuperan la instancia completa
 * (todos los campos), por lo que al cargar se reemplaza la referencia de
 * la matriz {@code places} por la proveniente del archivo.
 * - Los métodos de import/export usan un formato de texto simple: cada línea
 * tiene "Tipo fila, columna" (ej. "Tree 3, 5").
 *
 * Autor: MurilloRubiano
 */
public class Forest implements Serializable {
    static private int SIZE = 25;
    Thing[][] places;
    private static final long serialVersionUID = 1L;

    public Forest() {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someThings();
    }

    /**
     * Devuelve el tamaño (lado) del bosque.
     * 
     * @return número de filas/columnas de la grilla.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene la referencia al {@code Thing} en la posición indicada.
     * 
     * @param r fila
     * @param c columna
     * @return el objeto en esa celda o {@code null} si está vacía.
     */
    public Thing getThing(int r, int c) {
        return places[r][c];
    }

    /**
     * Coloca una referencia a {@code Thing} en la posición indicada.
     * Sobrescribe el contenido previo en la celda.
     * 
     * @param r fila
     * @param c columna
     * @param e objeto a colocar (puede ser {@code null} para vaciar la celda)
     */
    public void setThing(int r, int c, Thing e) {
        places[r][c] = e;
    }

    /**
     * Crea algunos objetos de ejemplo y los sitúa en el bosque.
     * Método auxiliar usado por el constructor para poblar una configuración
     * inicial de prueba.
     */
    public void someThings() {
        Tree beard = new Tree(this, 10, 10);
        Tree soul = new Tree(this, 15, 15);
        Squirrel scrat = new Squirrel(this, 11, 13);
        Squirrel sandy = new Squirrel(this, 13, 13);
        Shadow thief = new Shadow(this, 12, 10);
        Shadow lass = new Shadow(this, 17, 15);
        CherryTree murillo = new CherryTree(this, 3, 3);
        CherryTree rubiano = new CherryTree(this, 3, 6);
        GlowSpore murillo2 = new GlowSpore(this, 10, 2);
        GlowSpore rubiano2 = new GlowSpore(this, 5, 10);
    }

    /**
     * Cuenta cuántos vecinos (entre las 8 celdas alrededor) pertenecen a la
     * misma clase que el objeto en la posición dada.
     * 
     * @param r fila
     * @param c columna
     * @return número de vecinos del mismo tipo; 0 si la celda está vacía o fuera
     *         del bosque.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inForest(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inForest(r + dr, c + dc) &&
                            (places[r + dr][c + dc] != null)
                            && (places[r][c].getClass() == places[r + dr][c + dc].getClass()))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Indica si la posición está dentro de los límites del bosque y la celda
     * está vacía.
     * 
     * @param r fila
     * @param c columna
     * @return {@code true} si la celda existe y es {@code null}.
     */
    public boolean isEmpty(int r, int c) {
        return (inForest(r, c) && places[r][c] == null);
    }

    /**
     * Comprueba si las coordenadas están dentro de los límites válidos de la
     * grilla.
     */
    private boolean inForest(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Ejecuta un paso de simulación: prepara el estado de los actores y llama
     * a {@code ticTac()} en cada {@code Thing} presente.
     *
     * Comportamiento importante:
     * - Reinicia la marca {@code acted} para las ardillas antes de la ronda,
     * de modo que cada ardilla pueda actuar una vez por tic.
     * - Crea una copia superficial de la matriz de referencias como snapshot
     * (no es una copia profunda de los objetos). Esto evita problemas si se
     * quisiera iterar sobre el snapshot en lugar de la matriz original.
     * En la implementación actual la copia no se usa para invocar los
     * métodos (se itera sobre {@code places}), por lo que es una evaluación
     * parcial de la intención original y se ha documentado para referencia.
     */
    public void ticTac() {
        // Reiniciar bandera de actuación en ardillas para permitir una acción por tic.
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] instanceof Squirrel) {
                    ((Squirrel) places[r][c]).acted = false;
                }
            }
        }
        // Crear una copia superficial (referencias) de la grilla.
        Thing[][] copy = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                copy[r][c] = places[r][c];
            }
        }
        // Invocar el paso de simulación en cada Thing presente.
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] != null) {
                    places[r][c].ticTac();
                }
            }
        }

    }

    /**
     * Abre un archivo serializado y reemplaza el estado actual del bosque con su
     * contenido.
     * Valida que el objeto leído corresponda a una instancia de {@code Forest}.
     *
     * @param file archivo de entrada que contiene un bosque serializado.
     * @throws ForestException si el archivo no existe, está corrupto, contiene una
     *                         clase desconocida, no representa un bosque válido o
     *                         ocurre un error de lectura.
     */
    public void open(java.io.File file) throws ForestException {
        try {
            java.io.ObjectInputStream objectInput = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
            Object obj = objectInput.readObject();
            objectInput.close();
            // Verifica el tipo antes del cast para evitar cargar datos incompatibles.
            if (!(obj instanceof Forest)) {
                throw new ForestException("El archivo " + file.getName() + " no contiene un forest válido.");
            }
            Forest loaded = (Forest) obj;
            // Reemplaza la referencia completa de la grilla por la versión cargada.
            this.places = loaded.places;
        } catch (java.io.FileNotFoundException e) {
            throw new ForestException("El archivo " + file.getName() + " no fue encontrado.");
        } catch (java.io.StreamCorruptedException e) {
            throw new ForestException("El archivo " + file.getName() + " está corrupto o no es un archivo válido.");
        } catch (ClassNotFoundException e) {
            throw new ForestException("El archivo " + file.getName() + " contiene datos de una clase desconocida.");
        } catch (java.io.IOException e) {
            throw new ForestException("Error de lectura al abrir el archivo " + file.getName() + ".");
        }
    }

    /**
     * Guarda el estado actual del bosque en un archivo usando serialización de
     * objetos.
     *
     * @param file archivo de salida donde se almacenará el bosque serializado.
     * @throws ForestException si no hay permisos para escribir o ocurre un error de
     *                         escritura.
     */
    public void saveAs(java.io.File file) throws ForestException {
        try {
            java.io.ObjectOutputStream objectOutput = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(file));
            // Serializa el objeto completo, incluida la matriz places y su contenido.
            objectOutput.writeObject(this);
            objectOutput.close();
        } catch (SecurityException e) {
            throw new ForestException("No tiene permisos para guardar el archivo " + file.getName() + ".");
        } catch (java.io.IOException e) {
            throw new ForestException("Error de escritura al guardar el archivo " + file.getName() + ".");
        }
    }

    /**
     * construcción de la operación de apertura.
     * Lanza {@code ForestException} indicando que la funcionalidad aún no está
     * implementada.
     * 
     * @param file archivo solicitado
     * @throws ForestException siempre que se invoque (a modo de placeholder).
     */
    public void open00(java.io.File file) throws ForestException {
        throw new ForestException("Opción Open en construcción. Archivo " + file.getName());
    }

    /**
     * construcción de la operación de guardado.
     * 
     * @param file archivo destino
     * @throws ForestException indicando que la operación no está implementada.
     */
    public void saveAs00(java.io.File file) throws ForestException {
        throw new ForestException("Opción Save en construcción. Archivo " + file.getName());
    }

    public void importAs(java.io.File file) throws ForestException {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            // Limpiar el forest actual antes de importar
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    places[r][c] = null;
                }
            }
            String line;
            // Leer cada línea del archivo
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    // Separar tipo de coordenadas
                    String[] parts = line.split(" ");
                    String type = parts[0];
                    // Separar fila y columna por la coma
                    int r = Integer.parseInt(parts[1].replace(",", "").trim());
                    int c = Integer.parseInt(parts[2].trim());
                    // Crear el objeto según el tipo
                    if (type.equals("Tree")) {
                        new Tree(this, r, c);
                    } else if (type.equals("CherryTree")) {
                        new CherryTree(this, r, c);
                    } else if (type.equals("Squirrel")) {
                        new Squirrel(this, r, c);
                    } else if (type.equals("Shadow")) {
                        new Shadow(this, r, c);
                    } else if (type.equals("GlowSpore")) {
                        new GlowSpore(this, r, c);
                    } else {
                        throw new ForestException("Error al importar el archivo " + file.getName());
                    }
                }
            }
            reader.close();
        } catch (java.io.IOException e) {
            throw new ForestException("Error al importar el archivo " + file.getName());
        }
    }

    public void exportAs(java.io.File file) throws ForestException {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(file));
            // Lista para registrar los objetos ya exportados y evitar duplicados
            java.util.List<Thing> exported = new java.util.ArrayList<>();
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (places[r][c] != null && !exported.contains(places[r][c])) {
                        // Marcar este objeto como ya exportado
                        exported.add(places[r][c]);
                        String type = places[r][c].getClass().getSimpleName();
                        // Formato estándar: tipo fila, columna
                        writer.println(type + " " + r + ", " + c);
                    }
                }
            }
            writer.close();
        } catch (java.io.IOException e) {
            throw new ForestException("Error al exportar el archivo " + file.getName());
        }
    }

    /**
     * Placeholder para una variante de importación aún no implementada.
     * 
     * @param file archivo de importación
     * @throws ForestException indicando que la operación no está implementada.
     */
    public void importAs00(java.io.File file) throws ForestException {
        throw new ForestException("Opción Import en construcción. Archivo " + file.getName());
    }

    /**
     * Placeholder para una variante de exportación aún no implementada.
     * 
     * @param file archivo de exportación
     * @throws ForestException indicando que la operación no está implementada.
     */
    public void exportAs00(java.io.File file) throws ForestException {
        throw new ForestException("Opción Export en construcción. Archivo " + file.getName());
    }

    /**
     * Variante alternativa de apertura que lee la instancia serializada y
     * reemplaza la grilla interna por la recuperada del archivo.
     * 
     * @param file archivo serializado de entrada
     * @throws ForestException en caso de error de E/S o clase desconocida.
     */
    public void open01(java.io.File file) throws ForestException {
        try {
            java.io.ObjectInputStream objectInput = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
            Forest loaded = (Forest) objectInput.readObject();
            objectInput.close();
            this.places = loaded.places;
        } catch (java.io.IOException e) {
            throw new ForestException("Error al abrir el archivo " + file.getName());
        } catch (ClassNotFoundException e) {
            throw new ForestException("El archivo " + file.getName() + "no es un forest válido.");
        }
    }

    /**
     * Variante alternativa de guardado por serialización.
     * 
     * @param file archivo destino
     * @throws ForestException en caso de error de escritura.
     */
    public void saveAs01(java.io.File file) throws ForestException {
        try {
            java.io.ObjectOutputStream objectOutput = new java.io.ObjectOutputStream(
                    new java.io.FileOutputStream(file));
            objectOutput.writeObject(this);
            objectOutput.close();
        } catch (java.io.IOException e) {
            throw new ForestException("Error al guardar el archivo " + file.getName());
        }
    }
}
