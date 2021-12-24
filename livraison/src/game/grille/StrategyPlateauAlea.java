package grille;
import constante.*;
import combatant.*;
import element.*;
import game.Coordonne;
import java.util.*;

/**
 * Class permettant la génération d'un plateau de jeu aléatoire.
 */

public class StrategyPlateauAlea implements StrategyPlateau {

//Inclure plus de vide
/**
 * Permet de tirer au sort selon le string présent dans la liste
 */
private final List<String>aleaElement = new ArrayList<String>(List.of("mur","pastille","pastille","vide","vide","vide","vide","vide"));

    /**
     * Cette méthode permet de remplir un plateau de jeu aléatoirement dans les map en paramètre. 
     * 
     * @param mapCombattant map permettant de stocker les combattants sur le plateau
     * @param mapElement map permettant de stocker les éléments sur le plateau
     * @param factory usine de création de combattant
     */
    @Override 
    public void StrategyRemplissagePlateau(Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement,FactoryCombattant factory) {
        
        Random r = new Random();

        //Création personnage
        int lignePlacement= r.nextInt(Constante.nbLigne);
        int colonnePlacement = r.nextInt(Constante.nbColonne);

        //Placement des combattants
        for(int nbPerso=0;nbPerso<Constante.nbPersonnage;nbPerso++)
        {
            //Tirer un indice dans la liste des noms de combattant

            int indiceCombattantAlea= r.nextInt(Constante.listeName.size());
            String name = Constante.listeName.get(indiceCombattantAlea);
            Coordonne test = new Coordonne(lignePlacement,colonnePlacement);
            while(!CaseVide(test, mapCombattant, mapElement)){
                test = new Coordonne(r.nextInt(Constante.nbLigne),r.nextInt(Constante.nbColonne));
            }
            Combattant p = factory.createCombattant(name,test.getx(),test.gety());
            mapCombattant.put(p.getCoordonne(),p);
            
        }
        
        //Case restante tirer des éléments aléatoirement 
        for(int i=0;i<Constante.nbLigne;i++){
            for(int j=0;j<Constante.nbColonne;j++){
               String tirageElement = aleaElement.get(r.nextInt(aleaElement.size()));
               Element test;
               switch(tirageElement.toLowerCase())
               {
                    case "mur":
                        test=new Mur(i,j);
                        break;

                    case "pastille":
                        test=new Pastille(i,j);
                        break;

                    case "vide":
                        test=new Vide(i,j);
                        break;

                    default :
                        test=new Vide(i,j);
                        break;
               }

                //Si la n'est pas occuper on ajoute l'élément
                if(CaseVide(test.getCoordonne(),mapCombattant, mapElement)){
                    mapElement.put(test.getCoordonne(),test);
                    
                }
            }
        } 
    }

    public boolean CaseVide(Coordonne test ,Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement){
        if(!mapElement.containsKey(test) && !mapCombattant.containsKey(test)){
            return true;
        }
        return false;
    }

}