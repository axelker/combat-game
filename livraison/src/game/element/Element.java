package element;
import game.Coordonne;
import combatant.*;
import observer.*;
import constante.Constante;
import java.util.*;

/**
 * La classe Element est la representation d'un élément contenu dans le plateau de jeu
 */

public class Element extends AbstractModelEcoutable {
    protected Coordonne c;
    /**
     * Constructeur par defaut.
     * @param x coordonné x qui est la ligne de l'élément
     * @param y coordonné y qui est la colonne de l'élément
     */
    public Element(int x,int y)
    {
        this.c=new Coordonne(x,y);

    }
    /**
     * Retourne la coordonné x de l'élément
     * @return la coordonné x de l'élément
     */
    public int getx()
    {
        return c.getx();
    }
    /**
     * Retourne la coordonné y de l'élément
     * @return la coordonné y de l'élément
     */
    public int gety()
    {
        return c.gety();
    }
    /**
     * 
     * @return la coordonné contenant la position x et la position y de l'élément
     */
    public Coordonne getCoordonne()
    {
        return this.c;
    }
    
    /**
     * Applique l'éffet de l'élément sur un combbattant et retourne vrai si un effet a été appliqué 
     * @param listP combattant présent sur le plateau associé à leur coordonné
     * @return l'effet d'un élément sur les combattants présent dans la liste
     */
    public boolean effet(Map<Coordonne,Combattant>listP){
        return false;
    }

}