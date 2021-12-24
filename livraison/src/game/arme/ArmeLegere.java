package arme;
import java.util.*;

/**
 * Class représentant une arme légère
 */
public class ArmeLegere implements Arme {
    /**
     * Degat de l'arme
     */
    private final int degat=25;
    /**
     * Portée de l'arme
     */
    private final int porte=10;

    public int getDegat(){
        return this.degat;
    }
    public int getPorte(){
        return this.porte;
    }
}