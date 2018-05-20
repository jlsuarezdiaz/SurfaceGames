package battleship;

import java.awt.Color;
import static java.lang.Math.abs;
import java.util.ArrayList;
import javax.swing.Icon;
import surfacegames.*;

//rename shipLocations?
public class PlaceShips {

    //for CPU
    public static final int PORTAAVIONES = 5;
    public static final int ACORAZADO = 4;
    public static final int CRUCERO = 3;
    public static final int SUBMARINO = 3;
    public static final int LANCHA = 2;
    public static ArrayList<Integer> cLocations = new ArrayList<>();
    //for Player
    private static int currentShip = -1;
    public static boolean pAddShips = false; //determines when player can add their ships
    private static boolean firstSpot = true;
    private static boolean secondSpot = false;
    private static boolean pVertical;
    private static ArrayList<Integer> pLocations = new ArrayList<>();
    private final Icon barco_verde = new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-verde.png"));
    private final Icon barco_azul = new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-azul.png"));
    private final Icon barco_rojo = new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-rojo.png"));
    private final Icon barco_amarillo = new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco-amarillo.png"));
    private final Icon barco_negro = new javax.swing.ImageIcon(getClass().getResource("/battleship/media/barco.png"));
    
    public static ArrayList<Integer> getpLocations() {
        return pLocations;
    }

    //rules for cpu placement of ships
    private static boolean shipOrientation(int startPoint, int ship) { //return true if vertical
        int gridMax = 199;
        if (startPoint > gridMax - (ship * Gui.GRID_DIMENSIONS) + Gui.GRID_DIMENSIONS) {
            return false;
        } else if ((startPoint - Gui.NUM_BUTTONS) % Gui.GRID_DIMENSIONS > (Gui.GRID_DIMENSIONS - 1 - ship)) {
            return true;
        } else {
            int random = Helper.randomNumber(0, 1);
            return random == 0;
        }
    }

    private static void addShip(int ship) {
        int min = 100;
        int max = 197;
        boolean valid = true;
        int startPoint = Helper.randomNumber(min, max);
        boolean vertical = shipOrientation(startPoint, ship);
        int[] checkValid = new int[ship]; //stores points in temp array to check if overlap with previous

        if (vertical) {
            if (startPoint - Gui.GRID_DIMENSIONS + (ship * Gui.GRID_DIMENSIONS) > 199) {
                valid = false;
            }
            for (int i = 0; i < ship; i++) {
                checkValid[i] = startPoint;
                if (cLocations.contains(startPoint)) {
                    valid = false;
                    break;
                }
                startPoint += 10;
            }

        } else { //horizontal
            if ((startPoint - 100) % Gui.GRID_DIMENSIONS > Gui.GRID_DIMENSIONS - 1 - ship) {
                valid = false;
            }
            for (int i = 0; i < ship; i++) {
                checkValid[i] = startPoint;
                if (cLocations.contains(startPoint)) {
                    valid = false;
                    break;
                }
                startPoint++;
            }
        }

        if (valid) { //no overlap, adds temp array to locations arraylist
            for (int i : checkValid) {
                cLocations.add(i);
            }
        } else {
            addShip(ship); //failed a test, retry with a new point
        }
    }

    public static void cpu() {
        addShip(PORTAAVIONES);
        addShip(ACORAZADO);
        addShip(CRUCERO);
        addShip(SUBMARINO);
        addShip(LANCHA);
//        System.out.println(cLocations);
        pAddShips = true;
    }
    
