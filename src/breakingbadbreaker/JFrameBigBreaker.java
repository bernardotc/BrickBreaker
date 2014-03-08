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
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.plaf.FontUIResource;

public class JFrameBigBreaker extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    //private Caparazon caparazon; //objeto del caparazon
    //private Tortuga tortuga; //objeto de la tortuga
    private int score; // la puntacion del juego
    private int vidas;  // vidas
    private int contadorVidas;  // numero de veces antes de perder vida
    private float factorGravedad; // aumentara la gravedad
    private float factorAumento;    // ayudara a aumentar la velocidad en x
    private float factorAumentoY;   // ayudara a aumentar la velocidad en y
    private Image dbImage;    // Imagen a proyectar
    private Image background;   // imagen que sera background
    private Image background2;
    private Image background3;
    private Image background4;
    private Image background5;
    private Image inicio;
    private Image vida;
    private Image over;
    private Image ganar;
    private Graphics dbg;   // Objeto grafico
    private String instr1; // String que contiene las instrucciones del juego.
    private String instr2; // String que contiene las instrucciones del juego.
    private String instr3;  // String que contiene las instrucciones del juego.
    private String instr4;  // String que contiene las instrucciones del juego
    private SoundClip shell;    // Objeto Soundclip triste
    private SoundClip catched;  // Objeto SoundClip alegre
    private String nombreArchivo;   // Nombre del archivo
    private String[] arr;   // Arreglo del archivo dividido
    private SoundClip theme;
    private SoundClip theme2;
    private SoundClip breaking;
    private SoundClip lose;
    private SoundClip jump;
    private SoundClip extra;
    private int themeCont;
    private int changeSong;

    // banderas
    private boolean empezar;
    // de movimiento del caparazon
    private boolean click;
    private boolean volando;
    //de movimiento de la tortuga
    private boolean derecha;
    //generales
    private boolean izquierda;
    private boolean gameOver;   // saber si se acabaron las vidas
    private boolean pausado;    // Valor booleano para saber si el JFrame esta en pausa
    private boolean instrucciones;  // Valor booleano para mostrar/quitar instrucciones
    private boolean cargar; // Valor booleano para cargar el juego
    private boolean guardar;    // Valor booleano para guardar el juego
    private boolean sonido; // Valor booleano para controlar el sonido
    private boolean colision;
    private boolean reset;
    private boolean start;
    private boolean gane;

    // Imagenes adicionales para ambientizar el juego
    private Image im1;
    private Image im2;
    private Image block;
    private Image plant;

    // Variables auxilares para leer archivo y actualizar
    private int cPosX;  // caparazon PosX
    private int cPosY;  // caparazon PosY
    private float cVelX;    // caparazon VelX
    private float cVelY;    // caparazon VelY
    private boolean c;  // click
    private boolean v;  // volando
    private int tPosX;  // tortuga PosX
    private int tPosY; // tortuga PosY
    private float tVelX;    // tortugaVelX
    private int scr;    // score
    private int vid; // vidas
    private int contVid;    // contadordeVidas
    private float fG;   // factorGravedad
    private float fA;   // factorAumento
    private float fAY;  // factorAumentoY
    private boolean son;    // Sonido
    private boolean lvl1;
    private boolean lvl5;
    private LinkedList<Bloque> lista;
    private int blockSize;
    private int level;
    private Barra barra;
    private Bala bola;
    private int rebote;
    private int blockHit;

    private Base rv1;
    private Base rv2;

    private Bala upgrade1;
    private Bala upgrade2;
    private Bala upgrade3;
    private Bala extraVida;
    private boolean up1;
    private boolean up2;
    private boolean up3;
    private boolean extraLife;

    /**
     * Metodo constructor del JFrame donde se inicializan las variables y se
     * empieza el thread de <code>JFrame</code>.
     */
    public JFrameBigBreaker() {
        //Se inicializan variables
        lvl1 = false;
        lvl5 = false;
        start = false;
        empezar = false;
        rebote = 3;
        sonido = true;
        pausado = false;
        instrucciones = true;
        gane = false;

        //se cargan los sonicods
        theme = new SoundClip("sounds/theme.wav");
        theme2 = new SoundClip("sounds/theme2.wav");
        breaking = new SoundClip("sounds/brickbreak.wav");
        lose = new SoundClip("sounds/stomp.wav");
        jump = new SoundClip("sounds/jump.wav");
        extra = new SoundClip("sounds/marioSound.wav");
        themeCont = 0;
        changeSong = 0;

        //se cargan las imagenes
        URL bURL = this.getClass().getResource("images/back1.jpg");
        background = Toolkit.getDefaultToolkit().getImage(bURL);
        URL b2URL = this.getClass().getResource("images/back4.jpg");
        background2 = Toolkit.getDefaultToolkit().getImage(b2URL);
        URL b3URL = this.getClass().getResource("images/back3.jpg");
        background3 = Toolkit.getDefaultToolkit().getImage(b3URL);
        URL b4URL = this.getClass().getResource("images/back7.jpg");
        background4 = Toolkit.getDefaultToolkit().getImage(b4URL);
        URL b5URL = this.getClass().getResource("images/back6.png");
        background5 = Toolkit.getDefaultToolkit().getImage(b5URL);
        URL instrURL = this.getClass().getResource("images/back2.jpg");
        inicio = Toolkit.getDefaultToolkit().getImage(instrURL);
        URL vidaURL = this.getClass().getResource("images/vida.png");
        vida = Toolkit.getDefaultToolkit().getImage(vidaURL);
        URL overURL = this.getClass().getResource("images/back5.jpg");
        over = Toolkit.getDefaultToolkit().getImage(overURL);
        URL ganarURL = this.getClass().getResource("images/back9.jpg");
        ganar = Toolkit.getDefaultToolkit().getImage(ganarURL);

        setSize(1200, 800);
        setBackground(Color.white);

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        init();
        start();
    }

    public void init() {
        //Se inicializan variables
        // Se cargan las instrucciones
        instr1 = "El juego consiste en intentar atrapar el caparazón con la tortuga. Al momento de darle clic al caparazón, este se "; // Instrucciones del juego
        instr2 = "moverá a través de la pantalla. Con las teclas izquierda y derecha, podrás mover a la tortuga. Si no llegas ";
        instr3 = "atrapar el caparazón, el caparazón caerá más rápido... ¡CUIDADO! ¡TU PUEDES!";
        instr4 = "Teclas: P = pausa. I = instrucciones. S = sonido. C = Cargar. G = guardar";

        up1 = false;
        up2 = false;
        up3 = false;
        extraLife = false;

        // Se cargan los sonidos
        //shell = new SoundClip("sounds/stomp.wav");
        //catched = new SoundClip("sounds/marioSound.wav");
        //se genera el nivel
        level = 1;
        lista = new LinkedList();
        blockSize = 50;
        blockHit = 50;
        Bloque aux1 = new Bloque(80, 100, 0);
        Bloque aux2 = new Bloque(152, 100, 0);
        Bloque aux3 = new Bloque(224, 100, 0);
        Bloque aux4 = new Bloque(296, 100, 0);
        Bloque aux5 = new Bloque(368, 100, 0);
        Bloque aux6 = new Bloque(440, 100, 0);
        Bloque aux7 = new Bloque(512, 100, 0);
        Bloque aux8 = new Bloque(584, 100, 0);
        Bloque aux9 = new Bloque(656, 100, 0);
        Bloque aux10 = new Bloque(728, 100, 0);
        Bloque aux11 = new Bloque(800, 100, 0);
        Bloque aux12 = new Bloque(872, 100, 0);
        Bloque aux13 = new Bloque(944, 100, 0);
        Bloque aux14 = new Bloque(1016, 100, 0);
        Bloque aux15 = new Bloque(152, 172, 0);
        Bloque aux16 = new Bloque(224, 172, 0);
        Bloque aux17 = new Bloque(296, 172, 0);
        Bloque aux18 = new Bloque(368, 172, 0);
        Bloque aux19 = new Bloque(440, 172, 0);
        Bloque aux20 = new Bloque(512, 172, 0);
        Bloque aux21 = new Bloque(584, 172, 0);
        Bloque aux22 = new Bloque(656, 172, 0);
        Bloque aux23 = new Bloque(728, 172, 0);
        Bloque aux24 = new Bloque(800, 172, 0);
        Bloque aux25 = new Bloque(872, 172, 0);
        Bloque aux26 = new Bloque(944, 172, 0);
        Bloque aux27 = new Bloque(224, 244, 0);
        Bloque aux28 = new Bloque(296, 244, 0);
        Bloque aux29 = new Bloque(368, 244, 0);
        Bloque aux30 = new Bloque(440, 244, 0);
        Bloque aux31 = new Bloque(512, 244, 0);
        Bloque aux32 = new Bloque(584, 244, 0);
        Bloque aux33 = new Bloque(656, 244, 0);
        Bloque aux34 = new Bloque(728, 244, 0);
        Bloque aux35 = new Bloque(800, 244, 0);
        Bloque aux36 = new Bloque(872, 244, 0);
        Bloque aux37 = new Bloque(296, 316, 0);
        Bloque aux38 = new Bloque(368, 316, 0);
        Bloque aux39 = new Bloque(440, 316, 0);
        Bloque aux40 = new Bloque(512, 316, 0);
        Bloque aux41 = new Bloque(584, 316, 0);
        Bloque aux42 = new Bloque(656, 316, 0);
        Bloque aux43 = new Bloque(728, 316, 0);
        Bloque aux44 = new Bloque(368, 388, 0);
        Bloque aux45 = new Bloque(440, 388, 0);
        Bloque aux46 = new Bloque(512, 388, 0);
        Bloque aux47 = new Bloque(584, 388, 0);
        Bloque aux48 = new Bloque(656, 388, 0);
        Bloque aux49 = new Bloque(800, 316, 0);
        Bloque aux50 = new Bloque(728, 388, 0);
        lista.add(0, aux1);
        lista.add(1, aux2);
        lista.add(2, aux3);
        lista.add(3, aux4);
        lista.add(4, aux5);
        lista.add(5, aux6);
        lista.add(6, aux7);
        lista.add(7, aux8);
        lista.add(8, aux9);
        lista.add(9, aux10);
        lista.add(10, aux11);
        lista.add(11, aux12);
        lista.add(12, aux13);
        lista.add(13, aux14);
        lista.add(14, aux15);
        lista.add(15, aux16);
        lista.add(16, aux17);
        lista.add(17, aux18);
        lista.add(18, aux19);
        lista.add(19, aux20);
        lista.add(20, aux21);
        lista.add(21, aux22);
        lista.add(22, aux23);
        lista.add(23, aux24);
        lista.add(24, aux25);
        lista.add(25, aux26);
        lista.add(26, aux27);
        lista.add(27, aux28);
        lista.add(28, aux29);
        lista.add(29, aux30);
        lista.add(30, aux31);
        lista.add(31, aux32);
        lista.add(32, aux33);
        lista.add(33, aux34);
        lista.add(34, aux35);
        lista.add(35, aux36);
        lista.add(36, aux37);
        lista.add(37, aux38);
        lista.add(38, aux39);
        lista.add(39, aux40);
        lista.add(40, aux41);
        lista.add(41, aux42);
        lista.add(42, aux43);
        lista.add(43, aux44);
        lista.add(44, aux45);
        lista.add(45, aux46);
        lista.add(46, aux47);
        lista.add(47, aux48);
        lista.add(48, aux49);
        lista.add(49, aux50);

        nombreArchivo = "Datos.txt";
        cargar = false;
        guardar = false;

        // Se inicializan valores del juego
        score = 0;
        if (lvl1) {
            vidas = 1;
        } else {
            vidas = 5;
        }
        gameOver = false;
        factorGravedad = (float) .5;
        factorAumento = 1;
        factorAumentoY = 1;
        // Ningun objeto se mueve
        derecha = false;
        izquierda = false;
        click = false;
        volando = false;

        rv1 = new Base(200, 720);
        rv2 = new Base(880, 720);
        URL rvURL = this.getClass().getResource("images/rv1.jpg");
        ImageIcon carro = new ImageIcon(Toolkit.getDefaultToolkit().getImage(rvURL));
        URL rvvURL = this.getClass().getResource("images/rv2.png");
        ImageIcon carroo = new ImageIcon(Toolkit.getDefaultToolkit().getImage(rvvURL));
        rv1.setImageIcon(carro);
        rv2.setImageIcon(carroo);

        //objeto de la barra
        barra = new Barra(getWidth() / 2, getHeight());
        URL barraURL = this.getClass().getResource("images/barra.png");
        ImageIcon aux;
        aux = new ImageIcon(Toolkit.getDefaultToolkit().getImage(barraURL));
        barra.setImageIcon(aux);
        barra.setPosY(barra.getPosY() - barra.getAlto());
        barra.setPosX(barra.getPosX() - barra.getAncho() / 2);

        //objeto de la bala
        bola = new Bala(getWidth() / 2, getHeight() - 2 * barra.getAlto() - 1);
        URL bolaURL = this.getClass().getResource("images/bola.png");
        aux = new ImageIcon(Toolkit.getDefaultToolkit().getImage(bolaURL));
        bola.setImageIcon(aux);
        bola.setPosX(bola.getPosX() - bola.getAncho() / 2);

        //los upgrades y vidas extras
        upgrade1 = new Bala(getWidth() / 2, getHeight() - 2 * barra.getAlto() - 1);
        upgrade1.setVelY((int) ((Math.random() * 2)) + 1);
        URL up1URL = this.getClass().getResource("images/upgrade1.png");
        aux = new ImageIcon(Toolkit.getDefaultToolkit().getImage(up1URL));
        upgrade1.setImageIcon(aux);

        upgrade2 = new Bala(getWidth() / 2, getHeight() - 2 * barra.getAlto() - 1);
        upgrade1.setVelY((int) ((Math.random() * 2)) + 1);
        URL up2URL = this.getClass().getResource("images/upgrade2.png");
        aux = new ImageIcon(Toolkit.getDefaultToolkit().getImage(up2URL));
        upgrade2.setImageIcon(aux);

        upgrade3 = new Bala(getWidth() / 2, getHeight() - 2 * barra.getAlto() - 1);
        upgrade1.setVelY((int) ((Math.random() * 2)) + 1);
        URL up3URL = this.getClass().getResource("images/upgrade3.png");
        aux = new ImageIcon(Toolkit.getDefaultToolkit().getImage(up3URL));
        upgrade3.setImageIcon(aux);

        extraVida = new Bala(getWidth() / 2, getHeight() - 2 * barra.getAlto() - 1);
        upgrade1.setVelY((int) ((Math.random() * 2)) + 1);
        aux = new ImageIcon(vida);
        extraVida.setImageIcon(aux);
    }

    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    public void level2() {
        blockSize = blockHit = 56;
        Bloque aux1 = new Bloque(80, 100, 1);
        Bloque aux2 = new Bloque(152, 100, 1);
        Bloque aux3 = new Bloque(224, 100, 1);
        Bloque aux4 = new Bloque(296, 100, 1);
        Bloque aux5 = new Bloque(368, 100, 1);
        Bloque aux6 = new Bloque(440, 100, 1);
        Bloque aux7 = new Bloque(512, 100, 1);
        Bloque aux8 = new Bloque(584, 100, 1);
        Bloque aux9 = new Bloque(656, 100, 1);
        Bloque aux10 = new Bloque(728, 100, 1);
        Bloque aux11 = new Bloque(800, 100, 1);
        Bloque aux12 = new Bloque(872, 100, 1);
        Bloque aux13 = new Bloque(944, 100, 1);
        Bloque aux14 = new Bloque(1016, 100, 1);
        Bloque aux15 = new Bloque(80, 172, 1);
        Bloque aux16 = new Bloque(152, 172, 0);
        Bloque aux17 = new Bloque(224, 172, 0);
        Bloque aux18 = new Bloque(296, 172, 0);
        Bloque aux19 = new Bloque(368, 172, 0);
        Bloque aux20 = new Bloque(440, 172, 0);
        Bloque aux21 = new Bloque(512, 172, 0);
        Bloque aux22 = new Bloque(584, 172, 0);
        Bloque aux23 = new Bloque(656, 172, 0);
        Bloque aux24 = new Bloque(728, 172, 0);
        Bloque aux25 = new Bloque(800, 172, 0);
        Bloque aux26 = new Bloque(872, 172, 0);
        Bloque aux27 = new Bloque(944, 172, 0);
        Bloque aux28 = new Bloque(1016, 172, 1);
        Bloque aux29 = new Bloque(80, 244, 1);
        Bloque aux30 = new Bloque(152, 244, 0);
        Bloque aux31 = new Bloque(224, 244, 0);
        Bloque aux32 = new Bloque(296, 244, 0);
        Bloque aux33 = new Bloque(368, 244, 0);
        Bloque aux34 = new Bloque(440, 244, 0);
        Bloque aux35 = new Bloque(512, 244, 0);
        Bloque aux36 = new Bloque(584, 244, 0);
        Bloque aux37 = new Bloque(656, 244, 0);
        Bloque aux38 = new Bloque(728, 244, 0);
        Bloque aux39 = new Bloque(800, 244, 0);
        Bloque aux40 = new Bloque(872, 244, 0);
        Bloque aux41 = new Bloque(944, 244, 0);
        Bloque aux42 = new Bloque(1016, 244, 1);
        Bloque aux43 = new Bloque(80, 316, 1);
        Bloque aux44 = new Bloque(152, 316, 2);
        Bloque aux45 = new Bloque(224, 316, 2);
        Bloque aux46 = new Bloque(296, 316, 2);
        Bloque aux47 = new Bloque(368, 316, 2);
        Bloque aux48 = new Bloque(440, 316, 2);
        Bloque aux49 = new Bloque(512, 316, 2);
        Bloque aux50 = new Bloque(584, 316, 2);
        Bloque aux51 = new Bloque(656, 316, 2);
        Bloque aux52 = new Bloque(728, 316, 2);
        Bloque aux53 = new Bloque(800, 316, 2);
        Bloque aux54 = new Bloque(872, 316, 2);
        Bloque aux55 = new Bloque(944, 316, 2);
        Bloque aux56 = new Bloque(1016, 316, 1);
        lista.add(0, aux1);
        lista.add(1, aux2);
        lista.add(2, aux3);
        lista.add(3, aux4);
        lista.add(4, aux5);
        lista.add(5, aux6);
        lista.add(6, aux7);
        lista.add(7, aux8);
        lista.add(8, aux9);
        lista.add(9, aux10);
        lista.add(10, aux11);
        lista.add(11, aux12);
        lista.add(12, aux13);
        lista.add(13, aux14);
        lista.add(14, aux15);
        lista.add(15, aux16);
        lista.add(16, aux17);
        lista.add(17, aux18);
        lista.add(18, aux19);
        lista.add(19, aux20);
        lista.add(20, aux21);
        lista.add(21, aux22);
        lista.add(22, aux23);
        lista.add(23, aux24);
        lista.add(24, aux25);
        lista.add(25, aux26);
        lista.add(26, aux27);
        lista.add(27, aux28);
        lista.add(28, aux29);
        lista.add(29, aux30);
        lista.add(30, aux31);
        lista.add(31, aux32);
        lista.add(32, aux33);
        lista.add(33, aux34);
        lista.add(34, aux35);
        lista.add(35, aux36);
        lista.add(36, aux37);
        lista.add(37, aux38);
        lista.add(38, aux39);
        lista.add(39, aux40);
        lista.add(40, aux41);
        lista.add(41, aux42);
        lista.add(42, aux43);
        lista.add(43, aux44);
        lista.add(44, aux45);
        lista.add(45, aux46);
        lista.add(46, aux47);
        lista.add(47, aux48);
        lista.add(48, aux49);
        lista.add(49, aux50);
        lista.add(50, aux51);
        lista.add(51, aux52);
        lista.add(52, aux53);
        lista.add(53, aux54);
        lista.add(54, aux55);
        lista.add(55, aux56);
    }

    public void level3() {
        blockSize = blockHit = 58;
        Bloque aux1 = new Bloque(0, 100, 3);
        Bloque aux2 = new Bloque(72, 100, 3);
        Bloque aux3 = new Bloque(144, 100, 3);
        Bloque aux4 = new Bloque(216, 100, 3);
        Bloque aux5 = new Bloque(288, 100, 3);
        Bloque aux6 = new Bloque(360, 100, 3);
        Bloque aux7 = new Bloque(432, 100, 3);
        Bloque aux8 = new Bloque(696, 100, 3);
        Bloque aux9 = new Bloque(768, 100, 3);
        Bloque aux10 = new Bloque(840, 100, 3);
        Bloque aux11 = new Bloque(912, 100, 3);
        Bloque aux12 = new Bloque(984, 100, 3);
        Bloque aux13 = new Bloque(1056, 100, 3);
        Bloque aux14 = new Bloque(1128, 100, 3);
        Bloque aux15 = new Bloque(80, 252, 2);
        Bloque aux16 = new Bloque(152, 252, 2);
        Bloque aux17 = new Bloque(224, 252, 2);
        Bloque aux18 = new Bloque(296, 252, 2);
        Bloque aux19 = new Bloque(368, 252, 2);
        Bloque aux20 = new Bloque(440, 252, 2);
        Bloque aux21 = new Bloque(512, 252, 2);
        Bloque aux22 = new Bloque(584, 252, 2);
        Bloque aux23 = new Bloque(656, 252, 2);
        Bloque aux24 = new Bloque(728, 252, 2);
        Bloque aux25 = new Bloque(800, 252, 2);
        Bloque aux26 = new Bloque(872, 252, 2);
        Bloque aux27 = new Bloque(944, 252, 2);
        Bloque aux28 = new Bloque(1016, 252, 2);
        Bloque aux29 = new Bloque(0, 404, 1);
        Bloque aux30 = new Bloque(72, 404, 1);
        Bloque aux31 = new Bloque(144, 404, 1);
        Bloque aux32 = new Bloque(216, 404, 1);
        Bloque aux33 = new Bloque(288, 404, 1);
        Bloque aux34 = new Bloque(360, 404, 1);
        Bloque aux35 = new Bloque(432, 404, 1);
        Bloque aux36 = new Bloque(696, 404, 1);
        Bloque aux37 = new Bloque(768, 404, 1);
        Bloque aux38 = new Bloque(840, 404, 1);
        Bloque aux39 = new Bloque(912, 404, 1);
        Bloque aux40 = new Bloque(984, 404, 1);
        Bloque aux41 = new Bloque(1056, 404, 1);
        Bloque aux42 = new Bloque(1128, 404, 1);
        Bloque aux43 = new Bloque(0, 580, 4);
        Bloque aux44 = new Bloque(72, 580, 4);
        Bloque aux45 = new Bloque(144, 580, 4);
        Bloque aux46 = new Bloque(216, 580, 4);
        Bloque aux47 = new Bloque(288, 580, 4);
        Bloque aux48 = new Bloque(360, 580, 4);
        Bloque aux49 = new Bloque(432, 580, 4);
        Bloque aux50 = new Bloque(504, 580, 4);
        Bloque aux51 = new Bloque(576, 580, 4);
        Bloque aux52 = new Bloque(648, 580, 4);
        Bloque aux53 = new Bloque(720, 580, 4);
        Bloque aux54 = new Bloque(792, 580, 4);
        Bloque aux55 = new Bloque(864, 580, 4);
        Bloque aux56 = new Bloque(936, 580, 4);
        Bloque aux57 = new Bloque(1008, 580, 4);
        Bloque aux58 = new Bloque(1080, 580, 4);
        lista.add(0, aux1);
        lista.add(1, aux2);
        lista.add(2, aux3);
        lista.add(3, aux4);
        lista.add(4, aux5);
        lista.add(5, aux6);
        lista.add(6, aux7);
        lista.add(7, aux8);
        lista.add(8, aux9);
        lista.add(9, aux10);
        lista.add(10, aux11);
        lista.add(11, aux12);
        lista.add(12, aux13);
        lista.add(13, aux14);
        lista.add(14, aux15);
        lista.add(15, aux16);
        lista.add(16, aux17);
        lista.add(17, aux18);
        lista.add(18, aux19);
        lista.add(19, aux20);
        lista.add(20, aux21);
        lista.add(21, aux22);
        lista.add(22, aux23);
        lista.add(23, aux24);
        lista.add(24, aux25);
        lista.add(25, aux26);
        lista.add(26, aux27);
        lista.add(27, aux28);
        lista.add(28, aux29);
        lista.add(29, aux30);
        lista.add(30, aux31);
        lista.add(31, aux32);
        lista.add(32, aux33);
        lista.add(33, aux34);
        lista.add(34, aux35);
        lista.add(35, aux36);
        lista.add(36, aux37);
        lista.add(37, aux38);
        lista.add(38, aux39);
        lista.add(39, aux40);
        lista.add(40, aux41);
        lista.add(41, aux42);
        lista.add(42, aux43);
        lista.add(43, aux44);
        lista.add(44, aux45);
        lista.add(45, aux46);
        lista.add(46, aux47);
        lista.add(47, aux48);
        lista.add(48, aux49);
        lista.add(49, aux50);
        lista.add(50, aux51);
        lista.add(51, aux52);
        lista.add(52, aux53);
        lista.add(53, aux54);
        lista.add(54, aux55);
        lista.add(55, aux56);
        lista.add(56, aux57);
        lista.add(57, aux58);
    }

    public void level4() {
        blockSize = blockHit = 66;
        Bloque aux1 = new Bloque(0, 80, 5);
        Bloque aux2 = new Bloque(72, 80, 5);
        Bloque aux3 = new Bloque(144, 80, 5);
        Bloque aux4 = new Bloque(216, 80, 5);
        Bloque aux5 = new Bloque(288, 80, 5);
        Bloque aux6 = new Bloque(360, 80, 5);
        Bloque aux7 = new Bloque(432, 80, 5);
        Bloque aux8 = new Bloque(696, 80, 5);
        Bloque aux9 = new Bloque(768, 80, 5);
        Bloque aux10 = new Bloque(840, 80, 5);
        Bloque aux11 = new Bloque(912, 80, 5);
        Bloque aux12 = new Bloque(984, 80, 5);
        Bloque aux13 = new Bloque(1056, 80, 5);
        Bloque aux14 = new Bloque(1128, 80, 5);
        Bloque aux15 = new Bloque(0, 152, 6);
        Bloque aux16 = new Bloque(72, 152, 6);
        Bloque aux17 = new Bloque(144, 152, 6);
        Bloque aux18 = new Bloque(216, 152, 6);
        Bloque aux19 = new Bloque(288, 152, 6);
        Bloque aux20 = new Bloque(360, 152, 6);
        Bloque aux21 = new Bloque(432, 152, 6);
        Bloque aux22 = new Bloque(696, 152, 6);
        Bloque aux23 = new Bloque(768, 152, 6);
        Bloque aux24 = new Bloque(840, 152, 6);
        Bloque aux25 = new Bloque(912, 152, 6);
        Bloque aux26 = new Bloque(984, 152, 6);
        Bloque aux27 = new Bloque(1056, 152, 6);
        Bloque aux28 = new Bloque(1128, 152, 6);
        Bloque aux29 = new Bloque(0, 224, 5);
        Bloque aux30 = new Bloque(72, 224, 5);
        Bloque aux31 = new Bloque(144, 224, 5);
        Bloque aux32 = new Bloque(216, 224, 5);
        Bloque aux33 = new Bloque(288, 224, 5);
        Bloque aux34 = new Bloque(360, 224, 5);
        Bloque aux35 = new Bloque(432, 224, 5);
        Bloque aux36 = new Bloque(696, 224, 5);
        Bloque aux37 = new Bloque(768, 224, 5);
        Bloque aux38 = new Bloque(840, 224, 5);
        Bloque aux39 = new Bloque(912, 224, 5);
        Bloque aux40 = new Bloque(984, 224, 5);
        Bloque aux41 = new Bloque(1056, 224, 5);
        Bloque aux42 = new Bloque(1128, 224, 5);
        Bloque aux43 = new Bloque(0, 296, 4);
        Bloque aux44 = new Bloque(72, 296, 4);
        Bloque aux45 = new Bloque(144, 296, 4);
        Bloque aux46 = new Bloque(216, 296, 4);
        Bloque aux47 = new Bloque(288, 296, 4);
        Bloque aux48 = new Bloque(360, 296, 4);
        Bloque aux49 = new Bloque(432, 296, 4);
        Bloque aux50 = new Bloque(696, 296, 4);
        Bloque aux51 = new Bloque(768, 296, 4);
        Bloque aux52 = new Bloque(840, 296, 4);
        Bloque aux53 = new Bloque(912, 296, 4);
        Bloque aux54 = new Bloque(984, 296, 4);
        Bloque aux55 = new Bloque(1056, 296, 4);
        Bloque aux56 = new Bloque(1128, 296, 4);
        Bloque aux57 = new Bloque(564, 20, 6);
        Bloque aux58 = new Bloque(564, 92, 6);
        Bloque aux59 = new Bloque(564, 164, 6);
        Bloque aux60 = new Bloque(564, 236, 6);
        Bloque aux61 = new Bloque(564, 308, 6);
        Bloque aux62 = new Bloque(564, 380, 6);
        Bloque aux63 = new Bloque(564, 452, 6);
        Bloque aux64 = new Bloque(564, 534, 6);
        Bloque aux65 = new Bloque(564, 596, 6);
        Bloque aux66 = new Bloque(564, 668, 6);
        lista.add(0, aux1);
        lista.add(1, aux2);
        lista.add(2, aux3);
        lista.add(3, aux4);
        lista.add(4, aux5);
        lista.add(5, aux6);
        lista.add(6, aux7);
        lista.add(7, aux8);
        lista.add(8, aux9);
        lista.add(9, aux10);
        lista.add(10, aux11);
        lista.add(11, aux12);
        lista.add(12, aux13);
        lista.add(13, aux14);
        lista.add(14, aux15);
        lista.add(15, aux16);
        lista.add(16, aux17);
        lista.add(17, aux18);
        lista.add(18, aux19);
        lista.add(19, aux20);
        lista.add(20, aux21);
        lista.add(21, aux22);
        lista.add(22, aux23);
        lista.add(23, aux24);
        lista.add(24, aux25);
        lista.add(25, aux26);
        lista.add(26, aux27);
        lista.add(27, aux28);
        lista.add(28, aux29);
        lista.add(29, aux30);
        lista.add(30, aux31);
        lista.add(31, aux32);
        lista.add(32, aux33);
        lista.add(33, aux34);
        lista.add(34, aux35);
        lista.add(35, aux36);
        lista.add(36, aux37);
        lista.add(37, aux38);
        lista.add(38, aux39);
        lista.add(39, aux40);
        lista.add(40, aux41);
        lista.add(41, aux42);
        lista.add(42, aux43);
        lista.add(43, aux44);
        lista.add(44, aux45);
        lista.add(45, aux46);
        lista.add(46, aux47);
        lista.add(47, aux48);
        lista.add(48, aux49);
        lista.add(49, aux50);
        lista.add(50, aux51);
        lista.add(51, aux52);
        lista.add(52, aux53);
        lista.add(53, aux54);
        lista.add(54, aux55);
        lista.add(55, aux56);
        lista.add(56, aux57);
        lista.add(57, aux58);
        lista.add(58, aux59);
        lista.add(59, aux60);
        lista.add(60, aux61);
        lista.add(61, aux62);
        lista.add(62, aux63);
        lista.add(63, aux64);
        lista.add(64, aux65);
        lista.add(65, aux66);
    }

    public void level5() {
        blockSize = blockHit = 84;
        Bloque aux1 = new Bloque(80, 80, 7);
        Bloque aux2 = new Bloque(152, 152, 7);
        Bloque aux3 = new Bloque(224, 80, 7);
        Bloque aux4 = new Bloque(296, 152, 7);
        Bloque aux5 = new Bloque(368, 80, 7);
        Bloque aux6 = new Bloque(440, 152, 7);
        Bloque aux7 = new Bloque(512, 80, 7);
        Bloque aux8 = new Bloque(584, 152, 7);
        Bloque aux9 = new Bloque(656, 80, 7);
        Bloque aux10 = new Bloque(728, 152, 7);
        Bloque aux11 = new Bloque(800, 80, 7);
        Bloque aux12 = new Bloque(872, 152, 7);
        Bloque aux13 = new Bloque(944, 80, 7);
        Bloque aux14 = new Bloque(1016, 152, 7);
        Bloque aux15 = new Bloque(80, 224, 4);
        Bloque aux16 = new Bloque(152, 224, 4);
        Bloque aux17 = new Bloque(224, 224, 4);
        Bloque aux18 = new Bloque(296, 224, 4);
        Bloque aux19 = new Bloque(368, 224, 4);
        Bloque aux20 = new Bloque(440, 224, 4);
        Bloque aux21 = new Bloque(512, 224, 4);
        Bloque aux22 = new Bloque(584, 224, 4);
        Bloque aux23 = new Bloque(656, 224, 4);
        Bloque aux24 = new Bloque(728, 224, 4);
        Bloque aux25 = new Bloque(800, 224, 4);
        Bloque aux26 = new Bloque(872, 224, 4);
        Bloque aux27 = new Bloque(944, 224, 4);
        Bloque aux28 = new Bloque(1016, 224, 4);
        Bloque aux29 = new Bloque(80, 296, 6);
        Bloque aux30 = new Bloque(152, 368, 6);
        Bloque aux31 = new Bloque(224, 296, 6);
        Bloque aux32 = new Bloque(296, 368, 6);
        Bloque aux33 = new Bloque(368, 296, 6);
        Bloque aux34 = new Bloque(440, 368, 6);
        Bloque aux35 = new Bloque(512, 296, 6);
        Bloque aux36 = new Bloque(584, 368, 6);
        Bloque aux37 = new Bloque(656, 296, 6);
        Bloque aux38 = new Bloque(728, 368, 6);
        Bloque aux39 = new Bloque(800, 296, 6);
        Bloque aux40 = new Bloque(872, 368, 6);
        Bloque aux41 = new Bloque(944, 296, 6);
        Bloque aux42 = new Bloque(1016, 368, 6);
        Bloque aux43 = new Bloque(80, 440, 3);
        Bloque aux44 = new Bloque(152, 440, 3);
        Bloque aux45 = new Bloque(224, 440, 3);
        Bloque aux46 = new Bloque(296, 440, 3);
        Bloque aux47 = new Bloque(368, 440, 3);
        Bloque aux48 = new Bloque(440, 440, 3);
        Bloque aux49 = new Bloque(512, 440, 3);
        Bloque aux50 = new Bloque(584, 440, 3);
        Bloque aux51 = new Bloque(656, 440, 3);
        Bloque aux52 = new Bloque(728, 440, 3);
        Bloque aux53 = new Bloque(800, 440, 3);
        Bloque aux54 = new Bloque(872, 440, 3);
        Bloque aux55 = new Bloque(944, 440, 3);
        Bloque aux56 = new Bloque(1016, 440, 3);
        Bloque aux57 = new Bloque(80, 584, 7);
        Bloque aux58 = new Bloque(152, 584, 7);
        Bloque aux59 = new Bloque(224, 584, 7);
        Bloque aux60 = new Bloque(296, 584, 7);
        Bloque aux61 = new Bloque(368, 584, 7);
        Bloque aux62 = new Bloque(440, 584, 7);
        Bloque aux63 = new Bloque(512, 584, 7);
        Bloque aux64 = new Bloque(584, 584, 7);
        Bloque aux65 = new Bloque(656, 584, 7);
        Bloque aux66 = new Bloque(728, 584, 7);
        Bloque aux67 = new Bloque(800, 584, 7);
        Bloque aux68 = new Bloque(872, 584, 7);
        Bloque aux69 = new Bloque(944, 584, 7);
        Bloque aux70 = new Bloque(1016, 584, 7);
        Bloque aux71 = new Bloque(80, 512, 1);
        Bloque aux72 = new Bloque(152, 512, 1);
        Bloque aux73 = new Bloque(224, 512, 1);
        Bloque aux74 = new Bloque(296, 512, 1);
        Bloque aux75 = new Bloque(368, 512, 1);
        Bloque aux76 = new Bloque(440, 512, 1);
        Bloque aux77 = new Bloque(512, 512, 1);
        Bloque aux78 = new Bloque(584, 512, 1);
        Bloque aux79 = new Bloque(656, 512, 1);
        Bloque aux80 = new Bloque(728, 512, 1);
        Bloque aux81 = new Bloque(800, 512, 1);
        Bloque aux82 = new Bloque(872, 512, 1);
        Bloque aux83 = new Bloque(944, 512, 1);
        Bloque aux84 = new Bloque(1016, 512, 1);
        lista.add(0, aux1);
        lista.add(1, aux2);
        lista.add(2, aux3);
        lista.add(3, aux4);
        lista.add(4, aux5);
        lista.add(5, aux6);
        lista.add(6, aux7);
        lista.add(7, aux8);
        lista.add(8, aux9);
        lista.add(9, aux10);
        lista.add(10, aux11);
        lista.add(11, aux12);
        lista.add(12, aux13);
        lista.add(13, aux14);
        lista.add(14, aux15);
        lista.add(15, aux16);
        lista.add(16, aux17);
        lista.add(17, aux18);
        lista.add(18, aux19);
        lista.add(19, aux20);
        lista.add(20, aux21);
        lista.add(21, aux22);
        lista.add(22, aux23);
        lista.add(23, aux24);
        lista.add(24, aux25);
        lista.add(25, aux26);
        lista.add(26, aux27);
        lista.add(27, aux28);
        lista.add(28, aux29);
        lista.add(29, aux30);
        lista.add(30, aux31);
        lista.add(31, aux32);
        lista.add(32, aux33);
        lista.add(33, aux34);
        lista.add(34, aux35);
        lista.add(35, aux36);
        lista.add(36, aux37);
        lista.add(37, aux38);
        lista.add(38, aux39);
        lista.add(39, aux40);
        lista.add(40, aux41);
        lista.add(41, aux42);
        lista.add(42, aux43);
        lista.add(43, aux44);
        lista.add(44, aux45);
        lista.add(45, aux46);
        lista.add(46, aux47);
        lista.add(47, aux48);
        lista.add(48, aux49);
        lista.add(49, aux50);
        lista.add(50, aux51);
        lista.add(51, aux52);
        lista.add(52, aux53);
        lista.add(53, aux54);
        lista.add(54, aux55);
        lista.add(55, aux56);
        lista.add(56, aux57);
        lista.add(57, aux58);
        lista.add(58, aux59);
        lista.add(59, aux60);
        lista.add(60, aux61);
        lista.add(61, aux62);
        lista.add(62, aux63);
        lista.add(63, aux64);
        lista.add(64, aux65);
        lista.add(65, aux66);
        lista.add(66, aux67);
        lista.add(67, aux68);
        lista.add(68, aux69);
        lista.add(69, aux70);
        lista.add(70, aux71);
        lista.add(71, aux72);
        lista.add(72, aux73);
        lista.add(73, aux74);
        lista.add(74, aux75);
        lista.add(75, aux76);
        lista.add(76, aux77);
        lista.add(77, aux78);
        lista.add(78, aux79);
        lista.add(79, aux80);
        lista.add(80, aux81);
        lista.add(81, aux82);
        lista.add(82, aux83);
        lista.add(83, aux84);
    }

    public void restart() {

    }

    /**
     * Metodo donde empieza el hilo del <code>JFrame</code>.
     */
    public void run() {
        while (true) {
            if (themeCont <= 0) {
                if (changeSong % 2 == 0) {
                    theme.play();
                    themeCont = 13000;
                } else {
                    theme2.play();
                    themeCont = 84000;
                }
                changeSong++;
            }
            themeCont--;
            //Si el juego no ha terminado hacer
            if (!gameOver) {
                // Si la bandera de pausa esta apagada
                if (!pausado) {
                    if (!instrucciones && start) {
                        actualiza();
                        if (empezar) {
                            checaColision();
                        }
                    }
                }
            }

            // Se actualiza el <code>JFrame</code> repintando el contenido.
            repaint();

            try {
                // El thread se duerme.
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }

    /**
     * Metodo usado para actualizar la posicion de objetos tortuga y caparazon.
     *
     */
    public void actualiza() {
        // Si el caparazon se le acaba de dar click y va volando
        if (click && volando) {
            int opcion = (int) ((Math.random() * 5)) + 6; //da la opcion sobre la distancia final en x
            //caparazon.setVelX(opcion * factorAumento);
            //caparazon.setVelY(-16 * factorAumentoY);
            click = false;
        }
        // Si el contador de oportunidades antes de perder vida es 0
        if (contadorVidas < 1) {
            vidas--;
            factorGravedad += 0.5;
            factorAumento += 0.3;
            factorAumentoY += 0.3;
            contadorVidas = 3;
        }
        // Si se acaban las vidas
        if (vidas == 0) {
            gameOver = true;
            rv1.setPosX(150);
            rv1.setPosY(450);
            rv2.setPosX(980);
            rv2.setPosY(450);
        }

        // Si se esta presionando la tecla izquierda
        if (izquierda) {
            barra.setVelX(barra.getVelX() - (float) .5);
        }
        // Si se esta presionando la tecla derecha
        if (derecha) {
            barra.setVelX(barra.getVelX() + (float) .5);
        }
        int x = (int) Math.round(barra.getVelX());
        barra.setPosX(x + barra.getPosX());
        if (barra.getVelX() < 0) {
            barra.setVelX(barra.getVelX() + (float) .4);
        } else if (barra.getVelX() > 0) {
            barra.setVelX(barra.getVelX() - (float) .4);
        }

        if (empezar) {
            if (Math.abs(bola.getVelX()) <= 0.5) {
                if (bola.getVelX() < 0) {
                    bola.setVelX(-1);
                } else {
                    bola.setVelX(1);
                }
            }
            if (Math.abs(bola.getVelY()) <= 0.5) {
                if (bola.getVelY() < 0) {
                    bola.setVelY(-1);
                } else {
                    bola.setVelY(1);
                }
            }
            bola.setPosX(bola.getPosX() + Math.round(bola.getVelX()));
            bola.setPosY(bola.getPosY() + Math.round(bola.getVelY()));
        } else {
            bola.setPosX(barra.getPosX() + barra.getAncho() / 2 - 10);
            if (barra.getPosX() < 0) {
                barra.setVelX(barra.getVelX() * -1);
                barra.setPosX(0);
            }
            if (barra.getPosX() > getWidth() - barra.getAncho()) {
                barra.setVelX(barra.getVelX() * -1);
                barra.setPosX(getWidth() - barra.getAncho());
            }
        }

        if (blockHit == 0) {
            level += 1;
            empezar = false;
            bola.setPosX(getWidth() / 2);
            bola.setPosY(getHeight() - 2 * barra.getAlto() - 1);
            bola.setPosX(bola.getPosX() - bola.getAncho() / 2);
            bola.setVelX(1);
            bola.setVelY(1);
            up1 = false;
            up2 = false;
            up3 = false;
            extraLife = false;

            if (lvl1) {
                gameOver = true;
                gane = true;
            }
            if (lvl5 && level == 6) {
                gameOver = true;
                gane = true;
            }

            if (level == 2) {
                level2();
            } else if (level == 3) {
                level3();
            } else if (level == 4) {
                level4();
            } else if (level == 5) {
                level5();
            }
        }

        if (up1) {
            upgrade1.setPosY(upgrade1.getPosY() + (int) upgrade1.getVelY());
        }
        if (up2) {
            upgrade2.setPosY(upgrade2.getPosY() + (int) upgrade2.getVelY());
        }
        if (up3) {
            upgrade3.setPosY(upgrade3.getPosY() + (int) upgrade3.getVelY());
        }
        if (extraLife) {
            extraVida.setPosY(extraVida.getPosY() + (int) extraVida.getVelY());
        }
    }

    /**
     * Metodo usado para checar las colisiones del objeto tortuga y caparazon
     * con entre si <code>JFrame</code>.
     */
    public void checaColision() {
        if (bola.getPosX() + bola.getAncho() > getWidth()) {
            bola.setPosX(getWidth() - bola.getAncho());
            bola.setVelX(-bola.getVelX());
            rebote = 4;
        } else if (bola.getPosX() < 0) {
            bola.setPosX(0);
            bola.setVelX(-bola.getVelX());
            rebote = 3;
        } else if (bola.getPosY() < 25) {
            bola.setPosY(25);
            bola.setVelY(-bola.getVelY());
            rebote = 2;
        } else if (bola.getPosY() + bola.getAlto() > getHeight()) {
            bola.setPosY(getHeight() - bola.getAlto());
            bola.setVelY(-bola.getVelY());
            rebote = 1;
            empezar = false;
            bola.setPosY(getHeight() - 2 * barra.getAlto() - 1);
            bola.setVelX(1);
            bola.setVelY(1);
            vidas--;
            up1 = false;
            up2 = false;
            up3 = false;
            extraLife = false;
            if (sonido) {
                lose.play();
            }
        }

        if (up1) {
            if (upgrade1.getPosY() + upgrade1.getAlto() > getHeight()) {
                up1 = false;
            } else if (upgrade1.intersecta(barra)) {
                if (sonido) {
                    extra.play();
                }
                score += 500;
                up1 = false;
            }
        }
        if (up2) {
            if (upgrade2.getPosY() + upgrade2.getAlto() > getHeight()) {
                up2 = false;
            } else if (upgrade2.intersecta(barra)) {
                if (sonido) {
                    extra.play();
                }
                if (bola.getVelX() < 0) {
                    bola.setVelX(-1);
                } else {
                    bola.setVelX(1);
                }
                if (bola.getVelY() < 0) {
                    bola.setVelY(-1);
                } else {
                    bola.setVelY(1);
                }
                up2 = false;
            }
        }
        if (up3) {
            if (upgrade3.getPosY() + upgrade3.getAlto() > getHeight()) {
                up3 = false;
            } else if (upgrade3.intersecta(barra)) {
                if (sonido) {
                    extra.play();
                }
                for (int x = 0; x < blockSize; x++) {
                    Bloque bl = (Bloque) lista.get(x);
                    if (bl.getContador() >= 0) {
                        score += 100;
                        bl.setContador(bl.getContador() - 1);
                        if (bl.getContador() == -1) {
                            blockHit -= 1;
                        }
                    }
                }
                up3 = false;
            }
        }
        if (extraLife) {
            if (extraVida.getPosY() + extraVida.getAlto() > getHeight()) {
                extraLife = false;
            } else if (extraVida.intersecta(barra)) {
                if (sonido) {
                    extra.play();
                }
                vidas++;
                extraLife = false;
            }
        }

        if (barra.getPosX() < 0) {
            barra.setVelX(barra.getVelX() * -1);
            barra.setPosX(0);
        }
        if (barra.getPosX() > getWidth() - barra.getAncho()) {
            barra.setVelX(barra.getVelX() * -1);
            barra.setPosX(getWidth() - barra.getAncho());
        }

        if (bola.intersecta(barra)) {
            if (sonido) {
                jump.play();
            }
            rebote = 1;
            bola.setPosY(getHeight() - 2 * barra.getAlto() - 1);
            int posBola = bola.getPosX() + bola.getAncho() / 2;
            if (bola.getPosY() + bola.getAlto() / 2 >= barra.getPosY() + 1) {
                bola.setVelX(-bola.getVelX());
            } else if (posBola > barra.getPosX() + 50 && posBola < barra.getPosX() + 100) {
                bola.setVelY(-bola.getVelY());
            } else if (posBola <= barra.getPosX() + 50) {
                bola.setVelX(bola.getVelX() - (float) .15);
                bola.setVelY(-(bola.getVelY() + (float) .3));
            } else if (posBola >= barra.getPosX() + 100) {
                bola.setVelX(bola.getVelX() + (float) .3);
                bola.setVelY(-(bola.getVelY() - (float) .15));
            }
        }

        boolean reciente = false;
        for (int c = 0; c < blockSize; c++) {
            Bloque aux = (Bloque) lista.get(c);
            if (aux.getContador() >= 0) {
                if (bola.intersecta(aux)) {
                    if (sonido) {
                        breaking.play();
                    }

                    int up = (int) (Math.random() * 100);
                    if (!up1 && up < 30) {
                        up1 = true;
                        upgrade1.setPosX(aux.getPosX() + aux.getAncho() / 2);
                        upgrade1.setPosY(aux.getPosY() + aux.getAlto() / 2);
                    }
                    if (!up2 && up >= 50 && up <= 51) {
                        up2 = true;
                        upgrade2.setPosX(aux.getPosX() + aux.getAncho() / 2);
                        upgrade2.setPosY(aux.getPosY() + aux.getAlto() / 2);
                    }
                    if (!up3 && up >= 60 && up <= 61) {
                        up3 = true;
                        upgrade3.setPosX(aux.getPosX() + aux.getAncho() / 2);
                        upgrade3.setPosY(aux.getPosY() + aux.getAlto() / 2);
                    }
                    if (!extraLife && up == 99) {
                        extraLife = true;
                        extraVida.setPosX(aux.getPosX() + aux.getAncho() / 2);
                        extraVida.setPosY(aux.getPosY() + aux.getAlto() / 2);
                    }

                    score += 100;
                    aux.setContador(aux.getContador() - 1);
                    if (aux.getContador() == -1) {
                        blockHit -= 1;
                    }
                    if (!reciente) {
                        int checa = 1;
                        if (bola.getVelX() > 0 && bola.getPosX() + bola.getAncho() >= aux.getPosX() && checa == 1 && Math.abs(bola.getPosX() + bola.getAncho() / 2 - (aux.getPosX() + aux.getAncho() / 2)) >= Math.abs(bola.getPosY() + bola.getAlto() / 2 - (aux.getPosY() + aux.getAlto() / 2))/* && bola.getPosY() > aux.getPosY() + aux.getAlto() && bola.getPosY() < aux.getPosY()*/) {
                            bola.setPosX(aux.getPosX() - bola.getAncho() - 1);
                            bola.setVelX(-bola.getVelX());
                            checa = 0;
                            reciente = true;
                            rebote = 4;
                        } else if (bola.getVelX() < 0 && bola.getPosX() <= aux.getPosX() + aux.getAncho() && checa == 1 && Math.abs(bola.getPosX() + bola.getAncho() / 2 - (aux.getPosX() + aux.getAncho() / 2)) >= Math.abs(bola.getPosY() + bola.getAlto() / 2 - (aux.getPosY() + aux.getAlto() / 2))/* && bola.getPosY() > aux.getPosY() + aux.getAlto() && bola.getPosY() < aux.getPosY()*/) {
                            bola.setPosX(aux.getPosX() + aux.getAncho() + 1);
                            bola.setVelX(-bola.getVelX());
                            checa = 0;
                            reciente = true;
                            rebote = 3;
                        } else if (bola.getVelY() < 0 && bola.getPosY() >= aux.getPosY() + aux.getAlto() - 10 && checa == 1 && Math.abs(bola.getPosX() + bola.getAncho() / 2 - (aux.getPosX() + aux.getAncho() / 2)) <= Math.abs(bola.getPosY() + bola.getAlto() / 2 - (aux.getPosY() + aux.getAlto() / 2))) {
                            bola.setPosY(aux.getPosY() + aux.getAlto() + 1);
                            bola.setVelY(-bola.getVelY());
                            checa = 0;
                            reciente = true;
                            rebote = 2;
                        } else if (bola.getVelY() > 0 && bola.getPosY() <= aux.getPosY() && checa == 1 && Math.abs(bola.getPosX() + bola.getAncho() / 2 - (aux.getPosX() + aux.getAncho() / 2)) <= Math.abs(bola.getPosY() + bola.getAlto() / 2 - (aux.getPosY() + aux.getAlto() / 2))) {
                            bola.setPosY(aux.getPosY() - bola.getAlto() - 1);
                            bola.setVelY(-bola.getVelY());
                            checa = 0;
                            reciente = true;
                            rebote = 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>mouseClicked</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando se hace click en el
     * ariados.
     *
     * @param e es el <code>evento</code> generado al presionar el ariados.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseEnteredd</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando el ariados entra.
     *
     *
     * @param e es el <code>evento</code> generado al mover el ariados.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseExited</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera cuando el ariados sale.
     *
     *
     * @param e es el <code>evento</code> generado con el movimiento del
     * ariados.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Metodo <I>mousePressed</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar el ariados.
     *
     *
     * @param e es el <code>evento</code> generado al presionar el ariados.
     */
    public void mousePressed(MouseEvent e) {
        // Si se presiona el caparazon y el objeto no ha sido picado antes, no esta volando ni pausado
        /*if (caparazon.intersecta(e.getX(), e.getY()) && !click && !volando && !pausado) {
         click = true;
         volando = true;
         }*/
        if (rv1.intersecta(e.getX(), e.getY())) {
            gameOver = false;
            gane = false;
            start = true;
            instrucciones = false;
            lvl1 = true;
            lvl5 = false;
            empezar = false;
            pausado = false;
            init();
        } else if (rv2.intersecta(e.getX(), e.getY())) {
            gameOver = false;
            gane = false;
            start = true;
            instrucciones = false;
            lvl5 = true;
            lvl1 = false;
            empezar = false;
            pausado = false;
            init();
        }
    }

    /**
     * Metodo <I>mouseReleased</I> sobrescrito de la interface
     * <code>MouseListener</code>.<P>
     * En este metodo maneja el evento que se genera al solta el ariados.
     *
     *
     * @param e es el <code>evento</code> generado al soltar el ariados.
     */
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Metodo <I>mouseMoved</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el ariados
     *
     *
     * @param e es el <code>evento</code> generado al mover el ariados.
     */
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Metodo <I>mouseDragged</I> sobrescrito de la interface
     * <code>MouseMotionListener</code>.<P>
     * En este metodo maneja el evento que se genera al mover el planeta una vez
     * seleccionado.
     *
     * @param e es el <code>evento</code> generado al mover el planeta.
     */
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        // Si se presiona P
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (pausado && !instrucciones) {
                pausado = false;
            } else {
                pausado = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_I) { // Si se presiona I
            if (instrucciones && start) {
                instrucciones = false;
                pausado = false;
            } else {
                instrucciones = true;
                pausado = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {   // Si se presiona A (Debugging para comprobar click en el aire)
            if (!volando) {
                click = true;
            } else {
                click = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {   // Si se presiona S
            if (!sonido) {
                sonido = true;
            } else {
                sonido = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {   // Si se presiona la flecha derecha
            if (!derecha) {
                derecha = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    // Si se presiona la flecha izquierda
            if (!izquierda) {
                izquierda = true;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_C && !pausado) {   // Si se presiona C
            cargar = true;
        } else if (e.getKeyCode() == KeyEvent.VK_G && !pausado) {   // Si se presiona G
            guardar = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !pausado) {   // Si se presiona G
            empezar = true;
        }
    }

    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {   // Cuando se deja de presionar la flecha izquierda
            if (izquierda) {
                izquierda = false;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {   // Cuando se deja de presionar la flecha derecha
            if (derecha) {
                derecha = false;
            }
        }
    }

    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo() throws IOException {
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            // Crear un archivo demo
            File puntos = new File(nombreArchivo);
            PrintWriter fileOut = new PrintWriter(puntos);
            // Valores default
            fileOut.println("5,250,0,0,false,false,250,380,0,0,5,3,.5,1,1,true");
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(nombreArchivo));
        }
        String dato = fileIn.readLine();

        while (dato != null) {
            // Leer el string, dividirlo y asignar a los auxiliares
            arr = dato.split(",");
            cPosX = (Integer.parseInt(arr[0]));
            cPosY = (Integer.parseInt(arr[1]));
            cVelX = (Float.parseFloat(arr[2]));
            cVelY = (Float.parseFloat(arr[3]));
            c = (Boolean.parseBoolean(arr[4]));
            v = (Boolean.parseBoolean(arr[5]));
            tPosX = (Integer.parseInt(arr[6]));
            tPosY = (Integer.parseInt(arr[7]));
            tVelX = (Float.parseFloat(arr[8]));
            scr = (Integer.parseInt(arr[9]));
            vid = (Integer.parseInt(arr[10]));
            contVid = (Integer.parseInt(arr[11]));
            fG = (Float.parseFloat(arr[12]));
            fA = (Float.parseFloat(arr[13]));
            fAY = (Float.parseFloat(arr[14]));
            son = (Boolean.parseBoolean(arr[15]));
            dato = fileIn.readLine();
        }
        fileIn.close();
    }

    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException {
        /*PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
         // Guardar los valores necesarios para volver a cargar el juego en donde se quedo
         fileOut.println("" + caparazon.getPosX() + "," + caparazon.getPosY() + ","
         + caparazon.getVelX() + "," + caparazon.getVelY() + "," + click
         + "," + volando + "," + tortuga.getPosX() + "," + tortuga.getPosY()
         + "," + tortuga.getVelX() + "," + score + "," + vidas + ","
         + contadorVidas + "," + factorGravedad + "," + factorAumento + ","
         + factorAumentoY + "," + sonido);
         fileOut.close();*/
    }

    /**
     * Metodo paint que pinta todo en el <code>JFrame<code>.
     *
     * @param g
     */
    public void paint1(Graphics g) {
        if (!gameOver) {
            if (instrucciones) {
                g.drawImage(inicio, 0, 0, this);
                Bloque extra1 = new Bloque(100, 590, 0);
                Bloque extra2 = new Bloque(200, 590, 1);
                Bloque extra3 = new Bloque(300, 590, 2);
                Bloque extra4 = new Bloque(400, 590, 3);
                Bloque extra5 = new Bloque(500, 590, 4);
                Bloque extra6 = new Bloque(600, 590, 5);
                Bloque extra7 = new Bloque(700, 590, 6);
                Bloque extra8 = new Bloque(800, 590, 7);
                g.drawImage(extra1.getImagenI(), extra1.getPosX(), extra1.getPosY(), this);
                g.drawImage(extra2.getImagenI(), extra2.getPosX(), extra2.getPosY(), this);
                g.drawImage(extra3.getImagenI(), extra3.getPosX(), extra3.getPosY(), this);
                g.drawImage(extra4.getImagenI(), extra4.getPosX(), extra4.getPosY(), this);
                g.drawImage(extra5.getImagenI(), extra5.getPosX(), extra5.getPosY(), this);
                g.drawImage(extra6.getImagenI(), extra6.getPosX(), extra6.getPosY(), this);
                g.drawImage(extra7.getImagenI(), extra7.getPosX(), extra7.getPosY(), this);
                g.drawImage(extra8.getImagenI(), extra8.getPosX(), extra8.getPosY(), this);
                g.drawImage(rv1.getImagenI(), rv1.getPosX(), rv1.getPosY(), this);
                g.drawImage(rv2.getImagenI(), rv2.getPosX(), rv2.getPosY(), this);
            } else {
                g.setFont(new Font("Arial", Font.PLAIN, 20));
                g.setColor(Color.RED);
                if (level == 1) {
                    g.drawImage(background, 0, 0, this);
                } else if (level == 2) {
                    g.drawImage(background2, 0, 0, this);
                } else if (level == 3) {
                    g.drawImage(background3, 0, 0, this);
                } else if (level == 4) {
                    g.drawImage(background4, 0, 0, this);
                } else if (level == 5) {
                    g.drawImage(background5, 0, 0, this);
                }
                g.drawString("Vidas", 10, 45);
                g.setColor(Color.orange);
                g.drawString("Score: " + score, 70, 45);
                String auxi;
                if (sonido) {
                    auxi = "Si";
                } else {
                    auxi = "No";
                }
                g.setColor(Color.black);
                g.drawString("Sonido: " + auxi, 1100, 45);
                for (int v = 0, pos = 10; v < vidas; v++, pos += 20) {
                    g.drawImage(vida, pos, 50, this);
                }
                for (int c = 0; c < blockSize; c++) {
                    Bloque aux = (Bloque) lista.get(c);
                    if (aux.getContador() >= 0) {
                        g.drawImage(aux.getImagenI(), aux.getPosX(), aux.getPosY(), this);
                    }
                }
                if (up1) {
                    g.drawImage(upgrade1.getImagenI(), upgrade1.getPosX(), upgrade1.getPosY(), this);
                }
                if (up2) {
                    g.drawImage(upgrade2.getImagenI(), upgrade2.getPosX(), upgrade2.getPosY(), this);
                }
                if (up3) {
                    g.drawImage(upgrade3.getImagenI(), upgrade3.getPosX(), upgrade3.getPosY(), this);
                }
                if (extraLife) {
                    g.drawImage(extraVida.getImagenI(), extraVida.getPosX(), extraVida.getPosY(), this);
                }
                g.drawImage(barra.getImagenI(), barra.getPosX(), barra.getPosY(), this);
                g.drawImage(bola.getImagenI(), bola.getPosX(), bola.getPosY(), this);
            }
        } else {
            if (gane) {
                g.drawImage(ganar, 0, 0, this);
                g.setFont(new Font("Arial", Font.PLAIN, 40));
                g.setColor(Color.red);
                g.drawString("Score: " + score, 130, 400);
            } else {
                g.drawImage(over, 0, 0, this);
                g.setFont(new Font("Arial", Font.PLAIN, 40));
                g.setColor(Color.red);
                g.drawString("" + score, 130, 280);
            }
            g.drawImage(rv1.getImagenI(), rv1.getPosX(), rv1.getPosY(), this);
            g.drawImage(rv2.getImagenI(), rv2.getPosX(), rv2.getPosY(), this);
        }
    }
}
