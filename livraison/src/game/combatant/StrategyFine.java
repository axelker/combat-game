package combatant;
import java.util.*;
import constante.Constante;
import element.*;
import game.Coordonne;
/**
 * Class permettant de spécifier une stratégie recherché d'un combattant
 */
public class StrategyFine  extends StrategyAbstract {


    @Override
    public String strategyCombat(Combattant combattant,Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement){

        Random r = new Random();

        //Priorité au tir car ne coute aucune énérgie et risque
        String bestdirection="";
        int compteurBest=0;
        List<Coordonne> listeTir = new ArrayList<Coordonne>();

        //Tester quelle direction touche le plus de combattants
        for(String direction : Constante.listeDirection ) {
            int compteurtmp=0;
            List<Coordonne> listeDesTir =combattant.listCoordonneTir(direction,mapElement);
            compteurtmp=IncremValue(listeDesTir,mapCombattant);
    
            if(compteurtmp>compteurBest){
                compteurBest=compteurtmp;
                bestdirection=direction;
                listeTir=new ArrayList<Coordonne>(listeDesTir);
            }
        }
        //Si des combattants peuvent être impactés par le tir alors l'effectuer
         if(compteurBest!=0){
            //Appliquer les tirs sur les combattants touchées
            super.UseTir(combattant,listeTir,mapCombattant);
            return TirToString(combattant,bestdirection,listeTir);
        }

        //Mine si cela impacte un combattant alors déposer une mine
        List<Coordonne>listeMine=combattant.ListeDepot(mapElement);
        for(Coordonne cord : listeMine){
            if(mapCombattant.containsKey(cord)){
                combattant.depotMine(cord.getx(),cord.gety(),mapElement);
                return MineToString(combattant,cord);
            }
        }
           

        //Deplacement 
        List<Coordonne> listedeplacement = combattant.listeDeplacement(mapCombattant,mapElement);
        List<Coordonne> tmp = combattant.listeDeplacement(mapCombattant,mapElement);
        for(Coordonne cord : listedeplacement){
            //Vider les positions ou le combattant actuel a posés des bombes ou mine
            for(Bombe b : combattant.getListBombe()){
                if(listedeplacement.contains(b.getCoordonne())){
                    tmp.remove(b.getCoordonne());
                }
            }
            for(Mine m : combattant.getListMine()){
                if(listedeplacement.contains(m.getCoordonne())){
                    tmp.remove(m.getCoordonne());
                }
            }

        }
        listedeplacement=tmp;
        //BOMBE si la possibilité de déplacement est supérieur à 1 une bombe peut être posées
        if(listedeplacement.size()>2){
            List<Coordonne>listeBombe=combattant.ListeDepot(mapElement);
            for(Coordonne b : listeBombe ){
                combattant.depotBombe(b.getx(),b.gety(),mapElement);
                return BombeToString(combattant,b);
            }
                            
        }

        //Si la liste n'est pas vide effectuer un DEPLACEMENT( peux importe lequels celui-ci est valide et permet de ne pas marcher sur ses propres mines/bombe)
        if(!listedeplacement.isEmpty()){
            int deplacement = r.nextInt(listedeplacement.size());
            String res= DeplacementToString(combattant,listedeplacement.get(deplacement));
            super.UseDeplacement(combattant,listedeplacement.get(deplacement),mapElement,mapCombattant);
            return res;
        }
        
        //Si liste est vide donc le combattant est bloqué par bombe ,mine ,ou mur activer le bouclier
        //Bouclier 
        if(listedeplacement.isEmpty()){
            combattant.activeBouclier();
            return BouclierToString(combattant);
        }
        

        //attente si le combattant n'a plus beaucoup de point de vie
        if(combattant.getpointVie()<10){
            return AttenteToString(combattant);
        }

        //Default activé le bouclier
        return BouclierToString(combattant);


        

    }

    public int IncremValue(List<Coordonne>liste,Map<Coordonne,Combattant>mapCombattant){
        int valuetest=0;  
        for(Coordonne cord : liste){
            if(mapCombattant.containsKey(cord)){
                valuetest++;
            }
        }
        return valuetest;
    } 

}