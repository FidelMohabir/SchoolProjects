package edu.qc.seclass.replace;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace {

    //VARIABLES
    private boolean b_Option, f_Option, l_Option, i_Option;
    private String fromStringArgument, toStringArgument;
    private ArrayList<InputFile> inputFiles = new ArrayList<>();
    ArrayList<String> fromStringArguments = new ArrayList<>();
    ArrayList<String> toStringArguments = new ArrayList<>();
    private Charset charset = StandardCharsets.UTF_8;
    int validOptions;

    //CONSTRUCTORS
    public Replace(String[] arguments) {
        boolean argumentsParsed = parseArguments(arguments);
        if (argumentsParsed){
            replacement();
        } else if ((!argumentsParsed) || (validOptions <= 0)) {
            usage();
        }
    }

    //METHODS
    private boolean parseArguments(String[] arguments) {
        int index = 0;
        validOptions = 0;

        //parse options
        while (arguments[index].startsWith("-") &&
                arguments[index].length() == 2 &&
                index + 1 < arguments.length) {

            String optionArgument = arguments[index];
            switch (optionArgument){
                case "-b":
                    b_Option = true;
                    validOptions++;
                    break;
                case "-f":
                    f_Option = true;
                    validOptions++;
                    break;
                case "-i":
                    i_Option = true;
                    validOptions++;
                    break;
                case "-l":
                    l_Option = true;
                    validOptions++;
                    break;
                case "--":
                    break;
                default:
                    break;
            }
            index++;
        }

        int lastIndex = arguments.length-1;
        if(!arguments[lastIndex].endsWith("tmp")){
            return false;
        }

        //parse <from> and <to> string
        while(!arguments[index].equals("--") && (index + 1 < arguments.length) && !(arguments[index+1].endsWith("tmp"))) {
            fromStringArguments.add(arguments[index]);
            toStringArguments.add(arguments[index+1]);
            index += 2;
        }

        //parse inputFiles
        if(index + 1 >= arguments.length)  {
            return false;
        }

        while (index < arguments.length && !(arguments[index].endsWith("tmp"))){
            index++;
        }

        while (index < arguments.length && arguments[index].endsWith("tmp")) {
            String s = getFileContent(arguments[index]);
            inputFiles.add(new InputFile(arguments[index], s));
            index++;
        }

        return true;

    }

    private void replacement() {
        if(b_Option) {
            createBackup();
        }
        if((!f_Option && !l_Option && !i_Option) || (!f_Option && !l_Option && i_Option) ) {
            replaceAll();
        }
        if((f_Option && !l_Option && !i_Option) || (f_Option && !l_Option && i_Option)) {
            replaceFirst();
        }
        if((!f_Option && l_Option && !i_Option) || !f_Option && l_Option && i_Option) {
            replaceLast();
        }
        if(f_Option && l_Option && !i_Option || f_Option && l_Option && i_Option) {
            replaceFirst();
            replaceLast();
        }
        modifyFiles();
    }

    private void createBackup(){
        for(InputFile currentFile : inputFiles){
            String pathname = currentFile.getPathname();
            File backupFile = new File(pathname + ".bck");
            try {
                PrintWriter printWriter = new PrintWriter(backupFile);
                printWriter.print(currentFile.getContents());
                printWriter.close();
            } catch (IOException e) {
            }
        }
    }

    private void replaceLast() {
        for(int i = 0; i < fromStringArguments.size(); i++) {
            String fromStringArgument = fromStringArguments.get(i);
            String toStringArgument = toStringArguments.get(i);
            for (InputFile currentFile : inputFiles) {
                String fileContents = currentFile.getContents();
                if (fileContents.isEmpty() && fromStringArgument.isEmpty()) {
                    currentFile.setContents(toStringArgument);
                } else if (!fileContents.isEmpty() && fromStringArgument.isEmpty()) {
                    usage();
                } else if (i_Option) {
                    fileContents = fileContents.replaceFirst("(?i)" + fromStringArgument, toStringArgument);
                    currentFile.setContents(fileContents);
                } else {
                    if (!fileContents.contains(fromStringArgument)) return;
                    int index = fileContents.lastIndexOf(fromStringArgument);
                    fileContents = fileContents.substring(0, index) + toStringArgument + fileContents.substring(index + fromStringArgument.length());
                    currentFile.setContents(fileContents);
                }
            }
        }
    }

    private void replaceFirst() {
        for(int i = 0; i < fromStringArguments.size(); i++) {
            String fromStringArgument = fromStringArguments.get(i);
            String toStringArgument = toStringArguments.get(i);
            for (InputFile currentFile : inputFiles) {
                String fileContents = currentFile.getContents();
                if (i_Option) {
                    fileContents = fileContents.replaceFirst("(?i)" + fromStringArgument, toStringArgument);
                    currentFile.setContents(fileContents);
                } else {
                    fileContents = fileContents.replaceFirst(Pattern.quote(fromStringArgument), Matcher.quoteReplacement(toStringArgument));
                    currentFile.setContents(fileContents);
                }
            }
        }
    }

    private void replaceAll() {
        for (int i = 0; i < fromStringArguments.size(); i++) {
            String fromStringArgument = fromStringArguments.get(i);
            String toStringArgument = toStringArguments.get(i);
            for (InputFile currentFile : inputFiles) {
                String fileContents = currentFile.getContents();
                try {
                    if (!fileContents.isEmpty() && fromStringArgument.isEmpty()) {
                        usage();
                    } else
                        if (i_Option == false) {
                        fileContents = fileContents.replaceAll(fromStringArgument, toStringArgument);
                        currentFile.setContents(fileContents);
                    } else {
                        fileContents = fileContents.replaceAll("(?i)" + fromStringArgument, toStringArgument);
                        currentFile.setContents(fileContents);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
    }

    private void modifyFiles(){
        for(InputFile currentFile : inputFiles){
            String pathname = currentFile.getPathname();
            try {
                PrintWriter printWriter = new PrintWriter(pathname);
                printWriter.print(currentFile.getContents());
                printWriter.close();
            } catch (IOException e) {
            }
        }

    }

    private static void usage() {
        System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*" );
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException("test",e);
        }
        return content;
    }



}
