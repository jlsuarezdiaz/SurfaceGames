/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboard;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;

/**
 *
 * @author jlsuarezdiaz
 */
public class Led7Cell extends javax.swing.JPanel {
    
    private Line2D.Double led7up;
    private Line2D.Double led7middle;
    private Line2D.Double led7down;
    private Line2D.Double led7leftUp;
    private Line2D.Double led7leftDown;
    private Line2D.Double led7rightUp;
    private Line2D.Double led7rightDown;
    
    private Boolean led7upOn = false;
    private Boolean led7middleOn = false;
    private Boolean led7downOn = false;
    private Boolean led7leftUpOn = false;
    private Boolean led7leftDownOn = false;
    private Boolean led7rightUpOn = false;
    private Boolean led7rightDownOn = false;
    
    private double offset = 3.0;
    private double corner = 8.0;
    private float strokeWidth = 6.0f;
    
    private static final Composite ledOff = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f);
    private static final Composite ledOn = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f);
    private Stroke ledStroke = new BasicStroke(strokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND); 
    private static final RenderingHints flatten = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    //private Boolean[] led7on;
    //private Line2D.Double[] led7;
    
    private Color foreground = Color.RED;
    
    /**
     * Creates new form Led7Cell
     */
    public Led7Cell() {
        initComponents();
        //led7 = new Line2D.Double[7];
        //led7on = new Boolean[7]; 
    }

    @Override
    protected void paintComponent(Graphics g) {
        double w = this.getWidth();
        double h = this.getHeight();
        //double offset = 3.0;
        //double corner = 8.0;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        led7up = new Line2D.Double(corner, offset, w-corner, offset);
        led7middle = new Line2D.Double(corner,h/2,w-corner,h/2);
        led7down = new Line2D.Double(corner,h-offset,w-corner,h-offset);
        led7leftUp = new Line2D.Double(offset,corner,offset,h/2-corner/2);
        led7leftDown = new Line2D.Double(offset,h/2+corner/2,offset,h-corner);
        led7rightUp = new Line2D.Double(w-offset,corner,w-offset,h/2-corner/2);
        led7rightDown = new Line2D.Double(w-offset,h/2+corner/2,w-offset,h-corner);
        
        g2d.setPaint(foreground);
        g2d.setStroke(ledStroke);
        g2d.setRenderingHints(flatten);
        
        if(led7upOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7up);
        
        if(led7middleOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7middle);
        
        if(led7downOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7down);
        
        if(led7leftUpOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7leftUp);
        
        if(led7leftDownOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7leftDown);
        
        if(led7rightUpOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7rightUp);
        
        if(led7rightDownOn) g2d.setComposite(ledOn);
        else g2d.setComposite(ledOff);
        g2d.draw(led7rightDown);
        
    }
    
    void clear(){
        led7upOn = false;
        led7middleOn = false;
        led7downOn = false;
        led7leftUpOn = false;
        led7leftDownOn = false;
        led7rightUpOn = false;
        led7rightDownOn = false;
    }
    
    public void turnOff(){
        clear();
        this.repaint();
    }
    
    public void setForegroundColor(Color c){
        this.foreground = c;
    }
    
    public void setNumber(int number){
        turnOff();
        switch(number){
            case 0:
                led7upOn = true;
                led7rightUpOn = true;
                led7rightDownOn = true;
                led7downOn = true;
                led7leftDownOn = true;
                led7leftUpOn = true;
                break;
            case 1:
                led7rightUpOn = true;
                led7rightDownOn = true;
                break;
            case 2:
                led7upOn = true;
                led7rightUpOn = true;
                led7middleOn = true;
                led7leftDownOn = true;
                led7downOn = true;
                break;
            case 3:
                led7upOn = true;
                led7rightUpOn = true;
                led7middleOn = true;
                led7rightDownOn = true;
                led7downOn = true;
                break;
            case 4:
                led7leftUpOn = true;
                led7middleOn = true;
                led7rightUpOn = true;
                led7rightDownOn = true;
                break;
            case 5:
                led7upOn = true;
                led7leftUpOn = true;
                led7middleOn = true;
                led7rightDownOn = true;
                led7downOn = true;
                break;
            case 6:
                led7upOn = true;
                led7leftUpOn = true;
                led7leftDownOn = true;
                led7downOn = true;
                led7rightDownOn = true;
                led7middleOn = true;
                break;
            case 7:
                led7upOn = true;
                led7rightUpOn = true;
                led7rightDownOn = true;
                break;
            case 8:
                led7upOn = true;
                led7middleOn = true;
                led7downOn = true;
                led7leftUpOn = true;
                led7leftDownOn = true;
                led7rightUpOn = true;
                led7rightDownOn = true;
                break;
            case 9:
                led7upOn = true;
                led7middleOn = true;
                led7downOn = true;
                led7leftUpOn = true;
                led7rightUpOn = true;
                led7rightDownOn = true;
                break;
        }
        this.repaint();
    }
    
    public void setHeight(int height){
        int width = (height*2)/3;
        setPreferredSize(new Dimension(width,height));
        offset = height/20.0;
        corner = height/7.5;
        strokeWidth = height/10.0f;
        ledStroke = new BasicStroke(strokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(40, 60));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
