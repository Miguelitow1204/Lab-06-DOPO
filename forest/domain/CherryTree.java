package domain;
import java.awt.Color;

/**
 * Represents a cherry tree in the forest.
 * It starts green and blooms to pink every 4 tictacs, then returns to green.
 * It loses energy and die when it is 0
 * 
 * @author (Murillo-Rubiano) 
 * 
 */
public class CherryTree extends Tree{
    private boolean bloom;
    
    /**
     * Creates a new cherry tree in the forest at the given position
     * @param forest
     * @param row
     * @param column
     */
    public CherryTree(Forest forest, int row, int column){
        super(forest, row, column);
        this.bloom = false;
        this.color = Color.GREEN;
    }
    
    /**
     * Executes one simulation step for the cherry tree.
     * Blooms every 4 tic-tac, loses energy and die when it is 0
     */
    @Override
    public void ticTac(){
        super.ticTac();
        if(getTicTac() % 4 == 0){
            bloom = !bloom;
        }
        if(bloom){
            color = new Color(205, 105, 180); //rosado
        } else {
            color = Color.GREEN;
        }
    }
    
}