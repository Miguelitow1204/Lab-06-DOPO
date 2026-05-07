package domain;
import java.awt.Color;

/**
 * Represents a shadow in the forest.
 * Shadows are black dots that spread darkness across their entire row,
 * and move from south to north circularly.
 * 
 * @author (Murillo-Rubiano) 
 * 
 */
public class Shadow implements Thing{
    private Forest forest;
    private int row, column;
    
    /**
     * Creates a new shadow at the given position in the forest
     * @param forest
     * @param row
     * @param column
     */
    public Shadow(Forest forest, int row, int column){
        this.forest = forest;
        this.row = row;
        this.column = column;
        this.forest.setThing(row, column, (Thing)this);
        
        for(int c = 0; c < forest.getSize(); c++){
            if(forest.isEmpty(row, c)){
                forest.setThing(row, c, (Thing)this);
            }
        }
    }
    
    /**
     * Return black color
     */
    public Color getColor(){
        return Color.BLACK;
    }
    
    /**
     * Returns the shadow's shape
     */
    public int shape(){
        return Thing.SQUARE;
    }
    
    /**
     * Moves the shadow one row north circularly.
     * If it reaches the top, it wraps around to the bottom
     */
    public void ticTac(){
        //Limpiar fila actual
        for(int c = 0; c < forest.getSize(); c++){
            if(forest.getThing(row, c) == this){
                forest.setThing(row, c, null);
            }
        }
        //Me muevo al norte
        row = (row - 1 + forest.getSize()) % forest.getSize();
        //Ocupar toda la fila nueva
        for(int c = 0; c < forest.getSize(); c++){
            if(forest.isEmpty(row, c)){
                forest.setThing(row, c, (Thing)this);
            }
        }
    }
}