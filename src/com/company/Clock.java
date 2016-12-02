package com.company;

import sun.awt.windows.ThemeReader;

/**
 * Created by Oussama on 29/11/2016.
 */
public class Clock {

    Thread thread = new Thread();

    public Clock() {
        thread.start();
    }


    public void tick(int seconds) {

        seconds *= 1000.; // conversion to milliseconds
        int i=0;
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while (i<5) {
            currentTime = System.currentTimeMillis();


            if (((currentTime-startTime) /1000. == 1)) {
                System.out.println("one second passed");
                startTime = System.currentTimeMillis();
                i++;
               // MathTeacher.setTimeToDraw(i);
            }

        }

        System.out.println("done Ticking");
    }
}
