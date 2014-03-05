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
public class Barra extends Base {
    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto tortuga.
     * @param posY es el <code>posiscion en y</code> del objeto tortuga.
     *
     */
    public Barra(int posX, int posY) {
        super(posX, posY);
        this.velX = 0;
        this.velY = 0;
    }
}
