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
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Bloque extends Base {
    
    private ImageIcon b1;
    private ImageIcon b2;
    private ImageIcon b3;
    private ImageIcon b4;
    private ImageIcon b5;
    private ImageIcon b6;
    private ImageIcon b7;
    private int contador;
    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto tortuga.
     * @param posY es el <code>posiscion en y</code> del objeto tortuga.
     *
     */
    public Bloque(int posX, int posY) {
        super(posX, posY);
        this.velX = 0;
        this.velY = 0;
        Image imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/1.png"));
        ImageIcon image = new ImageIcon(imagen);
        this.setImageIcon(image);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/2.png"));
        b1 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/3.png"));
        b2 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/4.png"));
        b3 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/5.png"));
        b4 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/6.png"));
        b5 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/7.png"));
        b6 = new ImageIcon(imagen);
        imagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/8.png"));
        b7 = new ImageIcon(imagen);
        contador = (int) ((Math.random() * 8));
    }

    @Override
    public Image getImagenI() {
        if (contador == 0) {
            return this.getImageIcon().getImage();
        } else if (contador == 1) {
            return b1.getImage();
        } else if (contador == 2) {
            return b2.getImage();
        } else if (contador == 3) {
            return b3.getImage();
        } else if (contador == 4) {
            return b4.getImage();
        } else if (contador == 5) {
            return b5.getImage();
        } else if (contador == 6) {
            return b6.getImage();
        } else if (contador == 7) {
            return b7.getImage();
        } else {
            return b1.getImage();
        }
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
    
    
}
