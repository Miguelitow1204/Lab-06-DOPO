package domain;
import java.awt.Color;
import java.io.Serializable;


/*No olviden adicionar la documentacion*/
public interface Thing extends Serializable {
  public static final int SQUARE = 2;
  public static final int ROUND = 1;
    
   
  public void ticTac();
  
  public default int shape(){
      return SQUARE;
  }
  
  public default Color getColor(){
      return Color.black;
  }
  
  public default boolean isOnlyThing(){
      return true;
  }
  
  public default boolean isLivingThing(){
      return false;
  }    
     
}
