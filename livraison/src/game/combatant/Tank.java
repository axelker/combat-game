package combatant;
import arme.*;
import element.*;
import game.Coordonne;
import constante.Constante;

/**
 * Classe représentant un combattant tank
 */

public class Tank extends Personnage {
    /**
     * cout d'un déplacement 
     */
    private final int valueDeplacement=10;
    /**
     * cout de l'activation du bouclier
     */
    private final int valueBouclier=2;

    public Tank(int x,int y)
    {
        super(x,y);
        this.nom=Constante.tankName;
        this.arme=new ArmeLourde();

    }
    protected void pointVieDeplacement(){
        super.setPointVie(this.getpointVie()-this.valueDeplacement);
    }
    protected void pointVieBouclier(){
        super.setPointVie(this.getpointVie()-this.valueBouclier);
    }

    @Override
    public String toString(){
        return "T";
    }
}