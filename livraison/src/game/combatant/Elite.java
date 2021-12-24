package combatant;
import element.*;
import game.Coordonne;
import constante.Constante;
import arme.*;
/**
 * Class représentant un combattant Elite
 */
public class Elite extends Personnage {
    /**
     * cout d'un déplacement 
     */
    private final int valueDeplacement=2;
    /**
     * cout de l'activation du bouclier
     */
    private final int valueBouclier=10;

    public Elite(int x,int y)
    {
        super(x,y);
        this.nom=Constante.eliteName;
        this.arme=new Sniper();

    }

    protected void pointVieDeplacement(){
        super.setPointVie(this.getpointVie()-this.valueDeplacement);
    }
    protected void pointVieBouclier(){
        super.setPointVie(this.getpointVie()-this.valueBouclier);
    }

    @Override
    public String toString(){
        return "E";
    }
  
}