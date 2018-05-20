package battleship;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import surfacegames.Surface;

public class Gui extends JFrame implements ActionListener {

    public static final int GRID_DIMENSIONS = 10;
    public static final int NUM_BUTTONS = GRID_DIMENSIONS * GRID_DIMENSIONS;
    public static JButton buttons[] = new JButton[NUM_BUTTONS];
    private static JLabel text = new JLabel();
    private static JFrame f = new JFrame("HUNDIR LA FLOTA");
    private static JPanel container = new JPanel();
    private static Gui gui = new Gui();
    private static Surface surface;

    public static void main(String[] args) {
        PlaceShips.cpu();
        createGui();
        f.setVisible(true);
    }
    public static void setSurface(Surface surf){
        surface = surf;
    }
    
    public static Surface getSurface(){
        return surface;
    }
    private static void createGui() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);

        JPanel message = new JPanel();
        message.add(text);

        Gui.message("Coloca tu portaaviones en el tablero de la derecha - 5 casillas");
        
        container.setLayout(null);
        container.setBorder(new EmptyBorder(20, 100, 20, 100));
        //Tableros
        container.add(gui.createGrid(1)).setBounds(20, 50, 500, 500);
        container.add(gui.createGrid(2)).setBounds(550, 50, 500, 500);       
        //Mensaje
        container.add(message);
        message.setBounds(250, 600, 500, 20);
        
        f.setContentPane(container);
        //Tamaño ventana juego
        f.setSize(1100, 1000);
    }

    private Container createGrid(int num) {
        JPanel grid = new JPanel(new GridLayout(GRID_DIMENSIONS, GRID_DIMENSIONS));

        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttons[i] = new JButton(Integer.toString(i));
            buttons[i].setBorder(new LineBorder(Color.BLACK));
            if (PlaceShips.cLocations.contains(100 + i)) {
//                buttons[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/mar.png")));
//                buttons[i
//                
                buttons[i].setBackground(Color.gray); //change color to see cpu ships
            } else {
                buttons[i].setBackground(Color.gray);
//                buttons[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/mar.png")));
//                buttons[i].setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco.png")));
            }
            buttons[i].setActionCommand((num * 100) + i + "");
            buttons[i].setPreferredSize(new Dimension(100,100));
            buttons[i].addActionListener(this);
            grid.add(buttons[i]);
            grid.setBorder(new EmptyBorder(15, 0, 15, 0));
            grid.setPreferredSize(new Dimension(550, 450));
        }
        return grid;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String className = getClassName(ae.getSource());

        if (className.equals("JButton")) {
            JButton button = (JButton) (ae.getSource());
            int bCoord = Integer.parseInt(button.getActionCommand());

            //code here to handle user clicking on grid
            if (bCoord < 200) {
                if (Guess.player(bCoord)) {
                    button.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-roto.png")));
                    button.setEnabled(false);
                } else {
                    button.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/mar.png")));
                    button.setEnabled(false);
                }

            } else if (bCoord >= 200 && PlaceShips.pAddShips) {
                PlaceShips.player(bCoord,surface);
                PlaceShips g = new PlaceShips();
                if (PlaceShips.getpLocations().contains(bCoord)) {
                    button.setEnabled(false);
                    System.out.println(getClass().getResource("/battleship/media/barco.png"));
                    button.setDisabledIcon(g.getCurrentPlayerShipColor());
                }
            }
        }
    }

    private String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    public static void message(String text) {
        Gui.text.setText(text);

    }
}
