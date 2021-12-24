package vueconsole;
import grille.PlateauJeu;
import observer.*;

/**
 * Represente le plateau de jeu sur une console 
 */
public class VuePlateauJeu implements EcouteurModel {

    private PlateauJeu p;

public VuePlateauJeu(PlateauJeu p)
{
    this.p=p;
    p.ajoutEcouteur(this);
}



@Override
public void modeleMisAjour(Object source)
{
    System.out.println("Plateau modifié : \n"+p.toString());
    System.out.println(p.EtatCombattant());
}    



}