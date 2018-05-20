/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static utils.Math.mod;

/**
 *
 * @author nuria
 */
class MyButton extends JButton {

    private boolean isLastButton;
    
    private int rotation;
    
    private int id = 0;

    public MyButton() {

        super();
        setId(0);
        initUI();
    }

    public MyButton(Image image) {

        super(new ImageIcon(image));

        initUI();
    }

    private void initUI() {

        isLastButton = false;
        BorderFactory.createLineBorder(Color.gray);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }
    
    

    public void setLastButton() {
        
        isLastButton = true;
    }

    public boolean isLastButton() {

        return isLastButton;
    }
    
    public void rotate(int deg){
        rotation += deg;
        rotation = mod(rotation,360);
        
        ImageIcon i = (ImageIcon)getIcon();
        
        BufferedImage bi = new BufferedImage(
        i.getIconWidth(),
        i.getIconHeight(),
        BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = bi.createGraphics();
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(deg),i.getIconWidth()/2,i.getIconHeight()/2);
        g.setTransform(at);
        i.paintIcon(null, g, 0, 0);
        g.dispose();
        
        setIcon(new ImageIcon(bi));
    }
    
    public int getRotation(){
        return this.rotation;
    }
    
    public void setId(int id){
        this.id = id;
        setToolTipText(Integer.toString(id));
        if(id == 0){
            setToolTipText("Desplaza una pieza aquí.");
        }
    }
    
    public int getId(){
        return id;
    }
}