package combatant;
import java.util.*;
import element.*;
import game.Coordonne;

public abstract class StrategyAbstract implements StrategyCombattant {

    /**
     * Effectue le déplacement du combattant à la nouvelle coordonnée et intervertis son emplacement précendent
     * par une case vide
     * @param combattant Le combattant voulant effectuer un déplacement
     * @param newCoord  Nouvelle coordonnée du combattant
     * @param mapElement map contenant les éléments du plateau
     * @param mapCombattant map contenant les combattants plateau
     */
    public void UseDeplacement(Combattant combattant,Coordonne newCoord,Map<Coordonne,Element>mapElement,Map<Coordonne,Combattant>mapCombattant){
        Map<Coordonne,Combattant> tmp = new HashMap<Coordonne,Combattant>(mapCombattant);
         //Remplacer l'emplacement acutelle du combattant par le vide donc le supprimer puis le déplacer
         mapElement.put(combattant.getCoordonne(),new Vide(combattant.getx(),combattant.gety()));
        
        Combattant comb = mapCombattant.get(combattant.getCoordonne());
        tmp.remove(combattant.getCoordonne());

        //Si l'élément est une case vide supprimer la case vide de la mapElement
        if(mapElement.containsKey(newCoord)){
            if(mapElement.get(newCoord) instanceof Vide){
                mapElement.remove(newCoord);
            }
        }
       
        tmp.put(newCoord,comb); 
        mapCombattant=tmp;
        mapCombattant.get(newCoord).deplacement(newCoord.getx(),newCoord.gety());
    }

    public void UseTir(Combattant combattant,List<Coordonne>listeTouche,Map<Coordonne,Combattant>mapCombattant){
         
        //Appliquer les tirs sur les combattants touchées donc présent dans la liste
         for(Coordonne cord : listeTouche){
            if(mapCombattant.containsKey(cord)){
                Combattant tmp = mapCombattant.get(cord);
                //Verifier que le BOUCLIER est pas activées pour appliqué les degats
                if(tmp.getBouclier()==false)
                {
                    tmp.setPointVie(tmp.getpointVie()-combattant.getArme().getDegat());
                    mapCombattant.put(cord,tmp);
                }
               
            }
        }
    }
      
    /**
     * Retourne sous forme de chaine la position du combattant associé à une chaine
     * @param combattant le combattant actuelle
     * @return chaine de caractère illustrant la position du combattant
     */
    public String combattantPosition(Combattant combattant){
        return "Le combattant de coordonnée : "+combattant.getCoordonne().toString();
    }

    /**
    * @param combattant le combattant actuelle
    * @param c nouvelle coordonnée du combattant
    * @return retourne sous forme de chaine de caractère le déplacement du combattant ainsi que sa nouvelle postion
    */
    public String DeplacementToString(Combattant combattant,Coordonne c){
        return combattantPosition(combattant) + " se déplace à la coordonnée : " + c.toString();

    }
    /**
     * Retourne sous forme de chaine de caractère les coordonné impactées par le tir du combattant
     * @param combattant le combattant effectuant un tir
     * @param direction spécifie le chemin du tir
     * @param listeTir la liste des coordonnées impactées
     * @retrun retourne sous forme de chaine de caractère le tir du combattant et les positions étant impactées
     */
    public String TirToString(Combattant combattant,String direction,List<Coordonne>listeTir){
        
        String res= combattantPosition(combattant)+"tire sur la direction "+ direction +" et impacte les coordonnées : \n";
       
        for(Coordonne tmp : listeTir){
            res+=tmp.toString()+"  ";
        }
        return res;
    }
    /**
     * @param combattant le combattant posant une bombe
     * @param b la coordonné de la bombe posée
     * @retrun retourne sous forme de chaine de caractère la position de la bombe déposé par le combattant
     */
    public String BombeToString(Combattant combattant,Coordonne b){

        return combattantPosition(combattant)+" dépose une bombe à la position "+b.toString();

    }
    /**
     * @param combattant le combattant posant une mine
     * @param m la coordonné de la mine posée
     * @retrun retourne sous forme de chaine de caractère la position de la mine déposé par le combattant
     */
    public String MineToString(Combattant combattant,Coordonne m){
        return combattantPosition(combattant)+" dépose une mine à la position :  "+m.toString();
    }
    /**
     * @param combattant le combattant ayant activé le bouclier 
     * @retrun retourne sous forme de chaine de caractère le combattant activant son bouclier
     */
    public String BouclierToString(Combattant combattant){
        return combattantPosition(combattant) + "active le bouclier ";
    }
    /**
     * @param combattant le combattant en attente
     * @retrun retourne sous forme de chaine de caractère le combattant en attente
     */
    public String AttenteToString(Combattant combattant){
        return combattantPosition(combattant)+" ne fait rien pour économiser son énérgie";
    }


    public abstract String strategyCombat(Combattant combattant,Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement);

}