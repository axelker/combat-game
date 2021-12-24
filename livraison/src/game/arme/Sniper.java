package arme;
import java.util.*;
/**
 * Class permettant la représentation d'un sniper
 */
public class Sniper implements Arme {
    /**
     * Degat de l'arme
     */
    private final int degat=20;
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