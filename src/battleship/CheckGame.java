package battleship;

import java.awt.Color;

public class CheckGame {

    //cpu ships
    private static int cPortaaviones;
    private static int cAcorazado;
    private static int cCrucero;
    private static int cSubmarino;
    private static int cLancha;
    private static int cAllShips;

    //player ships
    private static int pPortaaviones;
    private static int pAcorazado;
    private static int pCrucero;
    private static int pSubmarino;
    private static int pLancha;
    private static int pAllShips;

    
    public static void init() {
        //cpu ships
        cPortaaviones = 5;
        cAcorazado = 4;
        cCrucero = 3;
        cSubmarino = 3;
        cLancha = 2;
        cAllShips = cPortaaviones + cAcorazado + cCrucero + cSubmarino + cLancha;

        //player ships
        pPortaaviones = 5;
        pAcorazado = 4;
        pCrucero = 3;
        pSubmarino = 3;
        pLancha = 2;
        pAllShips = pPortaaviones + pAcorazado + pCrucero + pSubmarino + pLancha;
    }
    public static boolean player(String ship) {

        if (ship.equals("PORTAAVIONES")) {
            cPortaaviones--;
            if (cPortaaviones == 0) {
                Gui.message("Has hundido su portaaviones");
            }
        }
        if (ship.equals("ACORAZADO")) {
            cAcorazado--;
            if (cAcorazado == 0) {
                Gui.message("Has hundido su acorazado");
            }
        }
        if (ship.equals("CRUCERO")) {
            cCrucero--;
            if (cCrucero == 0) {
                Gui.message("Has hundido su crucero");
            }
        }
        if (ship.equals("SUBMARINO")) {
            cSubmarino--;
            if (cSubmarino == 0) {
                Gui.message("Has hundido su submarino");
            }
        }
        if (ship.equals("LANCHA")) {
            cLancha--;
            if (cLancha == 0) {
                Gui.message("Has hundido su lancha");
            }
        }
        cAllShips--;
        if (cAllShips == 0) {
            //gameover
            Gui.message("¡HUNDISTE LA FLOTA!");
            return true;
        } else {
            return false;
        }
    }

    public static boolean cpu(String ship, int index) {
        boolean iconChange = false;
        if (ship.equals("PORTAAVIONES")) {
            iconChange = true;
            pPortaaviones--;
            if (pPortaaviones == 0) {
                Guess.changeMode(0);
            }
        }
        if (ship.equals("ACORAZADO")) {
            iconChange = true;
            pAcorazado--;
            if (pAcorazado == 0) {
                Guess.changeMode(1);
            }
        }
        if (ship.equals("CRUCERO")) {
            iconChange = true;
            pCrucero--;
            if (pCrucero == 0) {
                Guess.changeMode(2);
            }
        }
        if (ship.equals("SUBMARINO")) {
            iconChange = true;
            pSubmarino--;
            if (pSubmarino == 0) {
                Guess.changeMode(3);
            }
        }
        if (ship.equals("LANCHA")) {
            iconChange = true;
            pLancha--;
            if (pLancha == 0) {
                Guess.changeMode(4);
            }
        }
        if (iconChange){
            Gui.buttons[index].setDisabledIcon(new javax.swing.ImageIcon("/battleship/media/barco-roto.png"));
        }
        pAllShips--;
        if (pAllShips == 0) {
            //gameover
            Gui.message("GAME OVER");
            return true;
        } else {
            return false;
        }
    }

    public static int getMaxShip() {
        if (pPortaaviones > 0) {
            return 5;
        } else if (pAcorazado > 0) {
            return 4;
        } else if (pCrucero > 0 || pSubmarino > 0) {
            return 3;
        } else {
            return 2;
        }
    }
}
