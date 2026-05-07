package domain;

import java.awt.Color;

/**
 * Clase que representa una espora luminiscente en el bosque.
 * 
 * La clase GlowSpore implementa la interfaz Thing y simula el comportamiento
 * de una espora que emite luz (fosforescente) que:
 * - Parpadea alternando entre morado oscuro y morado claro en cada paso de
 * simulacion
 * - Se propaga a celdas adyacentes vacías cada 3 tic-tacs
 * - Se mueve aleatoriamente a celdas adyacentes vacías
 * - Muere despues de 5 tic-tacs de existencia
 * 
 * @author Murillo-Rubiano, con apoyo de Claude Sonnet 4.6
 * @see Thing
 * @see Forest
 */
public class GlowSpore implements Thing {

    private Forest forest;
    private int row, column;
    private boolean purplePhase;
    private int age;

    /**
     * Crea una nueva espora luminiscente en la posicion especificada del bosque.
     * 
     * Inicializa la espora con:
     * - Edad inicial en 0.
     * - Fase morada oscura activa (purplePhase = true).
     * - Registro inmediato de la espora dentro de la grilla del bosque.
     * 
     * @param forest el bosque donde se crea la espora
     * @param row    la fila de la posicion (0 a filas-1)
     * @param column la columna de la posicion (0 a columnas-1)
     */
    public GlowSpore(Forest forest, int row, int column) {
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.purplePhase = true;
        this.age = 0;
        forest.setThing(row, column, this);
    }

    /**
     * Retorna el color actual de la espora.
     * 
     * El color depende de la fase activa:
     * - Fase morada oscura: (148, 0, 211)
     * - Fase morada clara: (221, 160, 221)
     * 
     * @return el color correspondiente a la fase actual
     */
    public Color getColor() {
        return purplePhase ? new Color(148, 0, 211) : new Color(221, 160, 221);
    }

    /**
     * Retorna la forma de la espora, que siempre es circular.
     * 
     * La representación visual de la espora es un círculo.
     * 
     * @return Thing.ROUND indicando que la forma es redonda/circular
     */
    public int shape() {
        return Thing.ROUND;
    }

    /**
     * Ejecuta un paso de simulacion para la espora.
     * 
     * Orden de operaciones en cada tic-tac:
     * 1. Incrementa la edad.
     * 2. Si la edad es mayor o igual a 5, la espora muere y termina el paso.
     * 3. Alterna la fase de color para simular parpadeo.
     * 4. Si la edad es multiplo de 3, intenta propagarse.
     * 5. Intenta moverse a una celda adyacente vacia.
     */
    public void ticTac() {
        age++;

        if (age >= 5) {
            die();
            return;
        }

        purplePhase = !purplePhase;

        if (age % 3 == 0) {
            spread();
        }

        move();
    }

    /**
     * Elimina la espora del bosque.
     * 
     * Libera la celda actual colocando null en la posicion ocupada.
     */
    public void die() {
        forest.setThing(row, column, null);
    }

    /**
     * Propaga una nueva espora a una celda adyacente vacia elegida al azar.
     * 
     * Considera las 8 direcciones vecinas (incluye diagonales). Si no existe
     * ninguna celda libre, no se crea una nueva espora.
     */
    private void spread() {
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };
        java.util.List<int[]> free = new java.util.ArrayList<>();
        for (int[] d : dirs) {
            if (forest.isEmpty(row + d[0], column + d[1])) {
                free.add(d);
            }
        }
        if (!free.isEmpty()) {
            int[] chosen = free.get((int) (Math.random() * free.size()));
            new GlowSpore(forest, row + chosen[0], column + chosen[1]);
        }
    }

    /**
     * Mueve la espora a una celda adyacente vacia aleatoria.
     * 
     * Si no hay celdas adyacentes vacias disponibles, la espora permanece en
     * su posicion actual. Se evalúan las 8 direcciones posibles, incluidas las
     * diagonales.
     */
    private void move() {
        // Definir las 8 direcciones posibles (incluyendo diagonales)
        int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

        // Encontrar todas las celdas adyacentes que están vacías
        java.util.List<int[]> free = new java.util.ArrayList<>();
        for (int[] d : dirs) {
            if (forest.isEmpty(row + d[0], column + d[1])) {
                free.add(d);
            }
        }

        // Si hay celdas disponibles, mover la espora a una de ellas
        if (!free.isEmpty()) {
            int[] chosen = free.get((int) (Math.random() * free.size()));
            // Liberar la posición actual
            forest.setThing(row, column, null);
            // Actualizar las coordenadas
            row += chosen[0];
            column += chosen[1];
            // Ocupar la nueva posición
            forest.setThing(row, column, this);
        }
    }
}