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

/**
 *
 * @author jlsuarezdiaz
 */
public class MineSweeper2 extends GamePanel {
    private static final Surface ALLOWED_SURFACES[] = {Surface.DISK,Surface.H_CYLINDER,Surface.V_CYLINDER,Surface.H_SPHERE,Surface.V_SPHERE,Surface.TORUS};
    
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
    private int mines_left;
    private Image[] img;

    private int all_cells;
    //private JLabel statusbar;
    
    /**
     * Creates new form MineSweeper2
     */
    public MineSweeper2() {
        
        setDimension(new Dimension(CELL_SIZE*N_ROWS,CELL_SIZE*N_COLS));
        initComponents();
        
        
        img = new Image[NUM_IMAGES];
        
        for(int i = 0; i < NUM_IMAGES; i++){
            img[i] = (new ImageIcon(getClass().getResource("/minesweeper/media/"+i + ".png"))).getImage();
        }
        
        setDoubleBuffered(true);
        addMouseListener(new MinesAdapter());
        newGame();
    }
    
    private Point positionToMineCoord(int pos){
        return new Point(mod(pos,N_COLS),floorDiv(pos,N_COLS));
    }
    
    private int mineCoordToPosition(Point mine_coord){
        return mine_coord.y*N_COLS + mine_coord.x;
    }
    
    private Point getCanonicalPosition(Point mine_coord){
        //Coordenadas de panel --> Coordenadas de panel canónicas
        Point p = new Point(mine_coord.x*CELL_SIZE, mine_coord.y*CELL_SIZE);
        p = getCanonicalCoordinates(p, new Dimension(CELL_SIZE,CELL_SIZE));
        //Coordenadas de panel canónicas --> Coordenadas de mina canónicas
        return new Point(p.x/CELL_SIZE,p.y/CELL_SIZE);
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
                        int canon_pos = mineCoordToPosition(canonical);
                        System.out.println(p);
                        System.out.println(canonical);
                        System.out.println(canon_pos);
                        System.out.println("---");
                        
                        if (field[canon_pos] != COVERED_MINE_CELL)
                            field[canon_pos] += 1;
                    }
                }
                
               
                
                
                
                /*
                if (current_col > 0) { 
                    cell = position - 1 - N_COLS;
                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                    cell = position - 1;
                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;

                    cell = position + N_COLS - 1;
                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                }

                cell = position - N_COLS;
                if (cell >= 0)
                    if (field[cell] != COVERED_MINE_CELL)
                        field[cell] += 1;
                cell = position + N_COLS;
                if (cell < all_cells)
                    if (field[cell] != COVERED_MINE_CELL)
                        field[cell] += 1;

                if (current_col < (N_COLS - 1)) {
                    cell = position - N_COLS + 1;
                    if (cell >= 0)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                    cell = position + N_COLS + 1;
                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                    cell = position + 1;
                    if (cell < all_cells)
                        if (field[cell] != COVERED_MINE_CELL)
                            field[cell] += 1;
                }
                */
            }
        }
        
        addSoundEffect("explosion", "/surfacegames/media/explosion.wav");
        addSoundEffect("coin", "/surfacegames/media/coin.wav");
        addSoundEffect("win","/surfacegames/media/epic_win.wav");
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
            System.out.println(p);
        }
        for(Point p: neighborhood){
            if(isPositionValid(p)){
                Point canonical = getCanonicalPosition(p);
                System.out.println("From: "+mc);
                System.out.println("To: "+p);
                System.out.println("Canon: "+canonical);
                System.out.println("---");
                int canon_pos = mineCoordToPosition(canonical);
                if(field[canon_pos] > MINE_CELL){
                    field[canon_pos] -= COVER_FOR_CELL;
                    if(field[canon_pos] == EMPTY_CELL){
                        find_empty_cells(canon_pos);
                    }
                }
            }
        }
        
        /*
        if (current_col > 0) { 
            cell = j - N_COLS - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j - 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + N_COLS - 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }
        }

        cell = j - N_COLS;
        if (cell >= 0)
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                    find_empty_cells(cell);
            }

        cell = j + N_COLS;
        if (cell < all_cells)
            if (field[cell] > MINE_CELL) {
                field[cell] -= COVER_FOR_CELL;
                if (field[cell] == EMPTY_CELL)
                    find_empty_cells(cell);
            }

        if (current_col < (N_COLS - 1)) {
            cell = j - N_COLS + 1;
            if (cell >= 0)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + N_COLS + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }

            cell = j + 1;
            if (cell < all_cells)
                if (field[cell] > MINE_CELL) {
                    field[cell] -= COVER_FOR_CELL;
                    if (field[cell] == EMPTY_CELL)
                        find_empty_cells(cell);
                }
        }
        */
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
            //statusbar.setText("Game won");
        } else if (!inGame){
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
    
    
    
    class MinesAdapter extends MouseAdapter {
        
        @Override
        public void mousePressed(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            int cCol = x / CELL_SIZE;
            int cRow = y / CELL_SIZE;

            boolean rep = false;


            if (!inGame) {
                newGame();
                repaint();
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
