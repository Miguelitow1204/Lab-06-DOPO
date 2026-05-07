package domain;
import java.awt.Color;

/**Information about a Tree<br>
<b>(forest,row,column,color)</b><br>
<br>
 */
public class Tree extends LivingThing implements Thing{
    private Forest forest;
    
    protected int row,column;    
    protected Color color;
    private int season; //[spring, summer, autumn, winter]
    private int tictac;
    
    /**Create a new Tree(<b>row,column</b>) in the forest <b>forest</b>..
     * @param forest 
     * @param row 
     * @param column 
     */
    public Tree(Forest forest,int row, int column){
        this.forest=forest;
        this.row=row;
        this.column=column; 
        this.color=Color.PINK;
        this.season=0;
        this.tictac=0;
        this.forest.setThing(row,column,(Thing)this);    
    }
    

    /**Returns the row
    @return 
     */
    public final int getRow(){
        return row;
    }

    /**Returns the column
    @return 
     */
    public final int getColumn(){
        return column;
    }

    
    /**Returns the color
    @return 
     */
    public final Color getColor(){
        return color;
    }

    /**Returns the shape
    @return 
     */
    public final int shape(){
        return Thing.ROUND;
    }

    

    /**ticTac
     */
    public void ticTac(){
        tictac++;
        color=(tictac % 4==0? Color.PINK:
               tictac % 4==1? Color.GREEN:
               tictac % 4==2? Color.ORANGE:
               Color.GRAY);
        if (tictac % 4 == 1){
            years+=1;
        }
        if (tictac % 4 == 3){
            boolean OK=step();
            if (! OK){
                die();
            }
        }
    }
      
    /**Die
     */
    public void die(){
        forest.setThing(row, column,null);
    }
  
    //Ciclo 4
    /**
     * Returns the tictac counter
     * @return tictac
     */
    public int getTicTac(){
        return tictac;
    }
}
