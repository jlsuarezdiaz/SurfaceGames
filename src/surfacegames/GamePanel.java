/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surfacegames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Math.ceil;
import java.util.ArrayList;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static utils.Math.mod;

/**
 *
 * @author jlsuarezdiaz
 */
public abstract class GamePanel extends javax.swing.JPanel implements ActionListener{
    
    protected Dimension dim = new Dimension(400,400);
    
    protected Surface surface = Surface.DISK;
    
    protected File backgroundSound = null;
    protected Clip backgroundClip = null;

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        setPreferredSize(dim);
    }
    
    public void setSurface(Surface s){
        this.surface = s;
    }
    
    public Surface getSurface(){
        return this.surface;
    }
    
    public void setDimension(Dimension d){
        this.dim = d;
        setPreferredSize(dim);
    }
    
    public Dimension getDimension(){
        return this.dim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        
        int w = dim.width;
        int h = dim.height;
        
        
        BasicStroke stroke = new BasicStroke(3.0f);
        g2d.setStroke(stroke);
        
        switch(surface){
            //TODO pintar un borde (línea degradada para identificar los bordes en el rectágulo).
            case V_CYLINDER:
                g2d.setColor(Color.RED);
                g2d.drawLine(0, 0, 0, h);
                g2d.drawLine(w,0,w,h);
            break;
        }
    }
    
    
    
    /**
     * Calcula las coordenadas dentro del panel del punto dado, según la superficie.
     * @param p
     * @param gridSize Tamaño del objeto sobre el que se calculan las coordenadas
     * @return Coordenadas equivalentes del punto en el panel. Si el punto sobrepasa la superficie devuelve coordenadas inválidas
     */
    public Point getCanonicalCoordinates(Point p, Dimension gridSize){
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        int gx = gridSize.width;
        int gy = gridSize.height;
        Point psurf = null;
        
        switch(surface){
            case DISK:
                psurf = new Point(p);
                break;
            case V_SPHERE:
                if(y >= h || y < 0){ //TODO hacerlo con módulos por si el punto se pasa dos veces la frontera
                    psurf = new Point(mod(w-x-gx,w),mod(h-y-gy,h));
                }
                else{
                    psurf = new Point(mod(x,w),mod(y,h));
                }
                break;
            case H_SPHERE:
                if(x >= w || x < 0){ //TODO hacerlo con módulos por si el punto se pasa dos veces la frontera
                    psurf = new Point(mod(w-x-gx,w),mod(h-y-gy,h));
                }
                else{
                    psurf = new Point(mod(x,w),mod(y,h));
                }
                break;
            case V_CYLINDER:
                psurf = new Point(mod(x,w),y);
                break;
            case H_CYLINDER:                
                psurf = new Point(x,mod(y,h));
                break;
            case TORUS:
                psurf = new Point(mod(x,w), mod(y,h));
                break;
              
        }
        
        return psurf;
    }
    
    public Point getCanonicalCoordinates(Point p){
        return getCanonicalCoordinates(p, new Dimension(1,1));
    }
    
    public boolean isOnBorder(Point p, Dimension gridSize){
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        int gx = gridSize.width;
        int gy = gridSize.height;
        
        switch(surface){
            case V_SPHERE:
            case H_SPHERE:
            case TORUS:
                return false;
            case DISK:
                return x==-gx || x==w || y==-gy || y == h;
            case V_CYLINDER:
                return y==-gy || y== h;
            case H_CYLINDER:
                return x==-gx || x==w;
        }
        return false;
    }
    
    public boolean isOnBorder(Point p){
        return isOnBorder(p,new Dimension(1,1));
    }
    
    public boolean isOnBorderOrBeyond(Point p){
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        
        switch(surface){
            case V_SPHERE:
            case H_SPHERE:
            case TORUS:
                return false;
            case DISK:
                return x<0 || x>=w || y<0 || y >= h;
            case V_CYLINDER:
                return y<1 || y>= h;
            case H_CYLINDER:
                return x<-1 || x>=w;
        }
        return false;
    }
    
    public boolean isOnOrientationChange(Point p){
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        switch(surface){
            case DISK:
            case V_CYLINDER:
            case H_CYLINDER:
            case TORUS:
                return false;
            case V_SPHERE:
                return y >= h || y < 0;
            case H_SPHERE:
                return x >= w || x < 0;
            
        }
        return false;
    }
    
    public abstract Surface[] getAllowedSurfaces();
    
    public abstract void pause();
        
    public void setBackgroundSound(String filename){
        this.backgroundSound = new File(filename);
    }
    
    public void playBackgroundSound(){
        if(this.backgroundSound != null){
            try{
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(AudioSystem.getAudioInputStream(backgroundSound));
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void stopBackgroundSound(){
        if(backgroundClip != null){
            try{
                backgroundClip.stop();
                backgroundClip.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
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
