/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.m0r4.soft.commands;

/**
 *
 * @author e.mora.c
 */
public class InvalidCommandException extends Exception {

    public InvalidCommandException(String unexpectedCommand) {
        super(String.format("Unexpected command or action near from '%s'",unexpectedCommand));
    }
    public InvalidCommandException(int expected,int received) {
        super(String.format("Invalid parameters specification, expected: %d. Received: %d",expected, received));
    }
}
