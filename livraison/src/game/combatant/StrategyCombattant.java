package combatant;
import java.util.*;
import element.*;
import game.Coordonne;

/**
 * Class permetant de définir une stratégie de jeu pour un combattant
 */
public interface StrategyCombattant {
    /**
     * Permet d'effectuer une action sur le plateau de jeu pour le combattant
     * @param combattant le combattant effectuant une action
     * @param mapCombattant représente les combattants sur le plateau de jeu
     * @param mapElement représente les éléments sur le plateau de jeu
     * @return retourn l'action effectué par le combattant
     */
    public String strategyCombat(Combattant combattant,Map<Coordonne,Combattant>mapCombattant,Map<Coordonne,Element>mapElement);
}