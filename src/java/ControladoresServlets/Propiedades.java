/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladoresServlets;

/**
 *
 * @author jp
 */
public class Propiedades {

    private static Propiedades instance;

    public static Propiedades getInstance() {
        if (instance == null) {
            instance = new Propiedades();
        }
        return instance;
    }

    private String wsU, wsP, wsC;

    /**
     * @return the wsU
     */
    public String getWsU() {
        return wsU;
    }

    /**
     * @param wsU the wsU to set
     */
    public void setWsU(String wsU) {
        this.wsU = wsU;
    }

    /**
     * @return the wsP
     */
    public String getWsP() {
        return wsP;
    }

    /**
     * @param wsP the wsP to set
     */
    public void setWsP(String wsP) {
        this.wsP = wsP;
    }

    /**
     * @return the wsC
     */
    public String getWsC() {
        return wsC;
    }

    /**
     * @param wsC the wsC to set
     */
    public void setWsC(String wsC) {
        this.wsC = wsC;
    }

}
