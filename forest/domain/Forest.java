package domain;

import java.util.*;
import java.io.Serializable;

/*No olviden adicionar la documentacion*/
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

    public int getSize() {
        return SIZE;
    }

    public Thing getThing(int r, int c) {
        return places[r][c];
    }

    public void setThing(int r, int c, Thing e) {
        places[r][c] = e;
    }

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

    public boolean isEmpty(int r, int c) {
        return (inForest(r, c) && places[r][c] == null);
    }

    private boolean inForest(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Executes one simulation step in the forest
     * Iterates over all cells, calling ticTac() on each non-null element found.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] instanceof Squirrel) {
                    ((Squirrel) places[r][c]).acted = false;
                }
            }
        }
        Thing[][] copy = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                copy[r][c] = places[r][c];
            }
        }
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] != null) {
                    places[r][c].ticTac();
                }
            }
        }

    }
    
    public void open(java.io.File file) throws ForestException{
        try{
            //leer objetos java desde un archivo binario
            java.io.ObjectInputStream objectInput = new java.io.ObjectInputStream(new java.io.FileInputStream(file));
            //Leer el objeto guardado en el archivo
            Forest loaded = (Forest) objectInput.readObject();
            objectInput.close();
            //Restaurar el estado del forest actual con el del archivo
            this.places = loaded.places;
        } catch (java.io.IOException e){
            throw new ForestException("Error al abrir el archivo " + file.getName());
        } catch (ClassNotFoundException e){
            throw new ForestException("El archivo " + file.getName() + "no es un forest válido.");
        }
    }
    
    public void saveAs(java.io.File file) throws ForestException{
        try{
            java.io.ObjectOutputStream objectOutput =new java.io.ObjectOutputStream(new java.io.FileOutputStream(file));
            objectOutput.writeObject(this);
            objectOutput.close();
        } catch(java.io.IOException e) {
            throw new ForestException("Error al guardar el archivo " + file.getName());
        }
    }
    
    public void open00(java.io.File file) throws ForestException{
        throw new ForestException("Opción Open en construcción. Archivo " + file.getName());
    }
    
    public void saveAs00(java.io.File file) throws ForestException{
        throw new ForestException("Opción Save en construcción. Archivo " + file.getName());
    }
    
    public void importAs(java.io.File file) throws ForestException {
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
            //Limpiar el forest actual antes de importar
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    places[r][c] = null;
                }
            }
            String line;
            //Leer cada línea del archivo
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    //Separar tipo de coordenadas
                    String[] parts = line.split(" ");
                    String type = parts[0];
                    //Separar fila y columna por la coma
                    int r = Integer.parseInt(parts[1].replace(",", "").trim());
                    int c = Integer.parseInt(parts[2].trim());
                    //Crear el objeto según el tipo
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
            //Lista para registrar los objetos ya exportados y evitar duplicados
            java.util.List<Thing> exported = new java.util.ArrayList<>();
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (places[r][c] != null && !exported.contains(places[r][c])) {
                        //Marcar este objeto como ya exportado
                        exported.add(places[r][c]);
                        String type = places[r][c].getClass().getSimpleName();
                        //Formato estándar: tipo fila, columna
                        writer.println(type + " " + r + ", " + c);
                    }
                }
            }
            writer.close();
        } catch (java.io.IOException e) {
            throw new ForestException("Error al exportar el archivo " + file.getName());
        }
    }
    
    public void importAs00(java.io.File file) throws ForestException{
        throw new ForestException("Opción Import en construcción. Archivo " + file.getName());
    }
    
    public void exportAs00(java.io.File file) throws ForestException{
        throw new ForestException("Opción Export en construcción. Archivo " + file.getName());
    }
}
