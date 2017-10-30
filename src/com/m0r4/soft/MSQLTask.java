/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft;

import com.m0r4.soft.commands.InvalidCommandException;

/**
 *
 * @author e.mora.c
 */
public abstract class MSQLTask {
    public abstract void process(String[] command) throws InvalidCommandException;
}
