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
    private double Rot;
    
    public PairRot(String n, double rot){
        this.nombre = n;
        this.Rot = rot;
    }
    
    public void setNombre(String n){
        this.nombre=n;
    }
    
    public void setRot(double rot){
        this.Rot = rot;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public double getRot(){
        return this.Rot;
    }
}
