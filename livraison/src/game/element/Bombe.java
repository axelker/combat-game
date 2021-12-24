package element;
import game.Coordonne;
import combatant.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;
import observer.*;
import constante.Constante;

/**
 * Class permettant de réprésenter une bombe sur un plateau de jeu
 */

public class Bombe extends Element{
    /**
     * Représente dégat infligé par une bombe
     */
    private final int degat=Constante.degatBombe;
    /**
     * Nombre de tour pour que la bombe explose
     */
    private int compteurtour=5; // Compteur de tour pour que bombe explose 

    public Bombe(int x,int y)
    {
        super(x,y);

    }

   /**
    * @return retourne le compteur de nombre de tour avant que la bombe explose
    */
    public int getdegat()
    {
        return this.degat;
    }

    public int getcompteur()
    {
        return this.compteurtour;
    }

    //Décrémente le compteur
    public void setCompteur()
    {
        this.compteurtour--;
        this.fireChangement();
    }

    @Override
    /**
     * Applique l'effet d'une bombe sur les 8 cases adjacente et également si le Combattant est dessus 
     * @return permet de spécifier si la bombe a explosé ou non
     * */
    public boolean effet(Map<Coordonne,Combattant>listP)
    {
        boolean modif=false;
        //Compteur de la bombe à 0
        if(compteurtour<=0){
            modif=caseAdjacente(listP);
            modif=true;
            this.fireChangement();
        } 
        //Combattant sur la bombe
        if(listP.containsKey(this.c)){
            Combattant p = listP.get(c);
            p.setPointVie(p.getpointVie()-this.degat);
            caseAdjacente(listP);
            modif=true;
            this.fireChangement();

        }
        return modif;
    }

    /**
     * Affecte les dégats de la bombe au combattants se trouvant sur les cases adjacentes de celle-ci
     * @return retourne un boolean spécifiant si l'impacte a touché un combattant
     */
    public boolean caseAdjacente(Map<Coordonne,Combattant>listP)
    {
        boolean modif=false;
        List<Coordonne>ListeEffet = new ArrayList<Coordonne>();
        int ligne=-1;
        int colonne=-1;
        int nbLigne=Constante.nbLigne;
        int nbColonne=Constante.nbColonne;
        for(int i=-1;i<2;i++){
            ligne=getx()+i;
            for(int j=-1;j<2;j++){
                colonne=gety()+j;
                //Coordonnée ne sortant pas du plateau et n'étant pas les même que la position du joueur posant 
                if(ligne>=0 && ligne<nbLigne && colonne>=0 && colonne<nbColonne && (ligne!=getx() || colonne!=gety())){
                    
                    //Ajout uniquement des coordonnées valide
                    ListeEffet.add(new Coordonne(ligne,colonne));

                    if(listP.containsKey(new Coordonne(ligne,colonne))){
                        Combattant p = listP.get(new Coordonne(ligne,colonne));
                        if(p.getBouclier()==false){
                            p.setPointVie(p.getpointVie()-this.degat);
                            modif=true;
                        }
                    }
                }
            }
        } 
        return modif; 
    }

    @Override
    public String toString()
    {
        return "B";
    }

}