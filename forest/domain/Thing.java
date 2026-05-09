package domain;
import java.awt.Color;
import java.io.Serializable;


/**
 * Represents any object that can exist inside the forest simulation.
 * A Thing can perform actions over time and define visual or behavioral
 * characteristics such as shape, color, and interaction rules.
 * 
 * All implementing classes must be serializable.
 * 
 * @author Murillo-Rubiano
 * @version 1.0
 */
public interface Thing extends Serializable {
  public static final int SQUARE = 2;
  public static final int ROUND = 1;
    
   
  /**
   * Executes the action corresponding to a simulation cycle.
   * Implementing classes define the specific behavior.
   */
  public void ticTac();
  
  /**
   * Returns the shape of the object.
   * By default, the shape is square
   * 
   * @return an integer representing the shape of the object
   */
  public default int shape(){
      return SQUARE;
  }
  
  /**
   * Returns the display color of the object.
   * By default, the color is black.
   * 
   * @return the color associated with the object
   */
  public default Color getColor(){
      return Color.black;
  }
  
  /**
   * Indicates whether this object can exist alone in a cell.
   * By default, objects are considered unique in their position.
   * 
   * @return true if the object must be alone, false otherwise
   */
  public default boolean isOnlyThing(){
      return true;
  }
  
  /**
   * Indicates whether the object is considered a living being.
   * By default, objects are not living beings.
   * 
   * @return true if the object is alive, false otherwise
   */
  public default boolean isLivingThing(){
      return false;
  }    
     
}
