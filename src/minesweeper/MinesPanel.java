/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import surfacegames.GamePanel;
import surfacegames.Surface;

/**
 *
 * @author Javier
 */
public class MinesPanel extends GamePanel {
    private static final Surface[] allowedSurfaces = {Surface.DISK,Surface.V_SPHERE,Surface.H_SPHERE,Surface.V_CYLINDER,Surface.H_CYLINDER,Surface.TORUS};
    ImageIcon[] ic = new ImageIcon[14];
    int fw, fh, blockr, blockc, var1, var2, num_of_mine, detectedmine = 0, savedlevel = 1,
            savedblockr, savedblockc, savednum_of_mine = 10;

    int[] r = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] c = {-1, 0, 1, 1, 1, 0, -1, -1};
    JButton[][] blocks;
    int[][] countmine;
    int[][] colour;
    Point p;
    JButton reset = new JButton("");
    Random ranr = new Random();
    Random ranc = new Random();
    boolean check = true, starttime = false;
    Point framelocation;
    Stopwatch sw;
    MouseHendeler mh;
    JTextField tf_mine, tf_time;
        
    public MinesPanel(){
        initComponents();
        setIconos(); // cogemos los iconos
        setPanel(1, 0, 0, 0); // aqui mezcla cosas de GUI que podemos hacer nosotros en el panel con cosas de codigo
        setManue();
        
        sw = new Stopwatch();

        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    sw.stop();
                    setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                } catch (Exception ex) {
                    setPanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                }
                reset();

            }
        });
                
    }
    
    @Override
    public boolean isSurfaceChangeAllowedDuringGame(){
        return false;
    }
 
    public void setManue() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("GAME");

        JMenuItem menuitem = new JMenuItem("new game");
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Begineer");
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
        final JCheckBoxMenuItem expart = new JCheckBoxMenuItem("Expart");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom");
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenu help = new JMenu("Help");
        final JMenuItem helpitem = new JMenuItem("Help");

        ButtonGroup status = new ButtonGroup();

        menuitem.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        //panelb.removeAll();
                        //reset();
                        setPanel(1, 0, 0, 0);
                        //panelb.revalidate();
                        //panelb.repaint();
                    }
                });

        beginner.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        getPanelMinas().removeAll();
                        reset();
                        setPanel(1, 0, 0, 0);
                        getPanelMinas().revalidate();
                        getPanelMinas().repaint();
                        beginner.setSelected(true);
                        savedlevel = 1;
                    }
                });
        intermediate.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        getPanelMinas().removeAll();                        
                        reset();
                        setPanel(2, 0, 0, 0);
                        getPanelMinas().revalidate();
                        getPanelMinas().repaint();
                        intermediate.setSelected(true);
                        savedlevel = 2;
                    }
                });
        expart.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        getPanelMinas().removeAll();
                        reset();
                        setPanel(3, 0, 0, 0);
                        getPanelMinas().revalidate();
                        getPanelMinas().repaint();
                        expart.setSelected(true);
                        savedlevel = 3;
                    }
                });

        custom.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //panelb.removeAll();
                        Customizetion cus = new Customizetion();
                        reset();
                        getPanelMinas().revalidate();
                        getPanelMinas().repaint();

                        //Minesweeper ob=new Minesweeper(4);
                        custom.setSelected(true);
                        savedlevel = 4;
                    }
                });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        helpitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "instruction");

            }
        });

        //setJMenuBar(bar); da error y no sé por qué es.

        status.add(beginner);
        status.add(intermediate);
        status.add(expart);
        status.add(custom);

        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(intermediate);
        game.add(expart);
        game.add(custom);
        game.addSeparator();
        game.add(exit);
        help.add(helpitem);

        bar.add(game);
        bar.add(help);

    }
 
    public void reset() {
        check = true;
        starttime = false;
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                colour[i][j] = 'w';
            }
        }
    }    
    
    public void setIconos() {
        String name;

        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            ic[i] = new ImageIcon(name);
        }
        ic[9] = new ImageIcon("mine.gif");
        ic[10] = new ImageIcon("flag.gif");
        ic[11] = new ImageIcon("new game.gif");
        ic[12] = new ImageIcon("crape.gif");
    }
    
    public void setPanel(int level, int setr, int setc, int setm) {
        if (level == 1) {
            fw = 200;
            fh = 300;
            blockr = 10;
            blockc = 10;
            num_of_mine = 10;
        } else if (level == 2) {
            fw = 320;
            fh = 416;
            blockr = 16;
            blockc = 16;
            num_of_mine = 70;
        } else if (level == 3) {
            fw = 400;
            fh = 520;
            blockr = 20;
            blockc = 20;
            num_of_mine = 150;
        } else if (level == 4) {
            fw = (20 * setc);
            fh = (24 * setr);
            blockr = setr;
            blockc = setc;
            num_of_mine = setm;
        }

        savedblockr = blockr;
        savedblockc = blockc;
        savednum_of_mine = num_of_mine;

        setSize(fw, fh);
        //setResizable(false);
        detectedmine = num_of_mine;
        p = this.getLocation();

        blocks = new JButton[blockr][blockc];
        countmine = new int[blockr][blockc];
        colour = new int[blockr][blockc];
        mh = new MouseHendeler();

        //getContentPane().removeAll();
        this.panelMinas.removeAll();

        tf_mine = new JTextField("" + num_of_mine, 3);
        tf_mine.setEditable(false);
        tf_mine.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_mine.setBackground(Color.BLACK);
        tf_mine.setForeground(Color.RED);
        tf_mine.setBorder(BorderFactory.createLoweredBevelBorder());
        tf_time = new JTextField("000", 3);
        tf_time.setEditable(false);
        tf_time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_time.setBackground(Color.BLACK);
        tf_time.setForeground(Color.RED);
        tf_time.setBorder(BorderFactory.createLoweredBevelBorder());
        reset.setIcon(ic[11]);
        reset.setBorder(BorderFactory.createLoweredBevelBorder());

        this.panelTimer.removeAll();
        this.panelTimer.setLayout(new BorderLayout());
        this.panelTimer.add(tf_mine, BorderLayout.WEST);
        this.panelTimer.add(reset, BorderLayout.CENTER);
        this.panelTimer.add(tf_time, BorderLayout.EAST);
        this.panelTimer.setBorder(BorderFactory.createLoweredBevelBorder());

        this.panelMinas.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLoweredBevelBorder()));
        this.panelMinas.setPreferredSize(new Dimension(fw, fh));
        this.panelMinas.setLayout(new GridLayout(0, blockc));
