package element;
import game.Coordonne;
import combatant.*;
import observer.*;

/**
 * Class Représentant un élément vide sur un plateau de jeu
 */
public class Vide extends Element{

    public Vide(int x,int y)
    {
        super(x,y);
    }

    @Override
    public String toString()
    {
        return " ";
    }

}


