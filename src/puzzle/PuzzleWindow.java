/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.awt.Color;
import surfacegames.GamePanel;
import surfacegames.GameWindow;
import surfacegames.MainWindow;

/**
 *
 * @author jlsuarezdiaz
 */
public class PuzzleWindow extends GameWindow {

    /**
     * Creates new form PuzzleWindow
     */
    public PuzzleWindow() {
        initComponents();
        puzzlePanel.setScore(scoreboard);
        puzzlePanel.setBorderThickness(8);
    }
    
    public PuzzleWindow(MainWindow g){
        super(g);
        initComponents();
        puzzlePanel.setScore(scoreboard);
        puzzlePanel.setBorderThickness(8);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scoreboard = new scoreboard.Scoreboard(4);
        puzzlePanel = new puzzle.PuzzlePanel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Puzzle");

        jPanel1.setLayout(new java.awt.BorderLayout());

        scoreboard.setForegroundColor(Color.WHITE);
        jPanel1.add(scoreboard, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout snakePanelLayout = new javax.swing.GroupLayout(puzzlePanel);
        puzzlePanel.setLayout(snakePanelLayout);
        snakePanelLayout.setHorizontalGroup(
            snakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        snakePanelLayout.setVerticalGroup(
            snakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        jPanel1.add(puzzlePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private puzzle.PuzzlePanel puzzlePanel;
    private scoreboard.Scoreboard scoreboard;
    // End of variables declaration//GEN-END:variables

    @Override
    public GamePanel getGamePanel() {
        return this.puzzlePanel;
    }
}
