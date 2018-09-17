/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboard;

import java.awt.Color;
import java.util.ArrayList;
import surfacegames.Scoreable;

/**
 *
 * @author jlsuarezdiaz
 */
public class Scoreboard extends javax.swing.JPanel implements Scoreable{

    private ArrayList<Led7Cell> digits;
    
    private Integer value = null;
    
    private Color foreground = Color.RED;
    /**
     * Creates new form Scoreboard
     */
    public Scoreboard() {
        initComponents();
        digits = new ArrayList<Led7Cell>();
    }
    
    public Scoreboard(int num_digits){
        initComponents();
        digits = new ArrayList<Led7Cell>();
        for(int i = 0; i < num_digits; i++){
            digits.add(new Led7Cell());
            digits.get(i).setForegroundColor(foreground);
            //this.add(digits.get(i));
        }
        for(int i = digits.size() -1; i >= 0; i--){
            this.add(digits.get(i));
        }
        
    }
    
    private void clear(){
        for(int i = 0; i < digits.size(); i++){
            digits.get(i).clear();
        }
    }
    
    public void setForegroundColor(Color c){
        this.foreground = c;
        for(int i = 0; i < digits.size(); i++){
            digits.get(i).setForegroundColor(c);
        }
    }
    
    public void setScore(int value){
        this.value = value;
        clear();
   
        if(value == 0 && !digits.isEmpty()){
            digits.get(0).setNumber(0);
            this.repaint();
            return;
        }
        for(int i = 0; i < digits.size(); i++){
            digits.get(i).setNumber(value % 10);
            value = value / 10;
            if(value == 0) break;
        }
        this.repaint();
    }
    
    public void turnOff(){
        clear();
        this.repaint();
    }
    
    public void setHeight(int height){
        for(int i = 0; i < digits.size(); i++){
            digits.get(i).setHeight(height);
        }
    }
    
    public int getScore(){
        return (this.value==null)?0:this.value;
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
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT);
        flowLayout1.setAlignOnBaseline(true);
        setLayout(flowLayout1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}