/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surfacegames;

import java.awt.Color;

/**
 *
 * @author jlsuarezdiaz
 */
public interface Scoreable {
    public void setScore(int value);
    
    public int getScore();
    
    public void setForegroundColor(Color c);
    
    public void increaseScore(int value);
    
    public void decreaseScore(int value);
}
