/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.lang.Math.floorDiv;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import surfacegames.GamePanel;
import surfacegames.Surface;
import static utils.Math.mod;
import static java.lang.Math.floorDiv;
import javax.swing.JOptionPane;
import static java.lang.Math.floorDiv;
import static java.lang.Math.floorDiv;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.Timer;
import surfacegames.AnimatedGamePanel;
import surfacegames.Scoreable;

/**
 *
 * @author jlsuarezdiaz
 */
public class MinesPanel extends AnimatedGamePanel {
    private static final Surface ALLOWED_SURFACES[] = {Surface.DISK,Surface.H_CYLINDER,Surface.V_CYLINDER,Surface.H_SPHERE,Surface.V_SPHERE,Surface.TORUS,Surface.TORUS_2};
    
    private final int NUM_IMAGES = 13;
    private final int CELL_SIZE = 15;

    private final int COVER_FOR_CELL = 10;
    private final int MARK_FOR_CELL = 10;
    private final int EMPTY_CELL = 0;
    private final int MINE_CELL = 9;
    private final int COVERED_MINE_CELL = MINE_CELL + COVER_FOR_CELL;
    private final int MARKED_MINE_CELL = COVERED_MINE_CELL + MARK_FOR_CELL;

    private final int DRAW_MINE = 9;
    private final int DRAW_COVER = 10;
    private final int DRAW_MARK = 11;
    private final int DRAW_WRONG_MARK = 12;
    
    private final int N_MINES = 40;
    private final int N_ROWS = 16;
    private final int N_COLS = 16;

    private int[] field;
    private boolean inGame;
    private boolean started;
    private int mines_left;
    private Image[] img;

    private int all_cells;
    //private JLabel statusbar;
    
    private Scoreable mines_score = null;
    private Scoreable times_score = null;
    private JButton theFace = null;
    private final Icon[] face_icons = {new javax.swing.ImageIcon(getClass().getResource("/minesweeper/media/happy.png")),
                                       new javax.swing.ImageIcon(getClass().getResource("/minesweeper/media/expecting.png")),
                                       new javax.swing.ImageIcon(getClass().getResource("/minesweeper/media/dead.png")),
                                       new javax.swing.ImageIcon(getClass().getResource("/minesweeper/media/easy.png"))};
    private static final int NORMAL_FACE = 0;
    private static final int PRESSED_FACE = 1;
    private static final int EXPLODED_FACE = 2;
    private static final int WIN_FACE = 3;
    
    private Timer timer;
    
    /**
     * Creates new form MineSweeper2
     */
    public MinesPanel() {
        
        setDimension(new Dimension(CELL_SIZE*N_ROWS,CELL_SIZE*N_COLS));
        initComponents();
        
        
        img = new Image[NUM_IMAGES];
        
        for(int i = 0; i < NUM_IMAGES; i++){
            img[i] = (new ImageIcon(getClass().getResource("/minesweeper/media/"+i + ".png"))).getImage();
        }
        
        timer = new Timer(1000,this);
        
        setDoubleBuffered(true);
        addMouseListener(new MinesAdapter());
        newGame();
        
        addSoundEffect("explosion", "/surfacegames/media/explosion.wav");
        addSoundEffect("coin", "/surfacegames/media/coin.wav");
        addSoundEffect("win","/surfacegames/media/epic_win.wav");
    }
    
    public void setMinesScore(Scoreable s){
        this.mines_score = s;
        if(mines_score != null){
            mines_score.setScore(this.mines_left);
        }
    }
    
    public void setTimeScore(Scoreable s){
        this.times_score = s;
        if(times_score != null){
            times_score.setScore(0);
        }
    }
    
