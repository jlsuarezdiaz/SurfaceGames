/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import surfacegames.GamePanel;
import surfacegames.GameWindow;
import surfacegames.MainWindow;

/**
 *
 * @author jlsuarezdiaz
 */
public class MinesWindow extends GameWindow {

    MainWindow parent;
    
    /**
     * Creates new form MinesWindow2
     */
    public MinesWindow() {
        initComponents();
    }
    
    public MinesWindow(MainWindow g){
        super(g);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mineSweeper21 = new minesweeper.MinesPanel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Buscaminas");

        mineSweeper21.setLayout(new java.awt.BorderLayout());
        getContentPane().add(mineSweeper21, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private minesweeper.MinesPanel mineSweeper21;
    // End of variables declaration//GEN-END:variables

    @Override
    public GamePanel getGamePanel() {
        return mineSweeper21;
    }
}
