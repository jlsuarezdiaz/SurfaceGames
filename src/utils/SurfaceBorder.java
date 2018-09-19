/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Area;
import javax.swing.border.Border;
import surfacegames.Surface;

/**
 *
 * @author jlsuarezdiaz
 */
public class SurfaceBorder implements Border
{
    private Insets margin;
    
    private Surface surface;
    
    private int thickness;

    public SurfaceBorder ( Surface s, int thickness)
    {
        super ();
        this.surface = s;
        this.thickness = thickness;
        margin = new Insets ( thickness, thickness, thickness, thickness );
    }
    
    private void drawBorderVerticalLeft(Graphics2D g2d, int w, int h, int offset){
        g2d.drawLine(offset, offset, offset, h-offset);
    }
    
    private void drawBorderVerticalRight(Graphics2D g2d, int w, int h, int offset){
        g2d.drawLine(w-offset, offset, w-offset, h-offset);
    }
    
    private void drawBorderHorizontalUp(Graphics2D g2d, int w, int h, int offset){
        g2d.drawLine(offset, offset, w-offset, offset);
    }
    
    private void drawBorderHorizontalDown(Graphics2D g2d, int w, int h, int offset){
        g2d.drawLine(offset, h-offset, w-offset, h-offset);
    }

    public void paintBorder ( Component c, Graphics g, int x, int y, int width, int height )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        //g2d.setPaint ( new GradientPaint ( x, y, Color.RED, x + width, y, Color.BLUE ) );

        //Area border = new Area ( new Rectangle ( x, y, width, height ) );
        //border.subtract ( new Area ( new Rectangle ( x + margin.left, y + margin.top,
        //        width - margin.left - margin.right, height - margin.top - margin.bottom ) ) );
        //g2d.fill ( border );
        
        Color startColor = Color.RED;
        Color endColor = Color.YELLOW;
        
        Color startColor2 = Color.BLUE;
        Color endColor2 = Color.MAGENTA;
        
        Color startColor3 = Color.GREEN;
        Color endColor3 = Color.CYAN;
        
        Color startColor4 = Color.ORANGE;
        Color endColor4 = new Color(160, 82, 45); // BROWN
        
        Color borderColor = Color.GRAY;
                
        int w = width;//dim.width;
        int h = height;//dim.height;
        
        int startX, startY, endX, endY;
    
        GradientPaint gradient, gradient2, gradient3, gradient4;

       
        BasicStroke stroke = new BasicStroke(thickness);
        g2d.setStroke(stroke);
        //int grosor = (int) stroke.getLineWidth();
        int offset = 0;// (int) stroke.getLineWidth()/2;
        switch(surface){
            case DISK:
                g2d.setPaint(borderColor);
                drawBorderHorizontalUp(g2d, w, h, offset);
                drawBorderHorizontalDown(g2d, w, h, offset);
                drawBorderVerticalLeft(g2d, w, h, offset);
                drawBorderVerticalRight(g2d, w, h, offset);
                break;
            case V_CYLINDER:
                startX = startY = endX = 0;
                endY = h;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, offset, h-offset);
                g2d.drawLine(w-offset,0,w-offset,h-offset);
                
                g2d.setPaint(borderColor);
                drawBorderHorizontalUp(g2d, w, h, offset);
                drawBorderHorizontalDown(g2d, w, h, offset);
            break;
            
            case H_CYLINDER:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                g2d.setPaint(borderColor);
                drawBorderVerticalLeft(g2d, w, h, offset);
                drawBorderVerticalRight(g2d, w, h, offset);
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
            
            case PROJECTIVE:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                gradient = new GradientPaint(startX, startY, endColor, endX, endY, startColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                
                startX = startY = endX = 0;
                endY = h;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, offset, offset, h-offset);
                gradient2 = new GradientPaint(startX, startY, endColor2, endX, endY, startColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w-offset,offset,w-offset,h-offset); 
            break;
            
            case V_MOBIUS:
                startX = startY = endX = 0;
                endY = h;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, offset, h-offset);
                gradient = new GradientPaint(startX, startY, endColor, endX, endY, startColor);
                g2d.setPaint(gradient);
                g2d.drawLine(w-offset,offset,w-offset,h-offset); 
                
                g2d.setPaint(borderColor);
                drawBorderHorizontalUp(g2d, w, h, offset);
                drawBorderHorizontalDown(g2d, w, h, offset);
            break;
            
            case H_MOBIUS:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                gradient = new GradientPaint(startX, startY, endColor, endX, endY, startColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                g2d.setPaint(borderColor);
                drawBorderVerticalLeft(g2d, w, h, offset);
                drawBorderVerticalRight(g2d, w, h, offset);
            break;
            
            case V_KLEIN:
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
                gradient2 = new GradientPaint(startX, startY, endColor2, endX, endY, startColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w-offset,offset,w-offset,h-offset); 
            break;
            case H_KLEIN:
                startX = startY = endY = 0;
                endX = w;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w-offset, offset);
                gradient = new GradientPaint(startX, startY, endColor, endX, endY, startColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset,h-offset,w-offset,h-offset);
                
                
                startX = startY = endX = 0;
                endY = h;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(offset, offset, offset, h-offset);
                g2d.drawLine(w-offset,offset,w-offset,h-offset); 
            break;
            case PROJECTIVE_4:
                startX = startY = endY = 0;
                endX = w/2;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(offset, offset, w/2, offset);
                
                startX = w/2;
                endX = w;
                startY = endY = 0;
                gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);
                g2d.setPaint(gradient);
                g2d.drawLine(w/2,offset,w-offset,offset);
                
                startX = endX = w;
                startY = 0;
                endY = h/2;
                gradient2 = new GradientPaint(startX, startY, startColor2, endX, endY, endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w-offset, offset, w-offset, h/2);
                
                startX = endX = w;
                startY = h/2;
                endY = h;
                gradient2 = new GradientPaint(startX,startY,startColor2,endX,endY,endColor2);
                g2d.setPaint(gradient2);
                g2d.drawLine(w-offset, h/2, w-offset, h-offset);
                
                startX = w;
                endX = w/2;
                startY = endY = h;
                gradient3 = new GradientPaint(startX,startY,startColor3,endX,endY,endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(w/2,h-offset, w-offset, h-offset);
                
                startX = w/2;
                endX = 0;
                startY = endY = h;
                gradient3 = new GradientPaint(startX,startY,startColor3,endX,endY,endColor3);
                g2d.setPaint(gradient3);
                g2d.drawLine(offset, h-offset, w/2, h-offset);
                
                startX = endX = 0;
                startY = h;
                endY = h/2;
                gradient4 = new GradientPaint(startX,startY,startColor4,endX,endY,endColor4);
                g2d.setPaint(gradient4);
                g2d.drawLine(offset, h/2, offset, h-offset);
                
               
                
                startX = endY = endX = 0;
                startY = w/2;
                gradient4 = new GradientPaint(startX,startY,startColor4,endX,endY,endColor4);
                g2d.setPaint(gradient4);
                g2d.drawLine(offset, offset, offset, h/2);
            break;
        }
    }

    public Insets getBorderInsets ( Component c )
    {
        return margin;
    }

    public boolean isBorderOpaque ()
    {
        return true;
    }

    

 
}
