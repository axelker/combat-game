package element;
import observer.*;
import combatant.*;
import game.Coordonne;
import constante.Constante;
import java.util.*;
/**
 * Class representant une pastille d'énergie permettant d'augmenter les points de vie d'un combattant
 */
public class Pastille extends Element{
    /**
     * Valeur de la pastille 
     */
    private final int value =Constante.valuePastille;

    public Pastille(int x,int y)
    {
        super(x,y);
    }

   /**
    * @return retourn la valeur de la pastille
    */
    public int getValue()
    {
        return this.value;
    }

    @Override
    public boolean effet(Map<Coordonne,Combattant>listP)
    {
        boolean modif=false;
          //Combattant sur la bombe
          if(listP.containsKey(super.c)){
            Combattant p = listP.get(super.c);
            p.setPointVie(p.getpointVie()+this.value);
            modif=true;
            this.fireChangement();

        }
        return modif;
    }

    @Override
    public String toString()
    {
        return "p";
    }

}


