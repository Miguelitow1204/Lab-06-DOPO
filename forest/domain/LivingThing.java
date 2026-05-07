package domain;
import java.awt.Color;
import java.io.Serializable;


public abstract class LivingThing implements Serializable{
    
    protected int years;
    private int energy;
    private static final long serialVersionUID = 1L;

    /**Create a new LivingThing
     * 
     */
    public LivingThing(){
        energy=100;
        years=0;
    }


    /**The LivingThing makes one step
     * 
     */
    final boolean step(){
        boolean ok=false;
        if (energy>=1){
            energy-=1;
            ok=true;
        }
        return ok;
    }    
    

    
     /**Returns the energy
    @return 
     */   
    public final int getEnergy(){
        return energy;
    }    

    /**It's an LivingThing
     */
    public final boolean isLivingThing(){
        return true;
    }  
    
}
