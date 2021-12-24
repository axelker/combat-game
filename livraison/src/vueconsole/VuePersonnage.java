package vueconsole;
import combatant.*;
import observer.*;

public class VuePersonnage implements EcouteurModel {

    public Personnage p;


    public VuePersonnage(Personnage p)
    {
        this.p=p;
        p.ajoutEcouteur(this); 
    }


    @Override
    public void modeleMisAjour(Object source)
    {
        System.out.println("Nouvelle position :" + p.toString());
    }   


}