//        this.panelMinas.addContainerListener(this); POR QUE?

        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                blocks[i][j] = new JButton("");

                //blocks[i][j].addActionListener(this);
                blocks[i][j].addMouseListener(mh);

                this.panelMinas.add(blocks[i][j]);

            }
        }
        reset();

        this.panelMinas.revalidate();
        this.panelMinas.repaint();       
        //getContentPane().addContainerListener(this); ESTO FALTA POR AÑADIRLO
  
        setVisible(true);
    }
    
     
    public void winner() {
        int q = 0;
        for (int k = 0; k < blockr; k++) {
            for (int l = 0; l < blockc; l++) {
                if (colour[k][l] == 'w') {
                    q = 1;
                }
            }
        }


        if (q == 0) {
            //panelb.hide();
            for (int k = 0; k < blockr; k++) {
                for (int l = 0; l < blockc; l++) {
                    blocks[k][l].removeMouseListener(mh);
                }
            }

            sw.stop();
            JOptionPane.showMessageDialog(this, "u R a lover");
        }
    }
 
    public void showValue(MouseEvent e) {
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {

                if (e.getSource() == blocks[i][j]) {
                    if (e.isMetaDown() == false) {
                        if (blocks[i][j].getIcon() == ic[10]) {
                            if (detectedmine < num_of_mine) {
                                detectedmine++;
                            }
                            tf_mine.setText("" + detectedmine);
                        }

                        if (countmine[i][j] == -1) {
                            for (int k = 0; k < blockr; k++) {
                                for (int l = 0; l < blockc; l++) {
                                    if (countmine[k][l] == -1) {

                                        //blocks[k][l].setText("X");
                                        blocks[k][l].setIcon(ic[9]);
                                        //blocks[k][l].setBackground(Color.BLUE);
                                        //blocks[k][l].setFont(new Font("",Font.CENTER_BASELINE,8));
                                        blocks[k][l].removeMouseListener(mh);
                                    }
                                    blocks[k][l].removeMouseListener(mh);
                                }
                            }
                            sw.stop();
                            reset.setIcon(ic[12]);
                            JOptionPane.showMessageDialog(null, "sorry u R  loser");
                        } else if (countmine[i][j] == 0) {
                            dfs(i, j);
                        } else {
                            blocks[i][j].setIcon(ic[countmine[i][j]]);
                            //blocks[i][j].setText(""+countmine[i][j]);
                            //blocks[i][j].setBackground(Color.pink);
                            //blocks[i][j].setFont(new Font("",Font.PLAIN,8));
                            colour[i][j] = 'b';
                            //blocks[i][j].setBackground(Color.pink);
                            break;
                        }
                    } else {
                        if (detectedmine != 0) {
                            if (blocks[i][j].getIcon() == null) {
                                detectedmine--;
                                blocks[i][j].setIcon(ic[10]);
                            }
                            tf_mine.setText("" + detectedmine);
                        }


                    }
                }

            }
        }

    }

    public void calculation() {
        int row, column;

        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                int value = 0;
                int R, C;
                row = i;
                column = j;
                if (countmine[row][column] != -1) {
                    for (int k = 0; k < 8; k++) {
                        R = row + r[k];
                        C = column + c[k];

                        if (R >= 0 && C >= 0 && R < blockr && C < blockc) {
                            if (countmine[R][C] == -1) {
                                value++;
                            }

                        }

                    }
                    countmine[row][column] = value;

                }
            }
        }
    }
        
    public void dfs(int row, int col) {

        int R, C;
        colour[row][col] = 'b';

        blocks[row][col].setBackground(Color.GRAY);

        blocks[row][col].setIcon(ic[countmine[row][col]]);
        //blocks[row][col].setText("");
        for (int i = 0; i < 8; i++) {
            R = row + r[i];
            C = col + c[i];
            if (R >= 0 && R < blockr && C >= 0 && C < blockc && colour[R][C] == 'w') {
                if (countmine[R][C] == 0) {
                    dfs(R, C);
                } else {
                    blocks[R][C].setIcon(ic[countmine[R][C]]);
                    colour[R][C] = 'b';

                }
            }


        }
    }
    
    public void setMine() {
        int row = 0, col = 0;
        Boolean[][] flag = new Boolean[blockr][blockc];


        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                flag[i][j] = true;
                countmine[i][j] = 0;
            }
        }

        flag[var1][var2] = false;
        colour[var1][var2] = 'b';

        for (int i = 0; i < num_of_mine; i++) {
            row = ranr.nextInt(blockr);
            col = ranc.nextInt(blockc);

            if (flag[row][col] == true) {

                countmine[row][col] = -1;
                colour[row][col] = 'b';
                flag[row][col] = false;
            } else {
                i--;
            }
        }
    }
       
    @Override
    public Surface[] getAllowedSurfaces() {
        return allowedSurfaces;
    }

    @Override
    public void pause() {
        
    }


    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    
    
    class MouseHendeler extends MouseAdapter {

        public void mouseClicked(MouseEvent me) {
            if (check == true) {
                for (int i = 0; i < blockr; i++) {
                    for (int j = 0; j < blockc; j++) {
                        if (me.getSource() == blocks[i][j]) {
                            var1 = i;
                            var2 = j;
                            i = blockr;
                            break;
                        }
                    }
                }

                setMine();
                calculation();
                check = false;

            }

            showValue(me);
            winner();

            if (starttime == false) {
                sw.Start();
                starttime = true;
            }

        }
    }
  
    public class Stopwatch extends JFrame implements Runnable {

        long startTime;
        //final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("mm : ss :SSS");
        //final JButton startStopButton= new JButton("Start/stop");
        Thread updater;
        boolean isRunning = false;
        long a = 0;
        Runnable displayUpdater = new Runnable() {

            public void run() {
                displayElapsedTime(a);
                a++;
            }
        };

        public void stop() {
            long elapsed = a;
            isRunning = false;
            try {
                updater.join();
            } catch (InterruptedException ie) {
            }
            displayElapsedTime(elapsed);
            a = 0;
        }

        private void displayElapsedTime(long elapsedTime) {

            if (elapsedTime >= 0 && elapsedTime < 9) {
                tf_time.setText("00" + elapsedTime);
            } else if (elapsedTime > 9 && elapsedTime < 99) {
                tf_time.setText("0" + elapsedTime);
            } else if (elapsedTime > 99 && elapsedTime < 999) {
                tf_time.setText("" + elapsedTime);
            }
        }

        public void run() {
            try {
                while (isRunning) {
                    SwingUtilities.invokeAndWait(displayUpdater);
                    Thread.sleep(1000);
                }
            } catch (java.lang.reflect.InvocationTargetException ite) {
                ite.printStackTrace(System.err);
            } catch (InterruptedException ie) {
            }
        }

        public void Start() {
            startTime = System.currentTimeMillis();
            isRunning = true;
            updater = new Thread(this);
            updater.start();
        }
    }

    class Customizetion extends JFrame implements ActionListener {

        JTextField t1, t2, t3;
        JLabel lb1, lb2, lb3;
        JButton b1, b2;
        int cr, cc, cm, actionc = 0;

        Customizetion() {
            super("CUSTOMIZETION");
            setSize(180, 200);
            setResizable(false);
            setLocation(p);

            t1 = new JTextField();
            t2 = new JTextField();
            t3 = new JTextField();

            b1 = new JButton("OK");
            b2 = new JButton("Cencel");

            b1.addActionListener(this);
            b2.addActionListener(this);

            lb1 = new JLabel("Row");
            lb2 = new JLabel("Column");
            lb3 = new JLabel("mine");

            getContentPane().setLayout(new GridLayout(0, 2));

            getContentPane().add(lb1);
            getContentPane().add(t1);
            getContentPane().add(lb2);
            getContentPane().add(t2);
            getContentPane().add(lb3);
            getContentPane().add(t3);

            getContentPane().add(b1);
            getContentPane().add(b2);

            show();
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                try {
                    cr = Integer.parseInt(t1.getText());
                    cc = Integer.parseInt(t2.getText());
                    cm = Integer.parseInt(t3.getText());
                    //Minesweeper ms=new Minesweeper();
                    setPanel(4, row(), column(), mine());
                    dispose();
                } catch (Exception any) {
                    JOptionPane.showMessageDialog(this, "Wrong");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                }
                //Show_rcm();
            }

            if (e.getSource() == b2) {
                dispose();
            }
        }

        public int row() {
            if (cr > 30) {
                return 30;
            } else if (cr < 10) {
                return 10;
            } else {
                return cr;
            }
        }

        public int column() {
            if (cc > 30) {
                return 30;
            } else if (cc < 10) {
                return 10;
            } else {
                return cc;
            }
        }

        public int mine() {
            if (cm > ((row() - 1) * (column() - 1))) {
                return ((row() - 1) * (column() - 1));
            } else if (cm < 10) {
                return 10;
            } else {
                return cm;
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTimer = new javax.swing.JPanel();
        panelMinas = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());
        add(panelTimer, java.awt.BorderLayout.NORTH);
        add(panelMinas, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public JPanel getPanelMinas(){
        return panelMinas;
    }

    public JPanel getPanelTimer(){
        return panelTimer;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMinas;
    private javax.swing.JPanel panelTimer;
    // End of variables declaration//GEN-END:variables

}
