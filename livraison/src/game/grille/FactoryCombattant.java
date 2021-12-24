package grille;
import combatant.*;
import constante.Constante;
/**
 * La classe FactoryCombattant est une usine de création de combattant
 */
public class FactoryCombattant {

    /**
     * 
     * @param name nom du combattant.
     * @param x coordonné x du combattant.
     * @param y coordonné y du combattant.
     * @return retourne un nouveau combattant de coordonné x,y correspondant au paramètre name.
     */
    public Combattant createCombattant(String name,int x,int y){
        switch(name){
            case Constante.tankName:
                return new Tank(x,y);

            case Constante.eliteName:
                return new Elite(x, y);

            case Constante.soldatName:
                return new Soldat(x, y);

            case Constante.RandomName:
                return new RandomCombattant(x,y);

            default:
                throw new IllegalArgumentException("Valeur du paramètre name inconnu pour la factory !");
            
        }
    }
}