/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import surfacegames.GamePanel;
import surfacegames.GameWindow;
import surfacegames.MainWindow;

/**
 *
 * @author jlsuarezdiaz
 */
public class SnakeWindow extends GameWindow{

    /**
     * Creates new form SnakeWindow
     */
    public SnakeWindow() {
        super();
        initComponents();
        //setPreferredSize(snakePanel.getDimension());
    }
    
    public SnakeWindow(MainWindow g){
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

        snakePanel = new snake.SnakePanel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Snake");

        javax.swing.GroupLayout snakePanelLayout = new javax.swing.GroupLayout(snakePanel);
        snakePanel.setLayout(snakePanelLayout);
        snakePanelLayout.setHorizontalGroup(
            snakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        snakePanelLayout.setVerticalGroup(
            snakePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        getContentPane().add(snakePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private snake.SnakePanel snakePanel;
    // End of variables declaration//GEN-END:variables

    //@Override
    public GamePanel getGamePanel() {
        return this.snakePanel;
    }
}
