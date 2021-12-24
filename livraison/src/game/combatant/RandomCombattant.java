package combatant;
import element.*;
import game.Coordonne;
import java.util.Random;
import arme.*;
import constante.Constante;

/**
 * Class représentant un combattant avec des caractéritiques aléatoire.
 */


public class RandomCombattant extends Personnage {
    private  Random rand = new Random();
    /**
     * cout d'un déplacement
     */
    private final int valueDeplacement=rand.nextInt(9)+1;
    /**
     * cout de l'activation du bouclier
     */
    private final int valueBouclier=rand.nextInt(9)+1;

    public RandomCombattant(int x,int y)
    {
        super(x,y);
        this.nom=Constante.RandomName;
        this.arme=new ArmeRandom();
        setPointVie(rand.nextInt(100)+1);

    }
  
    protected void pointVieDeplacement(){
        super.setPointVie(this.getpointVie()-this.valueDeplacement);
    }
    protected void pointVieBouclier(){
        super.setPointVie(this.getpointVie()-this.valueBouclier);
    }

    @Override
    public String toString(){
        return "R";
    }
}