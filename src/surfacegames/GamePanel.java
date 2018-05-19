/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surfacegames;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Math.ceil;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static utils.Math.mod;

/**
 *
 * @author jlsuarezdiaz
 */
public abstract class GamePanel extends javax.swing.JPanel{
    
    protected Dimension dim = new Dimension(400,400);
    
    protected Surface surface = Surface.DISK;
    
    protected URL backgroundSound = null;
    protected Clip backgroundClip = null;
    
    protected Map<String, Clip> soundEffects = new HashMap<String,Clip>();

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        setDimension(dim);
    }
    
    public void setSurface(Surface s){
        this.surface = s;
        this.repaint();
    }
    
    public Surface getSurface(){
        return this.surface;
    }
    
    public void setDimension(Dimension d){
        this.dim = d;
        setPreferredSize(new Dimension(dim.width,dim.height+8)); //TODO por alguna razón la ventana se come 8 pixels de la parte de abajo del panel. Intentar averiguar por qué para no tener que hacer esto.
    }
    
    public Dimension getDimension(){
        return this.dim;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        //Definimos colores para degradado (STACKOVERFLOW)
        //TODO++: Añadir lineas para bordes (en este caso con un color fijo y que les de apariencia de borde)
        //TODO: Buscar una combinación de colores buena para los 3 gradientes y que los colores sean lo suficientemente distintos en cada gradiente.
        //TODO--: Buscar un stroke redondeado para que no queden feas las esquinas (?) ver practica 5 o 6 de SM
        Color startColor = Color.RED;
        Color endColor = Color.YELLOW;
        
        Color startColor2 = Color.BLUE;
        Color endColor2 = Color.MAGENTA;
        
        Color startColor3 = Color.GREEN;
        Color endColor3 = Color.CYAN;
        
        Color startColor4 = Color.ORANGE;
        Color endColor4 = new Color(160, 82, 45); // BROWN
        
                
        int w = dim.width;
        int h = dim.height;
        
        int startX, startY, endX, endY;
    
        GradientPaint gradient, gradient2, gradient3, gradient4;

       
        BasicStroke stroke = new BasicStroke(4.0f);
        g2d.setStroke(stroke);
        //int grosor = (int) stroke.getLineWidth();
        int offset = (int) stroke.getLineWidth()/2;
        switch(surface){
            case V_CYLINDER:
                startX = startY = endX = 0;
                endY = h;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, offset, h-offset);
                g2d.drawLine(w-offset,0,w-offset,h-offset);
            break;
            
            case H_CYLINDER:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
            break;
            
            case TORUS:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                
                startX = startY = endX = 0;
                endY = h;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, offset, offset, h-offset);
                g2d.drawLine(w-offset,offset,w-offset,h-offset);    
            break;
            
            case V_SPHERE:
                startX = startY = endX = 0;
                endY = h;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, offset, h-offset);
                g2d.drawLine(w-offset,offset,w-offset,h-offset);
                
                startX = startY = endY = 0;
                endX = w/2;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, offset, w/2, offset);
                
                startY = endY = 0;
                startX = w/2;
                endX = w;
                gradient2 = new GradientPaint(endX, endY, startColor2, startX, startY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w/2, offset, w-offset, offset);
                
                
                
                gradient3 = new GradientPaint(endX, endY, startColor3, startX, startY, endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(w/2,h-offset,w-offset,h-offset);
                
                startX = startY = endY = 0;
                endX = w/2;
                gradient3 = new GradientPaint(startX, startY, startColor3, endX, endY, endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(offset,h-offset,w/2,h-offset);
                
            break;
            
            
            case H_SPHERE:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                startX = startY = endX = 0;
                endY = h/2;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, offset, offset, h/2);
                
                startX = endX = 0;
                startY = h/2;
                endY = h;
                gradient2 = new GradientPaint(endX, endY, startColor2, startX, startY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, h/2, offset, h-offset);
                
                
                
                gradient3 = new GradientPaint(endX, endY, startColor3, startX, startY, endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(w-offset,h/2,w-offset,h-offset);
                
                startX = startY = endX = 0;
                endY = h/2;
                gradient3 = new GradientPaint(startX, startY, startColor3, endX, endY, endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(w-offset,offset,w-offset,h/2);
                
            break;
            
            case TORUS_2:
                startX = startY = endY = 0;
                endX = w/2;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w/2, offset);
                
                startX = endX = w;
                startY = 0;
                endY = h/2;
                gradient = new GradientPaint(endX, endY, startColor, startX, startY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(w-offset, offset, w-offset, h/2);
                
                startX = w/2;
                endX = w;
                startY = endY = 0;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w/2,offset,w-offset,offset);
                
                startX = endX = w;
                startY = h/2;
                endY = h;
                gradient2 = new GradientPaint(endX,endY,startColor2,startX,startY,endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w-offset, h/2, w-offset, h-offset);
                
                startX = w/2;
                endX = w;
                startY = endY = h;
                gradient3 = new GradientPaint(endX,endY,startColor3,startX,startY,endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(w/2,h-offset, w-offset, h-offset);
                
                startX = endX = 0;
                startY = h/2;
                endY = h;
                gradient3 = new GradientPaint(startX,startY,startColor3,endX,endY,endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(offset, h/2, offset, h-offset);
                
                startX = 0;
                endX = w/2;
                startY = endY = h;
                gradient4 = new GradientPaint(endX,endY,startColor4,startX,startY,endColor4);
                g2d.setPaint(gradient4);
                g2d.drawLine(offset, h-offset, w/2, h-offset);
                
                startX = startY = endX = 0;
                endY = w/2;
                gradient4 = new GradientPaint(startX,startY,startColor4,endX,endY,endColor4);
                g2d.setPaint(gradient4);
                g2d.drawLine(offset, offset, offset, h/2);
                
                
                
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
            case TORUS_2:
            {
                float t = ((float)h/w);
                float s = ((float)w/h);
                
                if(y < 0 && x < w/2){
                    psurf = new Point((int)(s*mod(y,h)),h/2 - (int)(t*x) - gy);
                }
                else if(y < 0 && x < w){
                    psurf = new Point((int)(s*mod(y,h)),h - (int)(t*(x-w/2)) - gy);
                }
                else if(x >= w && y < h/2){
                    psurf = new Point(w/2 - (int)(s*y) - gx, (int)(t*mod(x,w)));
                }
                else if(x >= w && y < h){
                    psurf = new Point(w - (int)(s*(y - h/2))- gx, (int)(t*mod(x,w)));
                }
                else if(y >= h && x < w/2){
                    psurf = new Point((int)(s*mod(y,h)), h/2 - (int)(t*x) -gy);
                }
                else if(y >= h && x < w){
                    psurf = new Point((int)(s*mod(y,h)), h - (int)(t*(x-w/2)) - gy);
                }
                else if(x < 0 && y < h/2){
                    psurf = new Point(w/2 - (int)(s*y) -gx, (int)(t*mod(x,w)));
                }
                else if(x < 0 && y < h){
                    psurf = new Point(w - (int)(s*(y - h/2)) - gx, (int)(t*mod(x,w)));
                }
                else{
                    psurf = new Point(p);
                }
                
            }
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
            case TORUS_2:
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
            case TORUS_2:
                return false;
            case DISK:
                return x<0 || x>=w || y<0 || y >= h;
            case V_CYLINDER:
                return y<-1 || y>= h;
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
    
    public Direction getDirectionChange(Point p){
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        switch(surface){
            case DISK:
            case V_CYLINDER:
            case H_CYLINDER:
            case TORUS:
                return Direction.SAME;
            case V_SPHERE:
                if(y >= h){
                    return Direction.UP;
                }
                else if(y < 0){
                    return Direction.DOWN;
                }
                else return Direction.SAME;
            case H_SPHERE:
                if(x >= w){
                    return Direction.LEFT;
                }
                else if(x < 0){
                    return Direction.RIGHT;
                }
                else return Direction.SAME;
            case TORUS_2:
                if(x >= w){
                    return Direction.DOWN;
                }
                else if(x < 0){
                    return Direction.UP;
                }
                else if(y >= h){
                    return Direction.RIGHT;
                }
                else if(y < 0){
                    return Direction.LEFT;
                }
                else return Direction.SAME;
            
        }
        return null;
    }
    
    /**
     * 
     * @param p
     * @return 
     */
    public float getRotationChange(Point p){
        // SENTIDO HORARIO Y SEXAGESIMAL
        int w = dim.width;
        int h = dim.height;
        int x = p.x;
        int y = p.y;
        switch(surface){
            case DISK:
            case V_CYLINDER:
            case H_CYLINDER:
            case TORUS:
                return 0;
            case V_SPHERE:
                if(y >= h || y < 0){
                    return 180;
                }
                else return 0;
            case H_SPHERE:
                if(x >= w || x < 0){
                    return 180;
                }
                else return 0;
            case TORUS_2:
                if(x >= w){
                    return 90;
                }
                else if(x < 0){
                    return 90;
                }
                else if(y >= h){
                    return 270;
                }
                else if(y < 0){
                    return 270;
                }
                else return 0;
            
        }
        return 0;
    }
    
    public abstract Surface[] getAllowedSurfaces();
    
    public abstract boolean isSurfaceChangeAllowedDuringGame();
    
    public abstract void pause();
        
    public void setBackgroundSound(String filename){
        this.backgroundSound = getClass().getResource(filename);
        try{
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(AudioSystem.getAudioInputStream(backgroundSound));
        }
        catch(Exception ex){
                ex.printStackTrace();
        }
    }
    
    public void playBackgroundSound(){
        if(this.backgroundSound != null){
            try{
                
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
                //backgroundClip.close();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void addSoundEffect(String key, String path){
        try{
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(getClass().getResource(path)));
            soundEffects.put(key, c);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void playSoundEffect(String key){
        Clip c = soundEffects.get(key);
        if(c != null){
            c.stop();
            c.setMicrosecondPosition(0);
            c.start();
        }
    }
    
    public void clean(){
        if(backgroundClip != null){
            backgroundClip.close();
        }
        
        for(Clip c : soundEffects.values()){
            c.close();
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
