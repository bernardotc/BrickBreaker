/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package breakingbadbreaker;

/**
 *
 * @author bernardot
 */
public class Bala extends Base {
    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto tortuga.
     * @param posY es el <code>posiscion en y</code> del objeto tortuga.
     *
     */
    public Bala(int posX, int posY) {
        super(posX, posY);
        this.velX = 1;
        this.velY = 1;
    }
}
