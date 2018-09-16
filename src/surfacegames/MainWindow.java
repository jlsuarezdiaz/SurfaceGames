/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surfacegames;

import battleship.Gui;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButtonMenuItem;
import puzzle.PuzzleWindow;
import minesweeper.MinesWindow;
import snake.SnakeWindow;
import static surfacegames.GameType.BATTLESHIP;

/**
 *
 * @author jlsuarezdiaz
 */
public class MainWindow extends javax.swing.JFrame {
    
    private Surface selectedSurface = Surface.DISK;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
    }
    
    private void startGame(GameType g){
        GameWindow iw = null;
        switch(g){
            case SNAKE:
                iw = new SnakeWindow(this);
                break;
            case PUZZLE:
                iw = new PuzzleWindow(this);
                break;
            case MINESWEEPER:
                iw = new MinesWindow(this);
                break;
            case BATTLESHIP:
                Gui.setSurface(selectedSurface);
                Gui.main(null);
                break;
        }
        if (g != BATTLESHIP){
            if(Arrays.asList(iw.getGamePanel().getAllowedSurfaces()).contains(selectedSurface)){
                iw.getGamePanel().setSurface(selectedSurface);
            }
            else{
                iw.getGamePanel().setSurface(iw.getGamePanel().getAllowedSurfaces()[0]);
                setView(iw.getGamePanel());
            }
            desktop.add(iw);
            iw.setVisible(true);
        }
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
            case TORUS_2:
                this.torus2MenuItem.setSelected(true);
                break;
            case PROJECTIVE:
                this.projectiveMenuItem.setSelected(true);
                break;
            case V_MOBIUS:
                this.vMobiusMenuItem.setSelected(true);
                break;
            case H_MOBIUS:
                this.hMobiusMenuItem.setSelected(true);
                break;
            case V_KLEIN:
                this.vKleinMenuItem.setSelected(true);
                break;
            case H_KLEIN:
                this.hKleinMenuItem.setSelected(true);
                break;
            case PROJECTIVE_4:
                this.projective4MenuItem.setSelected(true);
                break;
        }
        Enumeration<AbstractButton> buttons = surfaceBtGroup.getElements();
        while(buttons.hasMoreElements()){
            AbstractButton b = buttons.nextElement();
            b.setEnabled(false);
        }
        
        if(gp.isSurfaceChangeAllowedDuringGame()){
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
                case TORUS_2:
                    this.torus2MenuItem.setEnabled(true);
                    break;
                case PROJECTIVE:
                    this.projectiveMenuItem.setEnabled(true);
                    break;
                case V_KLEIN:
                    this.vKleinMenuItem.setEnabled(true);
                    break;
                case H_KLEIN:
                    this.hKleinMenuItem.setEnabled(true);
                    break;
                case V_MOBIUS:
                    this.vMobiusMenuItem.setEnabled(true);
                    break;
                case H_MOBIUS:
                    this.hMobiusMenuItem.setEnabled(true);
                    break;
                case PROJECTIVE_4:
                    this.projective4MenuItem.setEnabled(true);
                    break;
                }
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
        puzzleMenuItem = new javax.swing.JMenuItem();
        minesweeperMenuItem = new javax.swing.JMenuItem();
        battleshipMenuItem = new javax.swing.JMenuItem();
        surfaceMenu = new javax.swing.JMenu();
        diskMenuItem = new javax.swing.JRadioButtonMenuItem();
        vsphereMenuItem = new javax.swing.JRadioButtonMenuItem();
        hsphereMenuItem = new javax.swing.JRadioButtonMenuItem();
        vcylinderMenuItem = new javax.swing.JRadioButtonMenuItem();
        hcylinderMenuItem = new javax.swing.JRadioButtonMenuItem();
        torusMenuItem = new javax.swing.JRadioButtonMenuItem();
        torus2MenuItem = new javax.swing.JRadioButtonMenuItem();
        projectiveMenuItem = new javax.swing.JRadioButtonMenuItem();
        vMobiusMenuItem = new javax.swing.JRadioButtonMenuItem();
        hMobiusMenuItem = new javax.swing.JRadioButtonMenuItem();
        vKleinMenuItem = new javax.swing.JRadioButtonMenuItem();
        hKleinMenuItem = new javax.swing.JRadioButtonMenuItem();
        projective4MenuItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        surfaceHelpMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        soundMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Surface Games");

        try{
            desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/fondo.jpg")));
            desktop.setPreferredSize(new java.awt.Dimension(800, 600));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
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

        puzzleMenuItem.setText("Puzzle");
        puzzleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                puzzleMenuItemActionPerformed(evt);
            }
        });
        newGameMenu.add(puzzleMenuItem);

        minesweeperMenuItem.setText("Buscaminas");
        minesweeperMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minesweeperMenuItemActionPerformed(evt);
            }
        });
        newGameMenu.add(minesweeperMenuItem);

        battleshipMenuItem.setText("Battleship");
        battleshipMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                battleshipMenuItemActionPerformed(evt);
            }
        });
        newGameMenu.add(battleshipMenuItem);

        gameMenu.add(newGameMenu);

        topMenu.add(gameMenu);

        surfaceMenu.setText("Superficie");
        surfaceMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                surfaceMenuMenuSelected(evt);
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
        });

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

        surfaceBtGroup.add(torus2MenuItem);
        torus2MenuItem.setText("2-Toro");
        torus2MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                torus2MenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(torus2MenuItem);

        surfaceBtGroup.add(projectiveMenuItem);
        projectiveMenuItem.setText("Plano proyectivo");
        projectiveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectiveMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(projectiveMenuItem);

        surfaceBtGroup.add(vMobiusMenuItem);
        vMobiusMenuItem.setText("Cinta de Möbius (vertical)");
        vMobiusMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vMobiusMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(vMobiusMenuItem);

        surfaceBtGroup.add(hMobiusMenuItem);
        hMobiusMenuItem.setText("Cinta de Möbius (horizontal)");
        hMobiusMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hMobiusMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(hMobiusMenuItem);

        surfaceBtGroup.add(vKleinMenuItem);
        vKleinMenuItem.setText("Botella de Klein (vertical)");
        vKleinMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vKleinMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(vKleinMenuItem);

        surfaceBtGroup.add(hKleinMenuItem);
        hKleinMenuItem.setText("Botella de Klein (horizontal)");
        hKleinMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hKleinMenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(hKleinMenuItem);

        surfaceBtGroup.add(projective4MenuItem);
        projective4MenuItem.setText("4 proyectivos");
        projective4MenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projective4MenuItemActionPerformed(evt);
            }
        });
        surfaceMenu.add(projective4MenuItem);
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
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/disco.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        startGame(GameType.SNAKE);
    }//GEN-LAST:event_snakeMenuItemActionPerformed

    private void diskMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.DISK;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/disco.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.DISK);
        }
    }//GEN-LAST:event_diskMenuItemActionPerformed

    private void torusMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torusMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.TORUS;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/donut.png")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.TORUS);
            
        }
    }//GEN-LAST:event_torusMenuItemActionPerformed

    private void vsphereMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vsphereMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.V_SPHERE;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/esfera.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_SPHERE);
        }
    }//GEN-LAST:event_vsphereMenuItemActionPerformed

    private void hsphereMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hsphereMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.H_SPHERE;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/esfera.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_SPHERE);
        }
    }//GEN-LAST:event_hsphereMenuItemActionPerformed

    private void vcylinderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vcylinderMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.V_CYLINDER;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/cilindro.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_CYLINDER);
        }
    }//GEN-LAST:event_vcylinderMenuItemActionPerformed

    private void hcylinderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hcylinderMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.H_CYLINDER;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/cilindro_h_1.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_CYLINDER);
        }
    }//GEN-LAST:event_hcylinderMenuItemActionPerformed

    private void puzzleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_puzzleMenuItemActionPerformed
        // TODO add your handling code here:
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/disco.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        startGame(GameType.PUZZLE);
    }//GEN-LAST:event_puzzleMenuItemActionPerformed

    private void minesweeperMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minesweeperMenuItemActionPerformed
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/disco.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        startGame(GameType.MINESWEEPER);
    }//GEN-LAST:event_minesweeperMenuItemActionPerformed

    private void battleshipMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_battleshipMenuItemActionPerformed
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/disco.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        startGame(GameType.BATTLESHIP);
    }//GEN-LAST:event_battleshipMenuItemActionPerformed

    private void surfaceMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_surfaceMenuMenuSelected
        if(desktop.getSelectedFrame() == null){
            Enumeration<AbstractButton> buttons = surfaceBtGroup.getElements();
            while(buttons.hasMoreElements()){
                AbstractButton b = buttons.nextElement();
                b.setEnabled(true);
            }
        }
    }//GEN-LAST:event_surfaceMenuMenuSelected

    private void torus2MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torus2MenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.TORUS_2;
        try{
            this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg")));
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.TORUS_2);
        }
    }//GEN-LAST:event_torus2MenuItemActionPerformed

    private void vMobiusMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vMobiusMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.V_MOBIUS;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); //TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_MOBIUS);
        }
    }//GEN-LAST:event_vMobiusMenuItemActionPerformed

    private void hMobiusMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hMobiusMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.H_MOBIUS;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); // TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_MOBIUS);
        }
    }//GEN-LAST:event_hMobiusMenuItemActionPerformed

    private void projectiveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectiveMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.PROJECTIVE;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); //TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.PROJECTIVE);
        }
    }//GEN-LAST:event_projectiveMenuItemActionPerformed

    private void vKleinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vKleinMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.V_KLEIN;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); //TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.V_KLEIN);
        }
    }//GEN-LAST:event_vKleinMenuItemActionPerformed

    private void hKleinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hKleinMenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.H_KLEIN;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); //TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.H_KLEIN);
        }
    }//GEN-LAST:event_hKleinMenuItemActionPerformed

    private void projective4MenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projective4MenuItemActionPerformed
        JInternalFrame iw = desktop.getSelectedFrame();
        selectedSurface = Surface.PROJECTIVE_4;
        try{
            //this.desktop.setBackgroundImage(ImageIO.read(getClass().getResource("/surfacegames/media/ntorus.jpg"))); //TODO
            repaint();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(iw != null){
            ((GameWindow)iw).getGamePanel().setSurface(Surface.PROJECTIVE_4);
        }
    }//GEN-LAST:event_projective4MenuItemActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem battleshipMenuItem;
    private surfacegames.BackgroundDesktop desktop;
    private javax.swing.JRadioButtonMenuItem diskMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JRadioButtonMenuItem hKleinMenuItem;
    private javax.swing.JRadioButtonMenuItem hMobiusMenuItem;
    private javax.swing.JRadioButtonMenuItem hcylinderMenuItem;
    private javax.swing.JRadioButtonMenuItem hsphereMenuItem;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem minesweeperMenuItem;
    private javax.swing.JMenu newGameMenu;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JRadioButtonMenuItem projective4MenuItem;
    private javax.swing.JRadioButtonMenuItem projectiveMenuItem;
    private javax.swing.JMenuItem puzzleMenuItem;
    private javax.swing.JMenuItem snakeMenuItem;
    private javax.swing.JMenuItem soundMenuItem;
    private javax.swing.ButtonGroup surfaceBtGroup;
    private javax.swing.JMenuItem surfaceHelpMenuItem;
    private javax.swing.JMenu surfaceMenu;
    private javax.swing.JMenuBar topMenu;
    private javax.swing.JRadioButtonMenuItem torus2MenuItem;
    private javax.swing.JRadioButtonMenuItem torusMenuItem;
    private javax.swing.JRadioButtonMenuItem vKleinMenuItem;
    private javax.swing.JRadioButtonMenuItem vMobiusMenuItem;
    private javax.swing.JRadioButtonMenuItem vcylinderMenuItem;
    private javax.swing.JRadioButtonMenuItem vsphereMenuItem;
    // End of variables declaration//GEN-END:variables
}
