/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surfacegames;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JInternalFrame;
import snake.SnakeWindow;

/**
 *
 * @author jlsuarezdiaz
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }
    
    private void startGame(GameType g){
        JInternalFrame iw = null;
        switch(g){
            case SNAKE:
                iw = new SnakeWindow(this);
                break;
        }
        desktop.add(iw);
        iw.setVisible(true);
    }
    
    public void setView(GamePanel gp){
        switch(gp.getSurface()){
            case DISK:
                this.diskMenuItem.setSelected(true);
                break;
            case V_SPHERE:
                this.vsphereMenuItem.setSelected(true);
                break;
            case H_SPHERE:
                this.hsphereMenuItem.setSelected(true);
                break;
            case V_CYLINDER:
                this.vcylinderMenuItem.setSelected(true);
                break;
            case H_CYLINDER:
                this.hcylinderMenuItem.setSelected(true);
                break;
            case TORUS:
                this.torusMenuItem.setSelected(true);
                break;
        }
        Enumeration<AbstractButton> buttons = surfaceBtGroup.getElements();
        while(buttons.hasMoreElements()){
            AbstractButton b = buttons.nextElement();
            b.setEnabled(false);
        }
        
        for(Surface s: gp.getAllowedSurfaces()){
            switch(s){
                case DISK:
                this.diskMenuItem.setEnabled(true);
                break;
            case V_SPHERE:
                this.vsphereMenuItem.setEnabled(true);
                break;
            case H_SPHERE:
                this.hsphereMenuItem.setEnabled(true);
                break;
            case V_CYLINDER:
                this.vcylinderMenuItem.setEnabled(true);
                break;
            case H_CYLINDER:
                this.hcylinderMenuItem.setEnabled(true);
                break;
            case TORUS:
                this.torusMenuItem.setEnabled(true);
                break;
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

        surfaceBtGroup = new javax.swing.ButtonGroup();
        desktop = new surfacegames.BackgroundDesktop();
        topMenu = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        newGameMenu = new javax.swing.JMenu();
        snakeMenuItem = new javax.swing.JMenuItem();
        surfaceMenu = new javax.swing.JMenu();
        diskMenuItem = new javax.swing.JRadioButtonMenuItem();
        vsphereMenuItem = new javax.swing.JRadioButtonMenuItem();
        hsphereMenuItem = new javax.swing.JRadioButtonMenuItem();
        vcylinderMenuItem = new javax.swing.JRadioButtonMenuItem();
        hcylinderMenuItem = new javax.swing.JRadioButtonMenuItem();
        torusMenuItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        surfaceHelpMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        soundMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Surface Games");
        setPreferredSize(new java.awt.Dimension(1000, 800));

        try{
            desktop.setBackgroundImage(ImageIO.read(new File("src/surfacegames/media/background1.jpg")));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        desktop.setLayout(null);
        getContentPane().add(desktop, java.awt.BorderLayout.CENTER);

        gameMenu.setText("Juego");

        newGameMenu.setText("Nuevo juego");

        snakeMenuItem.setText("Snake");
        snakeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snakeMenuItemActionPerformed(evt);
            }
        });
        newGameMenu.add(snakeMenuItem);

        gameMenu.add(newGameMenu);

        topMenu.add(gameMenu);

        surfaceMenu.setText("Superficie");

        surfaceBtGroup.add(diskMenuItem);
        diskMenuItem.setSelected(true);
        diskMenuItem.setText("Disco");
        diskMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diskMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(diskMenuItem);

        surfaceBtGroup.add(vsphereMenuItem);
        vsphereMenuItem.setText("Esfera (vertical)");
        vsphereMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vsphereMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(vsphereMenuItem);

        surfaceBtGroup.add(hsphereMenuItem);
        hsphereMenuItem.setText("Esfera (horizontal)");
        hsphereMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hsphereMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(hsphereMenuItem);

        surfaceBtGroup.add(vcylinderMenuItem);
        vcylinderMenuItem.setText("Cilindro (vertical)");
        vcylinderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vcylinderMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(vcylinderMenuItem);

        surfaceBtGroup.add(hcylinderMenuItem);
        hcylinderMenuItem.setText("Cilindro (horizontal)");
        hcylinderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hcylinderMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(hcylinderMenuItem);

        surfaceBtGroup.add(torusMenuItem);
        torusMenuItem.setText("Toro");
        torusMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                torusMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(torusMenuItem);
        surfaceMenu.add(jSeparator1);

        surfaceHelpMenuItem.setText("Ayuda");
        surfaceMenu.add(surfaceHelpMenuItem);

        topMenu.add(surfaceMenu);

        optionsMenu.setText("Opciones");

        soundMenuItem.setText("Sonido");
        optionsMenu.add(soundMenuItem);

        topMenu.add(optionsMenu);

        setJMenuBar(topMenu);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void snakeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snakeMenuItemActionPerformed
        startGame(GameType.SNAKE);
    }//GEN-LAST:event_snakeMenuItemActionPerformed

    private void diskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.DISK);
        }
    }//GEN-LAST:event_diskMenuItemActionPerformed

    private void torusMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torusMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.TORUS);
        }
    }//GEN-LAST:event_torusMenuItemActionPerformed

    private void vsphereMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vsphereMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_SPHERE);
        }
    }//GEN-LAST:event_vsphereMenuItemActionPerformed

    private void hsphereMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hsphereMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_SPHERE);
        }
    }//GEN-LAST:event_hsphereMenuItemActionPerformed

    private void vcylinderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vcylinderMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_CYLINDER);
        }
    }//GEN-LAST:event_vcylinderMenuItemActionPerformed

    private void hcylinderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hcylinderMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_CYLINDER);
        }
    }//GEN-LAST:event_hcylinderMenuItemActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private surfacegames.BackgroundDesktop desktop;
    private javax.swing.JRadioButtonMenuItem diskMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JRadioButtonMenuItem hcylinderMenuItem;
    private javax.swing.JRadioButtonMenuItem hsphereMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenu newGameMenu;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem snakeMenuItem;
    private javax.swing.JMenuItem soundMenuItem;
    private javax.swing.ButtonGroup surfaceBtGroup;
    private javax.swing.JMenuItem surfaceHelpMenuItem;
    private javax.swing.JMenu surfaceMenu;
    private javax.swing.JMenuBar topMenu;
    private javax.swing.JRadioButtonMenuItem torusMenuItem;
    private javax.swing.JRadioButtonMenuItem vcylinderMenuItem;
    private javax.swing.JRadioButtonMenuItem vsphereMenuItem;
    // End of variables declaration//GEN-END:variables
}
