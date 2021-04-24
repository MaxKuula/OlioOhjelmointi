package com.example.objectproject;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class LogManager {

    private static LogManager logMan = new LogManager();

    public static LogManager getInstance() { return logMan; }


    public void createLog(Context context) {

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("log.txt", MODE_PRIVATE));
            osw.close();
        }
        catch (IOException e){

        }
    }

    public void appendLog(Context context, String text) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("log.txt", Context.MODE_APPEND));
            osw.write(text + "\n");
            osw.close();
        }
        catch (IOException e) {

        }
    }

}
