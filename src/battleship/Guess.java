package battleship;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Guess {

    private static ArrayList<Integer> cCurrentHits = new ArrayList<>(); //current ship only
    private static ArrayList<Integer> cGuesses = new ArrayList<>();
    private static int mode = 0; //target or hunt mode for cpu

    public static ArrayList<Integer> getcGuesses() {
        return cGuesses;
    }

    public static boolean player(int guess) {

        if (PlaceShips.cLocations.contains(guess)) {
            int index = PlaceShips.cLocations.indexOf(guess);
            if (index < 5) {
                CheckGame.player("PORTAAVIONES");
            } else if (index >= 5 && index < 9) {
                CheckGame.player("ACORAZADO");
            } else if (index >= 9 && index < 12) {
                CheckGame.player("CRUCERO");
            } else if (index >= 12 && index < 15) {
                CheckGame.player("SUBMARINO");
            } else if (index >= 15 && index < 17) {
                CheckGame.player("LANCHA");
            }
            cpu();
            return true;

        } else {
            cpu();
            return false;
        }

        //handle hit and miss when a player guesses
        //check game here
    }

    public static void cpu() {
        int guess = cpuGuess();
        int button = guess - 200;

        if (PlaceShips.getpLocations().contains(guess)) {
            cCurrentHits.add(guess);

            if (mode == 0 || mode == 1) {
                changeMode();
            }

            int index = PlaceShips.getpLocations().indexOf(guess);

            if (index < 5) {
                CheckGame.cpu("PORTAAVIONES", button);
            } else if (index >= 5 && index < 9) {
                CheckGame.cpu("ACORAZADO", button);
            } else if (index >= 9 && index < 12) {
                CheckGame.cpu("CRUCERO", button);
            } else if (index >= 12 && index < 15) {
                CheckGame.cpu("SUBMARINO", button);
            } else if (index >= 15 && index < 17) {
                CheckGame.cpu("LANCHA", button);
            }
        } else {
            Gui.buttons[button].setEnabled(false);
//            Gui.buttons[button].setBackground(Color.LIGHT_GRAY);

        }
        cGuesses.add(guess);
    }

    private static int cpuGuess() {
        int guess = 0;

        switch (mode) { //hunt mode is weak, add algorithm to diversify guesses
            case 0:  //hunt mode
                int random = getRandom(200, 299);
                while (!Helper.isValid(random, getcGuesses())) {
                    random = getRandom(200, 299);
                }
                guess = random;
                return guess;

            case 1: // 1 hit, figure out if V or H
                int prevGuess = cCurrentHits.get(0);
                guess = guessDirection(prevGuess, CheckGame.getMaxShip() - 1, prevGuess);
                return guess;
            case 2:
                int hits = cCurrentHits.size();
                int start = Collections.min(cCurrentHits);
                int end = Collections.max(cCurrentHits);

                if ((end - start) % 10 == 0) { //vertical
                    if (Helper.checkDirection("north", start, CheckGame.getMaxShip() - hits, cGuesses) > Helper.checkDirection("south", end, CheckGame.getMaxShip() - hits, cGuesses)) {
                        guess = start - 10;
                    } else {
                        guess = end + 10;
                    }
                } else {  //horizontal
                    if (Helper.checkDirection("east", end, CheckGame.getMaxShip() - hits, cGuesses) > Helper.checkDirection("west", start, CheckGame.getMaxShip() - hits, cGuesses)) {
                        guess = end + 1;
                    } else {
                        guess = start - 1;
                    }

                }

                if (Helper.isValid(guess, getcGuesses())) {
                    return guess;
                } else {
                    guess = adjacentShips();
                    int counter = 0; //to keep guess from getting stuck in loop if no available options
                    while (!Helper.isValid(guess, getcGuesses())) {
                        counter++;
                        guess = adjacentShips();
                        if (counter > 20) { //all options should have been tried after 20 iterations
                            guess = getRandom(200, 299);
                        }
                    }
                    return guess;
                }
        }
        return guess;
    }

    private static ArrayList checkSurrounding(int guess, int ship) {
        ArrayList<Integer> results = new ArrayList<>();

        results.add(Helper.checkDirection("north", guess, ship, cGuesses));
        results.add(Helper.checkDirection("east", guess, ship, cGuesses));
        results.add(Helper.checkDirection("south", guess, ship, cGuesses));
        results.add(Helper.checkDirection("west", guess, ship, cGuesses));

        return results;
    }

    public static int getRandom(int start, int end) {
        int randomInt = 0;
        int row = (randomInt - 200) / Gui.GRID_DIMENSIONS;
        int col = (randomInt - 200) % Gui.GRID_DIMENSIONS;
        randomInt = Helper.randomNumber(200, 299);

        while ((row + col) % 2 == 0) {
            randomInt = Helper.randomNumber(200, 299);
            row = (randomInt - 200) / Gui.GRID_DIMENSIONS;
            col = (randomInt - 200) % Gui.GRID_DIMENSIONS;
        }

        return randomInt;
    }

    private static int guessDirection(int guess, int ship, int prevGuess) {
        ArrayList<Integer> results = new ArrayList<>();
        ArrayList<Integer> maxResults = new ArrayList<>();
        results = checkSurrounding(guess, ship);

        int max = Collections.max(results);

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i) == max) {
                maxResults.add(i + 1);
            }
        }

        Random r = new Random();
        int randomDirection = maxResults.get(r.nextInt(maxResults.size()));
        if (randomDirection == 1) { //north
            guess = prevGuess - 10;
        }
        if (randomDirection == 2) { //east
            guess = prevGuess + 1;
        }
        if (randomDirection == 3) { //south
            guess = prevGuess + 10;
        }
        if (randomDirection == 4) { //west
            guess = prevGuess - 1;
        }
        return guess;
    }

    private static void changeMode() {
        switch (mode) {
            case 0: //after hit, go from hunt to target
                mode = 1;
                break;
            case 1: //determined if vertical or horizontal
                mode = 2;
                break;
        }
    }

    public static void changeMode(int ship) {
        int i = 0;

        switch (ship) {
            case 0:
                for (i = 0; i < 5; i++) {
                    removeGuesses(i);
                }
            case 1:
                for (i = 5; i < 9; i++) {
                    removeGuesses(i);
                }
            case 2:
                for (i = 9; i < 12; i++) {
                    removeGuesses(i);
                }
            case 3:
                for (i = 12; i < 15; i++) {
                    removeGuesses(i);
                }
            case 4:
                for (i = 15; i < 17; i++) {
                    removeGuesses(i);
                }
        }
        if (cCurrentHits.size() == 0) {
            mode = 0;
        }
    }

    private static void removeGuesses(int i) {
        if (cCurrentHits.contains(PlaceShips.getpLocations().get(i))) {
            cCurrentHits.remove(cCurrentHits.indexOf(PlaceShips.getpLocations().get(i)));
        }
    }

    private static int adjacentShips() {
        Random r = new Random();
        int randomPoint = cCurrentHits.get(r.nextInt(cCurrentHits.size()));
        int guess = 0;

        int direction = (int) Math.floor(Math.random() * 4);
        switch (direction) {
            case 0: //north
                guess = randomPoint - 10;
                return guess;
            case 1: //east
                guess = randomPoint + 1;
                return guess;
            case 2: //south
                guess = randomPoint + 10;
                return guess;
            case 3: //west
                guess = randomPoint - 1;
                return guess;
        }
        return guess;
    }

    public static void reset() {
        cCurrentHits.clear();
        cGuesses.clear();
        mode = 0;
    }
}
