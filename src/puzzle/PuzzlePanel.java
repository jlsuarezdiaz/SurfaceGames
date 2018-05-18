/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import surfacegames.GamePanel;
import surfacegames.Surface;

/**
 *
 * @author nuria
 */
public class PuzzlePanel extends GamePanel{
    private static final Surface[] allowedSurfaces = {Surface.DISK,Surface.V_SPHERE,Surface.H_SPHERE,Surface.V_CYLINDER,Surface.H_CYLINDER,Surface.TORUS};

    private JPanel panel;
    private BufferedImage source;
    private BufferedImage resized;    
    private Image image;
    private MyButton lastButton;
    private int width, height;    
    private int fil = 4;
    private int col = 4;
            
    private List<MyButton> buttons;
    
    private final int NUMBER_OF_BUTTONS = fil*col;
    private int desired_width = 300;

    public PuzzlePanel() {
        initUI();
    }
    
    /*Métodos abstractos*/
    @Override
    public void pause(){ 
    };
    
    @Override
    public  Surface[] getAllowedSurfaces(){
        return allowedSurfaces;
    };
    
    @Override
    public boolean isSurfaceChangeAllowedDuringGame(){
        return true;
    }
    
  
    private void initUI() {
        buttons = new ArrayList<>();

        panel = new JPanel();
        panel.setSize(this.dim);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(fil, col, 0, 0));
        
        desired_width = this.dim.width;

        try {
            source = loadImage();
            int h = getNewHeight(source.getWidth(), source.getHeight());
            resized = resizeImage(source, desired_width, h,
                    BufferedImage.TYPE_INT_ARGB);
            
        } catch (IOException ex) {
            Logger.getLogger(PuzzlePanel.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        width = resized.getWidth(null);
        height = resized.getHeight(null);
        
        add(panel, BorderLayout.CENTER);

        for (int i = 0; i < fil; i++) {

            for (int j = 0; j < col; j++) {

                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / col, i * height / fil,
                                (width / col), height / fil)));
                
                
                MyButton button = new MyButton(image);
                button.putClientProperty("position", new Point(i, j));

                if (i == 3 && j == 2) {
                    lastButton = new MyButton();
                    lastButton.setBorderPainted(true);
                    lastButton.setContentAreaFilled(true);
                    lastButton.setLastButton();
                    lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    buttons.add(button);
                }
            }
        }
        
        Collections.shuffle(buttons);
        buttons.add(lastButton);

        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) {
            MyButton btn = buttons.get(i);
            panel.add(btn);
            btn.setBorder(BorderFactory.createLineBorder(Color.gray));
            btn.addActionListener(new ClickAction());
        }
        
    }
    
    
    private int getNewHeight(int w, int h) {
        double ratio = w / (double) w;
        int newHeight = (int) (h * ratio);
        return newHeight;
    }

    private BufferedImage loadImage() throws IOException {

        BufferedImage bimg = ImageIO.read(new File("src/puzzle/icesid.jpg"));

        return bimg;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width,
            int height, int type) throws IOException {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }
    
    private boolean validateMovement(int bidx, int lidx){
        Surface tipo = this.getSurface();
        boolean disco, lateral, bases;

       switch(tipo){
            case DISK:
               return (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - col == lidx) || (bidx + col == lidx);
            
            case V_CYLINDER:
            case V_SPHERE:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - col == lidx) || (bidx + col == lidx);
                lateral = (bidx%col == 0 && lidx == bidx + (col-1)) || (lidx%col == 0 && bidx == lidx + (col-1));
                return(disco||lateral);
            
            case H_CYLINDER:
            case H_SPHERE:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - col == lidx) || (bidx + col == lidx);
                bases = (bidx < col && lidx == bidx + col*(fil-1)) || (lidx < col  && bidx == lidx + col*(fil-1));
                return(disco||bases);
                
            case TORUS:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - col == lidx) || (bidx + col == lidx);
                lateral = (bidx%col == 0 && lidx == bidx + (col-1)) || (lidx%col == 0 && bidx == lidx + (col-1));
                bases = (bidx < col && lidx == bidx + col*(fil-1)) || (lidx < col  && bidx == lidx + col*(fil-1));
                return(disco||lateral||bases);
               
       }                

        
        return false;
    }
    
    private class ClickAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            checkButton(e);
            checkSolution();
        }

        private void checkButton(ActionEvent e) {
            int lidx = 0;
            
            for (MyButton button : buttons) {
                if (button.isLastButton()) {
                    lidx = buttons.indexOf(button);
                }
            }

            JButton button = (JButton) e.getSource();
            int bidx = buttons.indexOf(button);

            if (validateMovement(bidx, lidx)) {
                Collections.swap(buttons, bidx, lidx);
                updateButtons();
            }
        }
        


        private void updateButtons() {
            
            panel.removeAll();
            
            for (JComponent btn : buttons) {
                panel.add(btn);
            }

            panel.validate();
        }
    }
     
    private void checkSolution() {

        List<Point> current = new ArrayList<>();

        for (JComponent btn : buttons) {
            current.add((Point) btn.getClientProperty("position"));
        }
        
        ArrayList<ArrayList<Point>> sols = createSolutions();

        for(ArrayList<Point> solution : sols){
            if (compareList(solution, current)) {
            JOptionPane.showMessageDialog(this, "Terminado!!!!",
                    "Congratulation", JOptionPane.INFORMATION_MESSAGE);
        }
        }
        
    }
    
    private ArrayList<ArrayList<Point>> createSolutions(){
        Surface tipo = this.getSurface();
        
        ArrayList<Point> solution1 = new ArrayList<>();
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution1.add(new Point(i,j));
            }
        }
       
        ArrayList<Point> solution2 = new ArrayList<>();
        
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution2.add(new Point(i,(j+1)%col));
            }
        }
        
        
        ArrayList<Point> solution3 = new ArrayList<>();
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution3.add(new Point(i,(j+2)%col));
            }
        }
        
        
        ArrayList<Point> solution4 = new ArrayList<>();
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution4.add(new Point((i+1)%fil,j));
            }
        }
        
        ArrayList<Point> solution5 = new ArrayList<>();
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution5.add(new Point((i+2)%fil,j));
            }
        }
        
        ArrayList<Point> solution6 = new ArrayList<>();
        for(int i = 0; i < fil; i++){
            for(int j = 0; j < col; j++){
                solution3.add(new Point((i+3)%fil,j));
            }
        }
        
       
        ArrayList<ArrayList<Point>> all_sol = new ArrayList<>();
        
        switch(tipo){
            case DISK:
                all_sol.add(solution1);
                break;
            case V_SPHERE:
            case V_CYLINDER:
                all_sol.add(solution1);
                all_sol.add(solution2);
                all_sol.add(solution3);
                break;
            case H_SPHERE:
            case H_CYLINDER:
                all_sol.add(solution1);
                all_sol.add(solution5);
                all_sol.add(solution6);
                all_sol.add(solution4);
                break;
        }
        
        return all_sol;
        
        
    }

    public static boolean compareList(List ls1, List ls2) {
        return ls1.toString().contentEquals(ls2.toString());
    }
}
