package battleship;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import surfacegames.Surface;

public class Gui extends JFrame implements ActionListener {

    public static final int GRID_DIMENSIONS = 10;
    public static final int NUM_BUTTONS = GRID_DIMENSIONS * GRID_DIMENSIONS;
    public static JButton[] buttons;
    private static JLabel text;
    private static JFrame f;
    private static JPanel container ;
    private static Gui gui = new Gui();
    private static Surface surface;
    //sonidos
    private static Map<String, Clip> soundEffects = new HashMap<String, Clip>();


    public static void main(String[] args) {
        gui.addSoundEffect("explosion", "/surfacegames/media/explosion.wav");
        gui.addSoundEffect("agua", "/surfacegames/media/agua.wav");
        CheckGame.init();
        Guess.reset();
    //    PlaceShips.init();
       
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
        f = new JFrame("HUNDIR LA FLOTA");
        buttons = new JButton[NUM_BUTTONS];
        f.setResizable(false);

        JPanel message = new JPanel();
        text = new JLabel();
        text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 16));
        
        
        message.add(text);

        Gui.message("Coloca tu portaaviones en el tablero de la derecha - 5 casillas");
        
        container = new JPanel();
        container.setLayout(null);
        container.setBorder(new EmptyBorder(10, 70, 10, 70));
        //Tableros
        container.add(gui.createGrid(1)).setBounds(20, 20, 500, 500);
        container.add(gui.createGrid(2)).setBounds(550, 20, 500, 500);       
        //Mensaje
        container.add(message);
        message.setBounds(250, 550, 500, 20);
        
        f.setContentPane(container);
        //Tamaño ventana juego
        f.setSize(1100, 650);
    }

    private Container createGrid(int num) {
        JPanel grid = new JPanel(new GridLayout(GRID_DIMENSIONS, GRID_DIMENSIONS));

        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttons[i] = new JButton(Integer.toString(i));
            buttons[i].setBorder(new LineBorder(Color.BLACK));
            if (PlaceShips.cLocations.contains(100 + i)) {
                buttons[i].setBackground(Color.gray); //change color to see cpu ships
            } else {
                buttons[i].setBackground(Color.gray);
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
                    gui.playSoundEffect("explosion");
                    
                    button.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-roto.png")));
                    button.setEnabled(false);
                } else {
                    gui.playSoundEffect("agua");
                    button.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/battleship/media/mar.png")));
                    button.setEnabled(false);
                }

            } else if (bCoord >= 200 && PlaceShips.pAddShips) {
                PlaceShips.player(bCoord);
                PlaceShips g = new PlaceShips();
                if (PlaceShips.getpLocations().contains(bCoord)) {
                    button.setEnabled(false);
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
    // Código para sonidos :D
    public void addSoundEffect(String key, String path) {
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(getClass().getResource(path)));
            soundEffects.put(key, c);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playSoundEffect(String key) {
        Clip c = soundEffects.get(key);
        if (c != null) {
            c.stop();
            c.setMicrosecondPosition(0);
            c.start();
        }
    }
}
