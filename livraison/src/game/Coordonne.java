package game;

/**
 * Class permettant de représenter une coordonné d'abscisse x et d'ordonné y
 */
public class Coordonne {

    private int x;
    private int y;

   /**
    * 
    * @param x abscisse 
    * @param y ordonné
    */
    public Coordonne(int x,int y){
        this.x=x;
        this.y=y;
    }
    /** 
     * Retourne l'abscisse x de la coordonnée
     * @return abscisse x de la coordonné 
     */
    public int getx(){
        return this.x;
    }
    /**
     * Retourne l'ordonné y de la coordonné
     * @return ordonné y de la coordonné
     */
    public int gety(){
        return this.y;
    }
    /**
     * Modifie l'abscisse x 
     * @param x nouvel valeur de x
     */
    public void setx(int x){
        this.x=x;
    }
     /**
     * Modifie l'ordonné y
     * @param y nouvel valeur de y
     */
    public void sety(int y){
        this.y=y;
    }
    /**
     * Redefinition du equals par rapport au valeur x et y de la coordonné
     * @param c objet à tester
     */
    @Override
    public boolean equals(Object c) 
    {
        if(this==c)
        {
            return true;
        }

        else if(c instanceof Coordonne)
            {
                Coordonne tmp = (Coordonne)c;
                if(tmp.getx()==this.getx() && tmp.gety()==this.gety())
                {
                    return true;
                }
            }
        
        return false;
    }
    //Redefinition du hash pour manipuler les map 
    @Override
    public int hashCode()
    {
       
        return toString().hashCode();
    }
    public String toString(){
        return "("+x+","+y+")";
    }
}