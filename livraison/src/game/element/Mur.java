package element;
import game.Coordonne;
import combatant.*;
import observer.*;

/**
 * Class représentant un mur 
 */
public class Mur extends Element{

    public Mur(int x,int y)
    {
        super(x,y);

    }

    @Override
    public String toString()
    {
        return "*";
    }

}


