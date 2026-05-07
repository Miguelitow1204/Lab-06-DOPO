package domain;


/**
 * Write a description of class ForestException here.
 * 
 * @author (Murillo-Rubiano) 
 * @version (1.0)
 */
public class ForestException extends Exception{
    public static final String OPTION_UNDER_CONSTRUCTION = "Opción en construcción";
    
    public ForestException(String message){
        super(message);
    }
}