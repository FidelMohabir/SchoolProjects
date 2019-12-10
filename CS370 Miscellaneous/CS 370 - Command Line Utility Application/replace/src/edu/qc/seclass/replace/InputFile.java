package edu.qc.seclass.replace;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class InputFile {

    //VARIABLES
    private String pathname, contents;

    //CONSTRUCTORS
    public InputFile(String pathname , String contents){
        this.pathname = pathname;
        this.contents = contents;
        try {
            PrintWriter printWriter = new PrintWriter(new File(pathname));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GETTERS SETTERS
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPathname() {
        return pathname;
    }

}