    public void setTheFace(JButton bt){
        this.theFace = bt;
        if(this.theFace != null){
            theFace.setIcon(face_icons[NORMAL_FACE]);
            theFace.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newGame();
                    repaint();
                }
            });
        }
    }
    
    private Point positionToMineCoord(int pos){
        return new Point(mod(pos,N_COLS),floorDiv(pos,N_COLS));
    }
    
    private int mineCoordToPosition(Point mine_coord){
        //System.out.println(mine_coord);
        return ((mine_coord!=null)?(mine_coord.y*N_COLS + mine_coord.x):-1);
    }
    
    private Point getCanonicalPosition(Point mine_coord){
        //Coordenadas de panel --> Coordenadas de panel canónicas
        Point p = new Point(mine_coord.x*CELL_SIZE, mine_coord.y*CELL_SIZE);
        p = getCanonicalCoordinates(p, new Dimension(CELL_SIZE,CELL_SIZE));
        //Coordenadas de panel canónicas --> Coordenadas de mina canónicas
        return (p != null)?new Point(p.x/CELL_SIZE,p.y/CELL_SIZE):null;
    }
    
    private boolean isPositionValid(Point mine_coord){
        //Coordenadas de panel --> Coordenadas de panel canónicas
        Point p = new Point(mine_coord.x*CELL_SIZE, mine_coord.y*CELL_SIZE);
        
        return !isOnBorderOrBeyond(p);
    }
    
    /**
     * @deprecated 
     * @param pos
     * @return 
     */
    private int getCanonicalPosition(int pos){
        //Posicion --> Coordenadas de mina
        int y = floorDiv(pos,N_COLS);
        int x = mod(pos,N_COLS);
        //Coordenadas de mina --> Coordenadas de panel
        Point p = new Point(x*CELL_SIZE,y*CELL_SIZE);
        //Coordenadas de panel --> Coordenadas de panel canónicas
        p = getCanonicalCoordinates(p, new Dimension(CELL_SIZE,CELL_SIZE));
        //Coordenadas de panel canónicas --> Coordenadas de mina canónicas
        x = p.x /CELL_SIZE;
        y = p.y /CELL_SIZE;
        // Coordenadas de mina canónicas --> Posición canónica.
        pos = y*N_COLS + x;
        return pos;
    }
    
    /**
     * @deprecated 
     * @param pos
     * @return 
     */
    private boolean isPositionValid(int pos){
        //Posicion --> Coordenadas de mina
        int y = floorDiv(pos,N_COLS);
        int x = mod(pos,N_COLS);
        //Coordenadas de mina --> Coordenadas de panel
        Point p = new Point(x*CELL_SIZE,y*CELL_SIZE);
        //Coordenadas de panel --> Coordenadas de panel canónicas
        return !isOnBorderOrBeyond(p);
    }
    
    private void newGame(){
        Random random;
        int current_col;
        
        int i = 0;
        int position = 0;
        //int cell = 0;
        //int canonical = 0;
        
        random = new Random();
        inGame = true;
        timer.stop();
        started = false;
        mines_left = N_MINES;
        
        all_cells = N_ROWS*N_COLS;
        field = new int[all_cells];
        
        for (i = 0; i < all_cells; i++)
            field[i] = COVER_FOR_CELL;
        
        //statusbar.setText(Integer.toString(mines_left));
        
        i = 0;
        while (i < N_MINES) {

            position = (int) (all_cells * random.nextDouble());

            if ((position < all_cells) &&
                (field[position] != COVERED_MINE_CELL)) {


                current_col = position % N_COLS;
                field[position] = COVERED_MINE_CELL;
                i++;
                Point mc = positionToMineCoord(position);
                
                Point neighborhood[] = new Point[]{
                    new Point(mc.x-1, mc.y-1), //arriba izquierda
                    new Point(mc.x-1, mc.y  ), //PSOE
                    new Point(mc.x-1, mc.y+1), //abajo izquierda
                    new Point(mc.x  , mc.y-1), //arriba centro
                    new Point(mc.x  , mc.y+1), //abajo centro
                    new Point(mc.x+1, mc.y-1), //arriba derecha
                    new Point(mc.x+1, mc.y  ), //Ciudadanos
                    new Point(mc.x+1, mc.y+1), //abajo derecha
                };
                
                for(Point p: neighborhood){
                    if(isPositionValid(p)){
                        Point canonical = getCanonicalPosition(p);
                        if(canonical != null){
                            int canon_pos = mineCoordToPosition(canonical);

                            if (field[canon_pos] != COVERED_MINE_CELL)
                                field[canon_pos] += 1;
                        }
                    }
                }             
                
            }
        }
        if(mines_score != null) mines_score.setScore(this.mines_left);
        if(times_score != null) times_score.setScore(0);
        if(theFace != null) theFace.setIcon(face_icons[NORMAL_FACE]);
        
    }
    
    public void find_empty_cells(int j) {

        int current_col = j % N_COLS;

        Point mc = positionToMineCoord(j);
        Point neighborhood[] = new Point[]{
            new Point(mc.x-1, mc.y-1), //arriba izquierda
            new Point(mc.x-1, mc.y  ), //PSOE
            new Point(mc.x-1, mc.y+1), //abajo izquierda
            new Point(mc.x  , mc.y-1), //arriba centro
            new Point(mc.x  , mc.y+1), //abajo centro
            new Point(mc.x+1, mc.y-1), //arriba derecha
            new Point(mc.x+1, mc.y  ), //Ciudadanos
            new Point(mc.x+1, mc.y+1), //abajo derecha
        };
        
        for(Point p: neighborhood){
            if(isPositionValid(p)){
                Point canonical = getCanonicalPosition(p);
                if(canonical != null){
                    int canon_pos = mineCoordToPosition(canonical);
                    if(field[canon_pos] > MINE_CELL && (field[canon_pos] != COVERED_MINE_CELL)){ //TODO: el segundo término del AND es un parche para solucionar el bug del 2-toro (la mina en la esquina no detecta un no vecino y este sí alcanza la mina.
                        field[canon_pos] -= COVER_FOR_CELL;
                        if(field[canon_pos] == EMPTY_CELL){
                            find_empty_cells(canon_pos);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {

        int cell = 0;
        int uncover = 0;

        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j++) {

                cell = field[(i * N_COLS) + j];

                if (inGame && cell == MINE_CELL)
                    inGame = false;

                if (!inGame) {
                    if (cell == COVERED_MINE_CELL) {
                        cell = DRAW_MINE;
                    } else if (cell == MARKED_MINE_CELL) {
                        cell = DRAW_MARK;
                    } else if (cell > COVERED_MINE_CELL) {
                        cell = DRAW_WRONG_MARK;
                    } else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                    }


                } else {
                    if (cell > COVERED_MINE_CELL)
                        cell = DRAW_MARK;
                    else if (cell > MINE_CELL) {
                        cell = DRAW_COVER;
                        uncover++;
                    }
                }

                g.drawImage(img[cell], (j * CELL_SIZE),
                    (i * CELL_SIZE), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            playSoundEffect("win");
            if(theFace != null) theFace.setIcon(face_icons[WIN_FACE]);
            timer.stop();
            //statusbar.setText("Game won");
        } else if (!inGame){
            if(theFace != null) theFace.setIcon(face_icons[EXPLODED_FACE]);
            timer.stop();
            //statusbar.setText("Game lost");
        }
    }

    @Override
    public Surface[] getAllowedSurfaces() {
        return ALLOWED_SURFACES;
    }

    @Override
    public void pause() {
        
    }
    
    @Override
    public boolean isSurfaceChangeAllowedDuringGame(){
        return false;
    }

    @Override
    public void setSurface(Surface s) {
        super.setSurface(s);
        newGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(times_score != null){
            times_score.increaseScore(1);
        }
    }
    
    
    
    class MinesAdapter extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) {
            if(inGame){
                int x = e.getX();
                int y = e.getY();

                int cCol = x / CELL_SIZE;
                int cRow = y / CELL_SIZE;

                boolean rep = false;


                //if (!inGame) {
                    //newGame();
                    //repaint();
                //}

                if(!started){
                    started = true;
                    timer.start();
                }


                if ((x < N_COLS * CELL_SIZE) && (y < N_ROWS * CELL_SIZE)) {

                    if (e.getButton() == MouseEvent.BUTTON3) {

                        if (field[(cRow * N_COLS) + cCol] > MINE_CELL) {
                            rep = true;

                            if (field[(cRow * N_COLS) + cCol] <= COVERED_MINE_CELL) {
                                if (mines_left > 0) {
                                    field[(cRow * N_COLS) + cCol] += MARK_FOR_CELL;
                                    mines_left--;
                                    //statusbar.setText(Integer.toString(mines_left));
                                } 
                                else{
                                    //statusbar.setText("No marks left");
                                }
                            } 
                            else {

                                field[(cRow * N_COLS) + cCol] -= MARK_FOR_CELL;
                                mines_left++;
                                //statusbar.setText(Integer.toString(mines_left));
                            }
                        }

                    } else {

                        if (field[(cRow * N_COLS) + cCol] > COVERED_MINE_CELL) {
                            return;
                        }

                        if ((field[(cRow * N_COLS) + cCol] > MINE_CELL) &&
                            (field[(cRow * N_COLS) + cCol] < MARKED_MINE_CELL)) {

                            field[(cRow * N_COLS) + cCol] -= COVER_FOR_CELL;
                            rep = true;

                            if (field[(cRow * N_COLS) + cCol] == MINE_CELL){
                                inGame = false;
                                playSoundEffect("explosion");
                            }
                            else if (field[(cRow * N_COLS) + cCol] == EMPTY_CELL){
                                find_empty_cells((cRow * N_COLS) + cCol);
                                playSoundEffect("coin");
                            }
                            else{
                                playSoundEffect("coin");
                            }
                        }
                    }

                    if (rep)
                        repaint();

                }
                if(mines_score != null) mines_score.setScore(mines_left);
                if(theFace != null) theFace.setIcon(face_icons[PRESSED_FACE]);
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e){
            if(theFace != null){
                if(theFace.getIcon().equals(face_icons[PRESSED_FACE])){
                    theFace.setIcon(face_icons[NORMAL_FACE]);
                }
            }
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
