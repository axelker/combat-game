package combatant;
import java.util.*;

import constante.Constante;
import element.*;
import game.Coordonne;

/**
 * Classe effectuant une action aléatoire pour le combattant
 */
public class StrategyAleatoire extends StrategyAbstract {
    /**
     * Représente les actions possible sous forme de String
     */
    private final String[] action = new String[]{"deplacement","Tir","Mine","Bombe","Bouclier","attente"};

    @Override
    public String strategyCombat(Combattant combattant,Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement){

        Random r = new Random();
        int choix = r.nextInt(action.length);
        String play = action[choix];

         switch(play){
            /////Deplacement///
            case "deplacement":
                //Recuperer la liste des déplacements autorisés pour le combattant et tirer aléatoirement une coordonné de déplacement dans cette liste 
                List<Coordonne> listedeplacement = combattant.listeDeplacement(mapCombattant,mapElement);
                String res=super.combattantPosition(combattant) +" essaye de se déplacer mais cela est impossible"; 
                if(!listedeplacement.isEmpty()) {
                    Coordonne c = listedeplacement.get(r.nextInt(listedeplacement.size()));
                    res= DeplacementToString(combattant,c);
                    super.UseDeplacement(combattant,c,mapElement,mapCombattant);
                    return res;
                }
                return res;

            /////Tir/////
            case "Tir":
                //Tirer une direction aléatoire et récupérer la liste des tires grace à la direction aléatoire
                String direction = Constante.listeDirection.get(r.nextInt(4));
                List<Coordonne> listeTir =combattant.listCoordonneTir(direction,mapElement);
                
                //Appliquer les tirs sur les combattants touchées
                super.UseTir(combattant,listeTir,mapCombattant);
                
                return TirToString(combattant,direction,listeTir);
            
            /////Mine/////
            case "Mine":
                List<Coordonne>listeMine=combattant.ListeDepot(mapElement);
                if(!listeMine.isEmpty()){
                    Coordonne m = listeMine.get(r.nextInt(listeMine.size()));
                    combattant.depotMine(m.getx(),m.gety(),mapElement);
                    return MineToString(combattant,m);

                }
                return super.combattantPosition(combattant) +" essaye de déposer une mine mais cela est impossible";                


            /////Bombe///
            case "Bombe":
                List<Coordonne>listeBombe=combattant.ListeDepot(mapElement);
                if(!listeBombe.isEmpty()){
                    Coordonne b = listeBombe.get(r.nextInt(listeBombe.size()));
                    combattant.depotBombe(b.getx(),b.gety(),mapElement);
                    return BombeToString(combattant,b);
                }
                return super.combattantPosition(combattant) +" essaye de déposer une bombe mais cela est impossible";                
        
            ///Bouclier///
            case "Bouclier":
                combattant.activeBouclier();
                return BouclierToString(combattant);
            ////Rien faire ////
            case "attente":
                return AttenteToString(combattant);

            default:
                throw new IllegalArgumentException("Valeur de l'action pour strategy aleatoire inconnu");

           
         }


        

    }

}