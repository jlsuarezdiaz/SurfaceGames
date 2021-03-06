/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

//import com.sun.javafx.tk.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import surfacegames.AnimatedGamePanel;
import surfacegames.Direction;
import surfacegames.GamePanel;
import surfacegames.Scoreable;
import surfacegames.Surface;

/**
 *
 * @author nuria
 */
public class SnakePanel extends AnimatedGamePanel {
    private static final Surface[] allowedSurfaces = {Surface.DISK,Surface.V_SPHERE,Surface.H_SPHERE,Surface.V_CYLINDER,Surface.H_CYLINDER,Surface.TORUS,Surface.TORUS_2, 
        Surface.PROJECTIVE, Surface.V_MOBIUS, Surface.H_MOBIUS, Surface.V_KLEIN, Surface.H_KLEIN, Surface.PROJECTIVE_4};
    
    private final int MAX_DOTS = 2000; // Tamaño máximo de la serpiente
    private final int DOT_SIZE = 10;
    private final int DELAY = 140;
    
    private int MAX_X = dim.width/10-1;
    private int MAX_Y = dim.height/10-1;
    
    private final Point snake[] = new Point[MAX_DOTS]; //Puntos totales de la serpiente
    
    private int dots; // Tamaño actual de la serpiente
    private Point apple = new Point(); //Posición de la manzana
    
    // Dirección de movimiento (se actualiza en los eventos de teclado)
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    //Temporizador que gestiona la animación
    private Timer timer;
    
    //Gráficos
    private Image ball_img;
    private Image apple_img;
    private Image head_img;
    
    private boolean pause = false;
    
    private Scoreable score;

    /**
     * Creates new form SnakePanel
     */
    public SnakePanel() {
        super();
        initComponents();
        initBoard();
        
    }
    
    public SnakePanel(Scoreable s){
        super();
        initComponents();
        setScoreable(s);
        initBoard();
    }
    
    public void setScoreable(Scoreable s){
        this.score = s;
        if(score != null) score.setScore(2*(dim.width+dim.height)/10); // Puntuación inicial: el perímetro.
    }
    
    public void initBoard(){
        setBackground(Color.BLACK); //TODO imagen chula de fondo (?)
        setFocusable(true); //Para que detecte los eventos de tecla (?)
        setDoubleBuffered(true); //Esto es algo de IG xd
        
        loadImages();
        initGame(); //TODO llamar a esta función a través de algún menú en vez de aquí
    }
    
    private void loadImages() {

        ImageIcon iid = new ImageIcon(getClass().getResource("/snake/media/dot.png"));
        ball_img = iid.getImage();

        ImageIcon iia = new ImageIcon(getClass().getResource("/snake/media/apple.png"));
        apple_img = iia.getImage();
        
        

        ImageIcon iih = new ImageIcon(getClass().getResource("/snake/media/head.png"));
        head_img = iih.getImage();
    }
    
