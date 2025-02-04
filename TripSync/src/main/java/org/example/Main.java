package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TripSync tripSync = TripSync.getInstance();
        tripSync.loadUtenti();
    }
}
