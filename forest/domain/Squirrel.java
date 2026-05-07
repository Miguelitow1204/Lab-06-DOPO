package domain;
import java.awt.Color;

/**
 * Squirrel class.
 * Squirrel is brown and as the years go, it turns yellowish,
 * after 10 years dies, if there are two neighbors withan empty cell between them, 
 * a new squirrel is born.
 * They move randomly through the forest
 * 
 * @author (Murillo-Rubiano) 
 * 
 */
public class Squirrel extends LivingThing implements Thing{
    private Forest forest;
    private int row, column;
    boolean acted;
    
    /**
     * Creates a new squirrel at the given position in the forest.
     * @param forest
     * @param row
     * @param column
     */
    public Squirrel(Forest forest, int row, int column){
        this.forest = forest;
        this.row = row;
        this.column = column;
        forest.setThing(row, column, this);
    }
    
    /**
     * Returns the squirrel's color, brown fading to yellow
     */
    public Color getColor(){
        int r = Math.min(255, 139 + years * 10);
        int g = Math.min(255, 69 + years * 15);
        int b = 19;
        return new Color(r, g, b);
    }
    
    /** 
     * Return the squirrel's shape
     */
    public int shape(){
        return Thing.ROUND;
    }
    
    /**
     * Removes the squirrel from the forest
     */
    public void die(){
        forest.setThing(row, column, null);
    }
    
    /**
     * Executes one simulation step for squirrel.
     * Dies after 10 years, reproduces, and moves random
     */
    public void ticTac(){
        if(acted) return;
        acted = true;
        years++;
        if(years >= 10){
            die();
            return;
        }
        reproduce();
        move();
    }
    /**
     * Cheks if two squirrels are neighbors with one empty cell between them.
     * If so, a new squirrel is born in the empty cell
     */
    private void reproduce(){
        int[][] dirs = {{0,2},{0,-2},{2,0},{-2,0}};
        for(int[] d : dirs){
            int middler = row + d[0]/2;
            int middlec = column + d[1]/2;
            int nr = row + d[0];
            int nc = column + d[1];
            // Only reproduce if middle cell is empty and neighbor is a squirrel
            // and the neighbor is to the right or below (avoid double reproduction)
            if(forest.isEmpty(middler, middlec) && forest.getThing(nr, nc) instanceof Squirrel && (nr > row || nc > column)){
                new Squirrel(forest, middler, middlec);
            }
        }
    }
    /**
     * Moves the squirrel to a random empty cell adjacent.
     * If no empty cell is available, it stays in the current cell
     */
    private void move(){
        //Verificar las direcciones
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
        java.util.List<int[]> free = new java.util.ArrayList<>();
        for(int[] d:dirs){
            if(forest.isEmpty(row+d[0], column+d[1])){
                free.add(d);
            }
        }
        if(!free.isEmpty()){
            //tomar una celda al azar
            int[] chosen = free.get((int)(Math.random()*free.size()));
            forest.setThing(row, column, null);
            row += chosen[0];
            column += chosen[1];
            forest.setThing(row, column, (Thing)this);
        }
    }
}