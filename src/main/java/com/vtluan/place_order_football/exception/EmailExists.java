package com.vtluan.place_order_football.exception;

public class EmailExists extends Exception {
    public EmailExists(String messenger) {
        super(messenger);
    }
}
