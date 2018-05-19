/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

/**
 *
 * @author usuario
 */
public class PairRot {
    private String nombre;
    private float rot;
    
    public PairRot(String n, float rot){
        this.nombre = n;
        this.rot = rot;
    }
    
    public void setNombre(String n){
        this.nombre=n;
    }
    
    public void setRot(float rot){
        this.rot = rot;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public double getRot(){
        return this.rot;
    }
}