    public Icon getCurrentPlayerShipColor(){
        Icon shipColor = null;
        switch(currentShip){
            case 2:
                shipColor = barco_verde;
                break;
            case 3:
                shipColor = barco_azul;
                break;
            case 4:
                shipColor = barco_amarillo;
                break;
            case 5:
                shipColor = barco_negro;
                break;
        }
        return shipColor;
    }
    //rules for player placement of ships
    public static boolean player(int button, Surface surface) {

        int ship = 0;
        int size = pLocations.size();
        int lastButt;
        boolean okVertical = false;
        boolean okHorizontal = false;
        
        if (pLocations != null && !pLocations.isEmpty()) {
            lastButt = pLocations.get(pLocations.size() - 1);
            
            //Posiciones básicas
            if(abs(button-lastButt)==1 && 
                    !(lastButt%10==9 && button%10==0) && 
                    !(button%10==9 && lastButt%10==0)) // horizontal
                    okHorizontal = true;
            else if(abs(button-lastButt)==10) // vertical
                okVertical = true;
            
            switch(Gui.getSurface()){
                case V_SPHERE:
                    //Identificar laterales
                    if(lastButt%10 == 0 && (button-9)==lastButt)
                        okHorizontal = true;
                    else if(lastButt%10==9 && (button+9)==lastButt)
                        okHorizontal = true;
                    //Identificar mitades parte superior
                    if(lastButt>=200 && lastButt<=209 && (lastButt+button)%10==9)
                        okHorizontal=true;
                    //Identificar mitades parte inferior
                    if(lastButt>=290 && lastButt<=299 && (lastButt+button)%10==9)
                        okHorizontal=true;                                        
                break;
                case H_SPHERE:
                    //Identificar parte superior e inferior
                    if(lastButt >=200 && lastButt<=209 && (button-90)==lastButt)
                        okVertical = true;
                    else if(lastButt >=290 && lastButt<=299 && (button+90)==lastButt)
                        okVertical = true;
                    //Identificar mitades lateral izquierdo
                    if(lastButt%10 == 0 && (abs(lastButt-200)+abs(button-200))==90) 
                        okVertical = true;
                    //Identificar mitades lateral derecho
                    if(lastButt%10==9 && (abs(lastButt-209)+abs(button-209))==90)
                        okVertical = true;                    
                break;
                case TORUS:
                    //Identificar laterales
                    if(lastButt%10 == 0 && (button-9)==lastButt)
                        okHorizontal = true;
                    else if(lastButt%10==9 && (button+9)==lastButt)
                        okHorizontal = true;
                    //Identificar parte superior e inferior
                    if(lastButt >=200 && lastButt<=209 && (button-90)==lastButt)
                        okVertical = true;
                    else if(lastButt >=290 && lastButt<=299 && (button+90)==lastButt)
                        okVertical = true;
                break;
                case V_CYLINDER:
                    //Identificar laterales
                    if(lastButt%10 == 0 && (button-9)==lastButt)
                        okHorizontal = true;
                    else if(lastButt%10==9 && (button+9)==lastButt)
                        okHorizontal = true;
                break;
                case H_CYLINDER:
                    //Identificar parte superior e inferior
                    if(lastButt >=200 && lastButt<=209 && (button-90)==lastButt)
                        okVertical = true;
                    else if(lastButt >=290 && lastButt<=299 && (button+90)==lastButt)
                        okVertical = true;
                break;
            }
            
        }
        if (size == 17) {
            pAddShips = false;
        }
        if (size < 5) {
            if (size == 4) {
                Gui.message("Coloca tu acorazado - 4 casillas");
            }
            currentShip = PORTAAVIONES;
        }
        if (size >= 5 && size < 9) {
            if (size == 8) {
                Gui.message("Coloca tu crucero - 3 casillas");
            }
            currentShip = ACORAZADO;
        }
        if (size >= 9 && size < 12) {
            if (size == 11) {
                Gui.message("Coloca tu submarino - 3 casillas");
            }
            currentShip = CRUCERO;
        }
        if (size >= 12 && size < 15) {
            if (size == 14) {
                Gui.message("Coloca tu lancha - 2 casillas");
            }
            currentShip = SUBMARINO;
        }
        if (size >= 15 && size < 17) {
            if (size == 16) {
                Gui.message("Encuentra los barcos de tu adversario en el tablero de la izquierda");
            }
            currentShip = LANCHA;
        }
        //first spot for each currentShip
        if (firstSpot && (checkValidVertical(button, currentShip) || checkValidHorizontal(button, currentShip))) {
            pLocations.add(button);
            firstSpot = false;
            secondSpot = true;
            return true;
        } else if (secondSpot) {
            if(okVertical && checkValidVertical(button, currentShip - 2)){
                pVertical = true;
                pLocations.add(button);
                secondSpot = false;
                return true;
            }else if(okHorizontal && checkValidHorizontal(button, currentShip - 2)){
                pVertical = false;
                pLocations.add(button);
                secondSpot = false;
                return true;
            } else {
                return false;
            }

        } else {
            if (pVertical) {
                if (okVertical) {
                    pLocations.add(button);
                    if (pLocations.size() == 5 || pLocations.size() == 9 || pLocations.size() == 12 || pLocations.size() == 15 || pLocations.size() == 17) {
                        firstSpot = true;
                    }
                    return true;
                } else {
                    return false;
                }
                //need to prevent wrap around placement
            } else if (!pVertical) {
                if (okHorizontal) {
                    pLocations.add(button);
                    if (pLocations.size() == 5 || pLocations.size() == 9 || pLocations.size() == 12 || pLocations.size() == 15 || pLocations.size() == 17) {
                        firstSpot = true;
                    }
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    private static boolean checkValidVertical(int bCoord, int currentShip) {
        return Helper.checkDirection("north", bCoord, currentShip, pLocations) + Helper.checkDirection("south", bCoord, currentShip, pLocations) >= currentShip;
    }

    private static boolean checkValidHorizontal(int bCoord, int ship) {
        return Helper.checkDirection("east", bCoord, ship, pLocations) + Helper.checkDirection("west", bCoord, ship, pLocations) >= ship;
    }
}
