package grille;
import observer.*;
import combatant.*;
import element.*;
import game.Coordonne;
import constante.Constante;
import java.lang.*;
import java.util.*;

/**
 * Class permettant de cacher les éléments du plateau qui doivent être caché pour un combattant.
 */
public class ProxyPlateau extends AbstractModelEcoutable {

    private List<Bombe>listebombe;
    private List<Mine>listemine;
    private PlateauJeu plateau;

    public ProxyPlateau(List<Bombe>listeb,List<Mine>listem){
        this.listebombe=listeb;
        this.listemine=listem;
        this.plateau=new PlateauJeu();
        this.ajoutEcouteur(plateau);
    }

    public void setListBombe(List<Bombe>listeb){
        this.listebombe=listeb;
        fireChangement();
    }
    public void setListMine(List<Mine>listem){
        this.listemine=listem;
        fireChangement();
    }
    public void setPlateu(PlateauJeu p){
        this.plateau=p;
        fireChangement();
    }

    public String getStringElementPlateau(int i,int j){
        
        //Sortir l'élément a tester
        String element = this.plateau.getStringElementPlateau(i,j);
       
        //Si l'élément est explosif 
        if(Constante.explosiftoString.contains(element)){

            Coordonne c = new Coordonne(i,j);

            //Parcour des bombes et mines si elles correspondent à celle du combattant alors les retourner sous leur forme
            for(Bombe b : this.listebombe){
                if(b.getCoordonne().equals(c)){
                    return element;
                }
            }
            for(Mine m : this.listemine){
                if(m.getCoordonne().equals(c)){
                    return element;
                }
            }

            //Sinon retourner un vide
            return " ";
           
        }
        return element;
    }



}
