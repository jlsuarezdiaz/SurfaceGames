package battleship;

import java.util.ArrayList;

public class Helper {

    public static int randomNumber(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static int checkDirection(String direction, int guess, int ship, ArrayList arr) {
        int num = direction(direction);
        int bCoord;

        boolean horizontal = false;
        if (direction.equals("east") || direction.equals("west")) {
            horizontal = true;
        }

        ArrayList<Integer> dir = new ArrayList<>();
        bCoord = guess + num;
        for (int i = 0; i < ship; i++) {
            if (isValid(bCoord, arr) && horizontalValid(guess, bCoord, horizontal)) {
                dir.add(bCoord);
//                System.out.println(bCoord);
            } else {
                break;
            }
            bCoord = bCoord + num;
        }
        return dir.size();
    }

    private static int direction(String direction) {
        switch (direction) {
            case ("north"):
                return -10;
            case ("east"):
                return 1;
            case ("south"):
                return 10;
            case ("west"):
                return -1;
        }
        return 0;
    }

    private static boolean horizontalValid(int start, int guess, boolean horizontal) {

        if (!horizontal) { //vertical
            return true;
        }

        int startRow = (start - 200) / 10;
        int guessRow = (guess - 200) / 10;

        return startRow == guessRow;
    }

    public static boolean isValid(int bCoord, ArrayList locations) {
        return bCoord < 300 && bCoord >= 200 && !locations.contains(bCoord);
    }
}
