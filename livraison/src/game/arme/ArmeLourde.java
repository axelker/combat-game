package arme;
import java.util.*;

/**
 * Class représentant une arme lourde
 */
public class ArmeLourde implements Arme {
     /**
     * Degat de l'arme
     */
    private final int degat=50;
    /**
     * Portée de l'arme
     */
    private final int porte=3;

    public int getDegat(){
        return this.degat;
    }
    public int getPorte(){
        return this.porte;
    }
}