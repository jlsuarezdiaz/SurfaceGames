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
    
    private List<MyButton> buttons;
    
    private final int NUMBER_OF_BUTTONS = 12;
    private final int DESIRED_WIDTH = 300;

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
    
  
    private void initUI() {
        buttons = new ArrayList<>();

        panel = new JPanel();
        panel.setSize(this.dim);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        panel.setLayout(new GridLayout(4, 3, 0, 0));
        
        int desired_width = this.dim.width;

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

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 3; j++) {

                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4,
                                (width / 3), height / 4)));
                
                
                MyButton button = new MyButton(image);
                button.putClientProperty("position", new Point(i, (j+2)%3));

                if (i == 3 && j == 2) {
                    lastButton = new MyButton();
                    lastButton.setBorderPainted(true);
                    lastButton.setContentAreaFilled(true);
                    lastButton.setLastButton();
                    lastButton.putClientProperty("position", new Point(i, (j+2)%3));
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
               return (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - 3 == lidx) || (bidx + 3 == lidx);
            
            case V_CYLINDER:
            case V_SPHERE:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - 3 == lidx) || (bidx + 3 == lidx);
                lateral = (bidx%3 == 0 && lidx == bidx + 2) || (lidx%3 == 0 && bidx == lidx + 2);
                return(disco||lateral);
            
            case H_CYLINDER:
            case H_SPHERE:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - 3 == lidx) || (bidx + 3 == lidx);
                bases = (bidx < 3 && lidx == bidx + 9) || (lidx < 3  && bidx == lidx + 9);
                return(disco||bases);
                
            case TORUS:
                disco = (bidx - 1 == lidx) || (bidx + 1 == lidx)|| (bidx - 3 == lidx) || (bidx + 3 == lidx);
                lateral = (bidx%3 == 0 && lidx == bidx + 2) || (lidx%3 == 0 && bidx == lidx + 2);
                bases = (bidx < 3 && lidx == bidx + 9) || (lidx < 3  && bidx == lidx + 9);
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
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution1.add(new Point(i,j));
            }
        }
       
        
        ArrayList<Point> solution2 = new ArrayList<>();
        
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution2.add(new Point(i,(j+1)%3));
            }
        }
        
        
        ArrayList<Point> solution3 = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution3.add(new Point(i,(j+2)%3));
            }
        }
        
        
        ArrayList<Point> solution4 = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution4.add(new Point((i+1)%4,j));
            }
        }
        
        ArrayList<Point> solution5 = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution5.add(new Point((i+2)%4,j));
            }
        }
        
        ArrayList<Point> solution6 = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                solution3.add(new Point((i+3)%4,j));
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
