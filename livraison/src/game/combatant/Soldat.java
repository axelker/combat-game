package combatant;
import element.*;
import game.Coordonne;
import constante.Constante;
import arme.*;

/**
 * Class représentant un combattant soldat
 */


public class Soldat extends Personnage {
    /**
     * cout d'un déplacement 
     */
    private final int valueDeplacement=5;
    /**
     * cout de l'activation du bouclier
     */
    private final int valueBouclier=5;
    public Soldat(int x,int y)
    {
        super(x,y);
        this.nom=Constante.soldatName;
        this.arme=new ArmeLegere();

    }
    protected void pointVieDeplacement(){
        super.setPointVie(this.getpointVie()-this.valueDeplacement);
    }
    protected void pointVieBouclier(){
        super.setPointVie(this.getpointVie()-this.valueBouclier);
    }

    @Override
    public String toString(){
        return "S";
    }
}