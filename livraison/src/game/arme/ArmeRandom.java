package arme;
import java.util.*;

/**
 * Class représentant une arme dont les caractéristique sont tirés aléatoirement.
 */
public class ArmeRandom implements Arme {

    private Random rand = new Random();
    /**
     * Degat de l'arme
     */
    private final int degat=rand.nextInt(40)+1;
    /**
     * Portée de l'arme
     */
    private final int porte=rand.nextInt(9)+1;

    public int getDegat(){
        return this.degat;
    }
    public int getPorte(){
        return this.porte;
    }
}