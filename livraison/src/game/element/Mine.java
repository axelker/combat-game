package element;
import game.Coordonne;
import combatant.*;
import observer.*;
import constante.Constante;
import java.util.*;

/**
 * Class permetant de représenter une mine sur un plateau de jeu  
 */

public class Mine extends Element {

    private final int degat=Constante.degatMine;

    public Mine(int x,int y)
    {        
        super(x,y);
    }

    public int getdegat()
    {
        return this.degat;
    }

    @Override
    public boolean effet(Map<Coordonne,Combattant>listP)
    {
        boolean modif=false;
          //Combattant sur la bombe
          if(listP.containsKey(this.c)){
            Combattant p = listP.get(this.c);
            p.setPointVie(p.getpointVie()-this.degat);
            modif=true;
            this.fireChangement();

        }
        return modif;
    }

    @Override
    public String toString()
    {
        return "M";
    }

}