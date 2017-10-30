/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.util;

/**
 *
 * @author e.mora.c
 */
public class MSQLConfiguration {
    public static LEVEL LOGLEVEL = LEVEL.DEBUG;
    
    public enum LEVEL{
        ERROR,
        WARN,
        DEBUG,
        TRACE,
        INFO
    }
    
    public static String getConfiguration(){
        StringBuilder sb = new StringBuilder();
        sb.append("LOGLEVEL: ").append(LOGLEVEL.toString()).append("\n");
        return sb.toString();
    }
}
