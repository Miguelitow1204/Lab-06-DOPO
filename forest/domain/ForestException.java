package domain;


/**
 * Exception class for Forest
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