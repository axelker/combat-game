package grille;
import constante.*;
import combatant.*;
import element.*;
import game.Coordonne;
import java.util.*;

public interface StrategyPlateau {

    public void StrategyRemplissagePlateau(Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement,FactoryCombattant factory);
}