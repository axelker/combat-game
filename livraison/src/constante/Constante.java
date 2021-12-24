package constante;
import java.util.*;
import combatant.*;
import grille.*;

/**
 * Classe permettant la définition des constantes pour l'application
 */
public interface Constante {

//////////////Combattant////////////////////////////////////
/**
 * Strategy du combattant
 */
public static final StrategyCombattant strategycombattant = new StrategyAleatoire();

/**
 * Strategy remplissage du plateau
 */
public static final StrategyPlateau strategyplateau = new StrategyPlateauAlea();

/**
 * Point vie combattant
 */
public static final int pointViePersonnage=100;
/**
 * Nom combattant tank
 */
public static final String tankName="Tank";
/**
 * Nom combattant soldat
 */
public static final String soldatName="Soldat";
/**
 * Nom combattant elite
 */
public static final String eliteName="Elite";
/**
 * Nom combattant Random
 */
public static final String RandomName="RandomComnattant";
/**
 * Liste des noms de combattant
 */
public static final List<String> listeName = new ArrayList<>(List.of(tankName,soldatName,eliteName,RandomName)); 
public final static String gauche="gauche";
public final static String droite ="droite";
public final static String haut="haut";
public final static String bas="bas";
/**
 * Liste des directions sous forme de string
 */
public static final List<String> listeDirection = new ArrayList<>(List.of(gauche,droite,bas,haut));  
////////////////////////////////////////////////////////////

//////////////////////////Plateau//////////////////////////
/**
 * Nombre ligne du plateau
 */
public static final int nbLigne = 10;
/**
 * Nombre colonne du plateau
 */
public static final int nbColonne = 10;
/**
 * Nombre de personnage sur le plateau
 */
public static final int nbPersonnage=2;

///////////////////////////////////////////////////////////


///////////////////////Pastille,Bombe,Mine////////////////////////////////
/**
 * Valeur de la pastille 
 */
public static final int valuePastille=20;
/**
 * Valeur dégat bombe
 */
public static final int degatBombe=15;
/**
 * Valeur dégat mine
 */
public static final int degatMine=40;

////////////////////////////////////////////////////////////////////////
//////////   Constante toString() plateau ///////////////////////////////////
public static final List<String>explosiftoString = new ArrayList<String>(List.of("B","M"));
public static final List<String>persotoString = new ArrayList<String>(List.of("T","R","S","E"));
public static final List<String>elementtoString = new ArrayList<String>(List.of("*","p"));



}