    private void initGame() {
        dots = 3; //Empieza con 3 puntos

        for (int z = 0; z < dots; z++) { 
            //Inicialización de la serpiente (la cabeza en (50,50) y su cola hacia la izquierda.
            snake[z] = new Point(50-z*10,50);
            //snake[z].x = 50 - z*10;
            //snake[z].y = 50;
        }
        for (int z = dots; z < MAX_DOTS; z++){
            snake[z] = new Point();
        }
        
        locateApple(); //Coloca la manzana.
        
        

        //Temporizador: controla la animación.
        // 1 (DELAY) - intervalo (en ms) en que se ejecuta el evento
        // 2 (this) - evento que activa el temporizador (mas o menos)
        timer = new Timer(DELAY, this);
        timer.start();
        
        setBackgroundSound("/surfacegames/media/tetris_sound.wav");
        playBackgroundSound();
        
        addSoundEffect("coin", "/surfacegames/media/coin.wav");
        addSoundEffect("fortnite", "/surfacegames/media/fortnite.wav");
        addSoundEffect("explosion", "/surfacegames/media/explosion.wav");
        addSoundEffect("teleport", "/surfacegames/media/teleport.wav");
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple_img, apple.x, apple.y, this); //Pinta manzana

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head_img, snake[z].x, snake[z].y, this); //Pinta cabeza de la serpiente
                } else {
                    g.drawImage(ball_img, snake[z].x, snake[z].y, this); //Pinta el resto del cuerpo
                }
            }
            if(pause){
                paintPause(g);
            }
            this.getToolkit().sync(); //Fuerza la sincronización
        } else {
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (dim.width - metr.stringWidth(msg)) / 2, dim.height / 2);
        
    }
    
    private void paintPause(Graphics g){
        String msg = "PAUSA";
        Font medium = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(medium);

        g.setColor(Color.white);
        g.setFont(medium);
        g.drawString(msg, (dim.width - metr.stringWidth(msg)) / 2, dim.height / 2);
    }
    
    public void pause(){
        pause=true;
        timer.stop();
        stopBackgroundSound();
     
        repaint();  
    }
    
    public void resume(){
        if(inGame){
            pause=false;
            timer.start();
            playBackgroundSound();
        }
    }
    
    private void checkApple(){
        // Si pasa por la manzana crece y se crea otra manzana.
        if(snake[0].equals(apple)){
            if(dots < MAX_DOTS -1) dots++;
            playSoundEffect("coin");
            if(score != null) score.increaseScore(5*dots);
            locateApple();
        }
    }
    
    public void move(){
        //Actualiza la posición de la serpiente según la dirección de movimiento.
        for(int z = dots; z > 0; z--){
            //Avanza el cuerpo de la serpiente
            snake[z].x = snake[z-1].x;
            snake[z].y = snake[z-1].y;
        }
        
        //Actualiza la cabeza
        if (leftDirection) {
            snake[0].x -= DOT_SIZE;
        }

        if (rightDirection) {
            snake[0].x += DOT_SIZE;
        }

        if (upDirection) {
            snake[0].y -= DOT_SIZE;
        }

        if (downDirection) {
            snake[0].y += DOT_SIZE;
        }
        
        //Actualiza la cabeza y la dirección según la superficie
        /*
        if(isOnOrientationChange(snake[0])){
            if(leftDirection || rightDirection){
                leftDirection=!leftDirection;
                rightDirection=!rightDirection;
            }
            if(upDirection || downDirection){
                upDirection=!upDirection;
                downDirection=!downDirection;
            }
        }*/
        Direction dir = getDirectionChange(snake[0]);
        switch(dir){
            case UP:
                upDirection = true;
                downDirection = false;
                leftDirection = false;
                rightDirection = false;
                break;
            case DOWN:
                upDirection = false;
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
                break;
            case LEFT:
                upDirection = false;
                downDirection = false;
                leftDirection = true;
                rightDirection = false;
                break;
            case RIGHT:
                upDirection = false;
                downDirection = false;
                leftDirection = false;
                rightDirection = true;
                break;
        }
        
        Point last = snake[0];
        snake[0] = getCanonicalCoordinates(snake[0],new Dimension(DOT_SIZE,DOT_SIZE));
        
        if(!last.equals(snake[0])){
            playSoundEffect("teleport");
        }
        
        if(score != null){
            score.decreaseScore(1);
            int curr = score.getScore();
            if(curr < 30) score.setForegroundColor(Color.RED);
            else if(curr < 100) score.setForegroundColor(Color.YELLOW);
            else score.setForegroundColor(Color.GREEN);
        } //Cada paso resta 1
        
    }
    
    private void checkCollision(){
        // Si hemos chocado con algún border o estamos sin puntos se acabó
        if(snake[0] == null || isOnBorderOrBeyond(snake[0]) || (score != null && score.getScore() <= 0)){
            inGame = false;
        }
        else{
            //Else comprobamos si choca consigo misma
            for(int z = dots; z > 0; z--){
                if((z > 4) && snake[0].equals(snake[z])){
                    inGame = false; 
                }
            }
        }
        
        if(!inGame){
            timer.stop();
            stopBackgroundSound();
            endBackgroundSound();
            playSoundEffect("explosion");
            
            try{
                sleep(2000);
            }
            catch(Exception ex){}
            playSoundEffect("fortnite");
        }
    }
    
    private void locateApple(){
        //Cambia la manzana al azar
        
        apple.x = ((int)(Math.random() * MAX_X))*DOT_SIZE;
        apple.y = ((int)(Math.random() * MAX_Y))*DOT_SIZE;
    }

    @Override
    public void setDimension(Dimension d) {
        super.setDimension(d);
        MAX_X = dim.width/10-1;
        MAX_Y = dim.height/10-1;
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        //Evento que se activa con cada tick del timer.
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    
    @Override
    public Surface[] getAllowedSurfaces() {
        return allowedSurfaces;
    }
    
    @Override
    public boolean isSurfaceChangeAllowedDuringGame(){
        return true;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //Manejador de tecla pulsada.
        //Cambia la dirección según la flecha
        //(impide pasar de una dirección a la opuesta, hay que girar 90º primero)
        
        int key = evt.getKeyCode();

        if(!pause){
            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
        if(key == KeyEvent.VK_P){
            if(pause)
                resume();
            else
                pause();
        }
        
    }//GEN-LAST:event_formKeyPressed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
