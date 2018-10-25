import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.util.logging.Level;

import java.util.logging.Logger;



public class Entropia {



    public static void main(String[] args) {

        System.out.println("hello \u2603");



        if (args.length == 0) {

            System.out.println("Nie podano pliku do odczytu!");



            return;

        }



        String fileName = args[0];

        System.out.println("Wczytano plik " + fileName);



        try {

            BufferedReader bufor = new BufferedReader(new FileReader(fileName));



            int kodZnaku;

            while (bufor.ready()) {

                kodZnaku = bufor.read();

                System.out.println(kodZnaku + "\t" + (char) kodZnaku);



            }

        } catch (Exception ex) {

            System.out.println("Bład przekazywania tekstu!" + ex);

        }



    }



}

