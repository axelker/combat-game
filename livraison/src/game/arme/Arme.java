package arme;
import java.util.*;
/**
 * Représente une arme pour un combattant
 */
public interface Arme {
    /**
     * @return retourne les dégat infligés par l'arme
     */
    public int getDegat();
    /**
     * Retourne la portée de l'arme 
     */
    public int getPorte();
}