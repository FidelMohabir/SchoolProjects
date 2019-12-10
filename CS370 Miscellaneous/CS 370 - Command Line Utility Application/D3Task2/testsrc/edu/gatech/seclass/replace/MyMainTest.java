package edu.gatech.seclass.replace;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MyMainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities

    private File createTmpFile() throws IOException {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    private File createInputFile1() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile2() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }

    private File createInputFile3() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }

    private File createInputFile4() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Yo, Taylor," +
                "I'm really happy for you and I'mma let you finish, " +
                "but Beyonce had one of the best videos of all time! " +
                "One of the best videos of all time!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile5() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("!@#$ %^&* 1234 5678 12#$ !@34");

        fileWriter.close();
        return file1;
    }

    private File createInputFile6() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$");
        fileWriter.close();
        return file1;
    }

    private File createInputFile11() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile12() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill," + System.lineSeparator() +
                "This is another test file for the replace utility" + System.lineSeparator() +
                "that contains a list:" + System.lineSeparator() +
                "-a) Item 1" + System.lineSeparator() +
                "-b) Item 2" + System.lineSeparator() +
                "..." + System.lineSeparator() +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }

    private File createInputFile13() throws Exception {
        File file1 = createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?" + System.lineSeparator() +
                "It is important to know your abc and 123," +
                "so you should study it" + System.lineSeparator() +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }

    private File createInputFile14() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("");

        fileWriter.close();
        return file;
    }

    private File createInputFile15() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("First line: not replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: not replaced" + System.lineSeparator());

        fileWriter.close();
        return file;
    }

    private File createInputFile16() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("ababab");

        fileWriter.close();
        return file;
    }

    private File createInputFile17() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("The goal here is to replace string \"-i\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -i and -f");

        fileWriter.close();
        return file;
    }

    private File createInputFile18() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("Let's have some numbers in the file: 12345678");

        fileWriter.close();
        return file;
    }

    private File createInputFile19() throws Exception {
        File file = createTmpFile();
        FileWriter fileWriter = new FileWriter(file);

        fileWriter.write("-- -- -- --");

        fileWriter.close();
        return file;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }







    // ACTUAL TEST CASES
    // FIRST 6 TEST CASES ARE THE 6 ORIGINAL TEST CASES PROVIDED
    // The first three of these test cases correspond to the examples  of usage of ​replace​ that we provided.
    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        // Would replace all occurrences of "Howdy" with "Hello" in the inputFiles specified.
        // Because the "-i" option is specified, occurrences of "howdy", "HOwdy", "HOWDY", and so on would also be replaced.
        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        // We are expecting 'Hello' instead of 'Howdy'
        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        // Grabbing the actual content in the inputFiles.
        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        //assertEquals(String message, Object expected, Object actual)
        //Asserts that two objects are equal.
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertEquals("The inputFiles differ!", expected3, actual3);

        //assertFalse(boolean condition)
        //Asserts that a condition is false.
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();

        String args[] = {"-b", "-f", "Bill", "William", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy William,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy William,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void mainTest3() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-f", "-l", "abc", "ABC", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your ABC and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: ABC and 123";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The inputFiles differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    //The remaining three are additional tests provided to further  clarify the behavior of ​replace​. 
    @Test
    public void mainTest4() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"123", "<numbers removed>", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n" +
                "It is important to know your abc and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: abc and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The inputFiles differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void mainTest5() throws Exception {
        File inputFile = createInputFile2();

        String args1[] = {"-b", "--", "-a", "1", "--", inputFile.getPath()};
        Main.main(args1);
        String args2[] = {"--", "-b", "2", "--", inputFile.getPath()};
        Main.main(args2);

        String expected = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "1) Item 1\n" +
                "2) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The inputFiles differ!", expected, actual);
        assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void mainTest6() {
        String args[] = {"blah",};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    //Implementation of test frame #1
    @Test
    public void mainTest7() throws Exception {
        File inputFile1 = createTmpFile();
        //              OPT <from> <to>  --   <filename> [<filename>]*
        String args[] = {"",  "",   "", "--" };
        Main.main(args);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #2
    @Test
    public void mainTest8() throws Exception {
        File inputFile1 = createTmpFile();
        //              OPT <from> <to>  --   <filename> [<filename>]*
        String args[] = {"",  "",   "", "--", inputFile1.getPath() };
        Main.main(args);

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", 0, actual1.length());
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #3
    @Test
    public void mainTest9() throws Exception{
        File inputFile1 = createTmpFile();
        //              OPT <from> <to>  --   <filename> [<filename>]*
        String args[] = {"", "",   "", "--", inputFile1.getPath() };
        inputFile1.delete();
        Main.main(args);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #8
    @Test
    public void mainTest10() throws Exception{
        File inputFile1 = createTmpFile();
        //              OPT <from> <to>  --   <filename> [<filename>]*
        String args[] = {"-f",          "--", inputFile1.getPath() };
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #9
    @Test
    public void mainTest11() throws Exception{
        File inputFile1 = createTmpFile();
        //               OPT <from> <to>  --   <filename> [<filename>]*
        String args[] = {"-f", "test",  "--", inputFile1.getPath() };
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #10
    @Test
    public void mainTest12() throws Exception{
        File inputFile1 = createTmpFile();
        //               OPT     <from>    <to>             --   <filename> [<filename>]*
        String args[] = {"-f", "test1", "test2", "test3",  "--", inputFile1.getPath() };
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #12
    @Test
    public void mainTest13() throws Exception{
        File inputFile1 = createTmpFile();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"-t", "fromTest", "toTest", "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #13
    @Test
    public void mainTest14() throws Exception {
        File inputFile1 = createInputFile1();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" , "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #14
    @Test
    public void mainTest15() throws Exception {
        File inputFile1 = createInputFile1();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #15
    @Test
    public void mainTest16() throws Exception {
        File inputFile1 = createInputFile1();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #16
    @Test
    public void mainTest17() throws Exception {
        File inputFile1 = createInputFile1();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
    Newly created test case.
    Purpose: testing the -b option
    I realized the -b option wasn't included in my catpart.txt.tsl, big mess up...
     */
    @Test
    public void mainTest18() throws Exception {
        File inputFile1 = createInputFile1();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-b" , "Howdy", "Hello", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #17
    @Test
    public void mainTest19() throws Exception {
        File inputFile1 = createInputFile4();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l", "time", "TEST", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Yo, Taylor," +
                "I'm really happy for you and I'mma let you finish, " +
                "but Beyonce had one of the best videos of all TEST! " +
                "One of the best videos of all TEST!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #18
    @Test
    public void mainTest20() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {          "!@#$", "TEST",   "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }


    //Implementation of test frame #19
    @Test
    public void mainTest21() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "!@#$", "TEST", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "TEST %^&* 1234 5678 12#$ !@34";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #20
    @Test
    public void mainTest22() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "!@#$", "TEST", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "TEST %^&* 1234 5678 12#$ !@34";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #21
    @Test
    public void mainTest23() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "12#$", "TEST", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 TEST !@34";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
    Newly created test case.
    Purpose: testing the -b option with special characters
    I realized the -b option wasn't included in my catpart.txt.tsl, big mess up...
     */
    @Test
    public void mainTest24() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-b" , "!@#$", "!@#$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ !@34";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #22
    @Test
    public void mainTest25() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "!@#$", "TEST", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "TEST %^&* 1234 5678 12#$ !@34";
        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #23
    @Test
    public void mainTest26() throws Exception {
        File inputFile1 = createInputFile5();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {         "12#$", "TEST",   "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #24
    @Test
    public void mainTest27() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "$31ls", "Sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She Sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #25
    @Test
    public void mainTest28() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "$31ls", "Sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she Sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #26
    @Test
    public void mainTest29() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "$31ls", "Sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She Sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she Sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    /*
    Newly created test case.
    Purpose: testing the -b option with combination of alphanumeric and special characters
    I realized the -b option wasn't included in my catpart.txt.tsl, big mess up...
     */
    @Test
    public void mainTest30() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-b" , "$31ls", "$31ls", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #27
    @Test
    public void mainTest31() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "$31ls", "Sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She Sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she Sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #28
    @Test
    public void mainTest32() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" ,   "$31ls", "Sel1$", "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #29
    @Test
    public void mainTest33() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "shells", "smells", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea smells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #30
    @Test
    public void mainTest34() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "shells", "smells", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore smells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #31
    @Test
    public void mainTest35() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "shells", "smells", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea smells by the sea shore. !@#$\n" +
                "The smells she sells are surely seashells. !!!!\n" +
                "So if she sells smells on the seashore, ????\n" +
                "I'm sure she $31ls seashore smells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #32
    @Test
    public void mainTest36() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "shells", "smells", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea smells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore smells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #33
    @Test
    public void mainTest37() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" ,   "!@#$", "!@#$", "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #34
    @Test
    public void mainTest38() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "!@#$", "%^&*", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. %^&*\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #35
    @Test
    public void mainTest39() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "!@#$", "%^&*", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. %^&*";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #36
    @Test
    public void mainTest40() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "!@#$", "%^&*", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. %^&*\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. %^&*";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #37
    @Test
    public void mainTest41() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l", "!@#$", "%^&*", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. %^&*\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. %^&*";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #38
    @Test
    public void mainTest42() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" ,   "!@#$", "!@#$", "--", inputFile1.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #39
    @Test
    public void mainTest43() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "$31ls", "sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #40
    @Test
    public void mainTest44() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "$31ls", "sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #41
    @Test
    public void mainTest45() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "$31ls", "sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #42
    @Test
    public void mainTest46() throws Exception {
        File inputFile1 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l", "$31ls", "sel1$", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "She sel1$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she sel1$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    //Implementation of test frame #43
    @Test
    public void mainTest47() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();

        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #44
    @Test
    public void mainTest48() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #45
    @Test
    public void mainTest49() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #46
    @Test
    public void mainTest50() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    /*
    Newly created test case.
    Purpose: testing the -b option
    I realized the -b option wasn't included in my catpart.txt.tsl, big mess up...
     */
    @Test
    public void mainTest51() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-b", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #47
    @Test
    public void mainTest52() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String expected2 = "Hello Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #48
    @Test
    public void mainTest53() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" , "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #49
    @Test
    public void mainTest54() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "!@#$", "$#@!", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "$#@! %^&* 1234 5678 12#$ !@34";

        String expected2 = "She $31ls sea shells by the sea shore. $#@!\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #50
    @Test
    public void mainTest55() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "!@#$", "$#@!", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ $#@!";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. $#@!";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #51
    @Test
    public void mainTest56() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "!@#$", "$#@!", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "$#@! %^&* 1234 5678 12#$ $#@!";

        String expected2 = "She $31ls sea shells by the sea shore. $#@!\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. $#@!";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    /*
    Newly created test case.
    Purpose: testing the -b option
    I realized the -b option wasn't included in my catpart.txt.tsl, big mess up...
     */
    @Test
    public void mainTest57() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "!@#$", "!@#$", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ !@#$";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #52
    @Test
    public void mainTest58() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "!@#$", "!@#$", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ !@#$";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #53
    @Test
    public void mainTest59() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" , "", "", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #54
    @Test
    public void mainTest60() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f" , "12#$", "!!!111", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 !!!111 !@34";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #55
    @Test
    public void mainTest61() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l" , "$31ls", "!!!111", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ !@34";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she !!!111 seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #56
    @Test
    public void mainTest62() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-i" , "!@#$", "!!!111", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!!!111 %^&* 1234 5678 12#$ !@34";

        String expected2 = "She $31ls sea shells by the sea shore. !!!111\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !!!111";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #57
    @Test
    public void mainTest63() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "-l" , "$31ls", "1234!@#$", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@#$ %^&* 1234 5678 12#$ !@34";

        String expected2 = "She 1234!@#$ sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she 1234!@#$ seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #58
    @Test
    public void mainTest64() throws Exception {
        File inputFile1 = createInputFile5();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>      --   <filename> [<filename>]*
        String args[] = {"" , "", "", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    //Implementation of test frame #59
    @Test
    public void mainTest65() throws Exception {
        File inputFile1 = createInputFile4();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-f", "the", "THEE", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Yo, Taylor," +
                "I'm really happy for you and I'mma let you finish, " +
                "but Beyonce had one of THEE best videos of all time! " +
                "One of the best videos of all time!";

        String expected2 = "She $31ls sea shells by THEE sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    //Implementation of test frame #60
    @Test
    public void mainTest66() throws Exception {
        File inputFile1 = createInputFile4();
        File inputFile2 = createInputFile6();
        //               OPT     <from>     <to>   --   <filename> [<filename>]*
        String args[] = {"-l", "the", "THEE", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Yo, Taylor," +
                "I'm really happy for you and I'mma let you finish, " +
                "but Beyonce had one of the best videos of all time! " +
                "One of THEE best videos of all time!";

        String expected2 = "She $31ls sea shells by the sea shore. !@#$\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on THEE seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@#$";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected2, actual2);
        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }


    @Test
    public void mainTest67() throws Exception {
        File inputFile1 = createInputFile4();
        File inputFile2 = createInputFile4();
        //               OPT     <fromStringArgument>     <toStringArgument>   --   <filename> [<filename>]*
        String args[] = {"-l", "the", "THEE", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Yo, Taylor," +
                "I'm really happy for you and I'mma let you finish, " +
                "but Beyonce had one of the best videos of all time! " +
                "One of THEE best videos of all time!";



        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The inputFiles differ!", expected1, actual1);
        assertEquals("The inputFiles differ!", expected1, actual2);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }
	
	
	   // Actual test cases

    @Test
    public void mainTest70() {
        String args[] = {"-a", "-b"};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void mainTest71() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"Howdy", "Hello", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Hello Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest72() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"howdy", "Hello", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"Hello bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest73() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-i", "howdy", "Hello", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Hello Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"Hello bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest74() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-f", "-l", "-i", "Bill", "William", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy William," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy William\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest75() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"lines" + System.lineSeparator() + "so that ", "lines." +
                System.lineSeparator() + "In this way, ", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines." + System.lineSeparator() +
                "In this way, we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest76() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-b", "Let's make sure", "Let's make sure", "--", inputFile.getPath()};
        Main.main(args);
        String expected = getFileContent(inputFile.getPath() + ".bck");
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest77() throws Exception {
        File inputFile = createInputFile11();
        String expected = getFileContent(inputFile.getPath());
        String args1[] = {"Let's make sure", "We hope", "--", inputFile.getPath()};
        Main.main(args1);
        String args2[] = {"We hope", "Let's make sure", "--", inputFile.getPath()};
        Main.main(args2);
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest78() throws Exception {
        File inputFile = createInputFile11();
        String args1[] = {"-b", "-f", "-i", "bill", "William", "--", inputFile.getPath()};
        Main.main(args1);
        String actual1 = getFileContent(inputFile.getPath());
        File inputFile2 = createInputFile11();
        String args2[] = {"-f", "-i", "-b", "bill", "William", "--", inputFile2.getPath()};
        Main.main(args2);
        String actual2 = getFileContent(inputFile.getPath());
        File inputFile3 = createInputFile11();
        String args3[] = {"-i", "-b", "-f", "bill", "William", "--", inputFile3.getPath()};
        Main.main(args3);
        String actual3 = getFileContent(inputFile.getPath());
        File inputFile4 = createInputFile11();
        String args4[] = {"-i", "-f", "-b", "bill", "William", "--", inputFile4.getPath()};
        Main.main(args4);
        String actual4 = getFileContent(inputFile.getPath());
        String expected = "Howdy William," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!";
        assertEquals("The files differ!", expected, actual1);
        assertEquals("The files differ!", expected, actual2);
        assertEquals("The files differ!", expected, actual3);
        assertEquals("The files differ!", expected, actual4);
        assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest79() throws Exception {
        File inputFile = createInputFile11();
        String expected = getFileContent(inputFile.getPath());
        String args[] = {"Let's", "Let us", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void mainTest80() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"s", "5", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bill," + System.lineSeparator() +
                "Thi5 i5 a te5t file for the replace utility" + System.lineSeparator() +
                "Let'5 make 5ure it ha5 at lea5t a few line5" + System.lineSeparator() +
                "5o that we can create 5ome intere5ting te5t ca5e5..." + System.lineSeparator() +
                "And let'5 5ay \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest81() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"l", "1", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bi11," + System.lineSeparator() +
                "This is a test fi1e for the rep1ace uti1ity" + System.lineSeparator() +
                "Let's make sure it has at 1east a few 1ines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And 1et's say \"howdy bi11\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest82() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-i", "l", "1", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bi11," + System.lineSeparator() +
                "This is a test fi1e for the rep1ace uti1ity" + System.lineSeparator() +
                "1et's make sure it has at 1east a few 1ines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And 1et's say \"howdy bi11\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest83() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-i", "-f", "let's", "Let us", "Let's", "let us", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let us make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let us say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest84() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void mainTest85() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-i", "-f", "the goal", "The objective", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The objective here is to replace string \"-i\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -i and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest86() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"the goal", "The objective", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The goal here is to replace string \"-f\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -f and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest87() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"--", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The goal here is to replace string \"-f\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -f and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest88() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-f", "--", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The goal here is to replace string \"-f\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -i and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest89() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-l", "--", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The goal here is to replace string \"-i\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -f and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest90() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-f", "-l", "--", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The goal here is to replace string \"-f\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -f and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest91() throws Exception {
        File inputFile = createInputFile18();
        String args[] = {"Let's have some numbers in the file: 12345678", "New content", "--",
                inputFile.getPath()};
        Main.main(args);
        String expected = "New content";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest92() throws Exception {
        File inputFile = createInputFile18();
        String args[] = {"Let's have some numbers in the file: 12345678", "", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest93() throws Exception {
        File inputFile = createInputFile18();
        String args[] = {"", "Hey", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Let's have some numbers in the file: 12345678";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void mainTest94() throws Exception {
        File inputFile = createInputFile18();
        String args[] = {"--", inputFile.getPath()};
        Main.main(args);
        String expected = "Let's have some numbers in the file: 12345678";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }

    @Test
    public void mainTest95() throws Exception {
        File inputFile = createInputFile11();
        inputFile.delete();
        String args[] = {"a", "b", "--", inputFile.getPath()};
        Main.main(args);
        assertEquals("File " + inputFile.getName() + " not found", errStream.toString().trim());
    }

    @Test
    public void mainTest96() throws Exception {
        File inputFile1 = createInputFile11();
        File inputFile2 = createInputFile12();
        File inputFile3 = createInputFile13();
        inputFile2.delete();

        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"Hello bill\" again!";
        String expected3 = "Hello Bill, have you learned your abc and 123?" + System.lineSeparator() +
                "It is important to know your abc and 123," +
                "so you should study it" + System.lineSeparator() +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected3, actual3);

        assertEquals("File " + inputFile2.getName() + " not found", errStream.toString().trim());

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void mainTest97() throws Exception {
        File inputFile1 = createInputFile11();
        File inputFile2 = createInputFile12();
        File inputFile3 = createInputFile13();
        inputFile1.delete();
        inputFile2.delete();

        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected3 = "Hello Bill, have you learned your abc and 123?" + System.lineSeparator() +
                "It is important to know your abc and 123," +
                "so you should study it" + System.lineSeparator() +
                "and then repeat with me: abc and 123";

        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected3, actual3);

        assertEquals("File " + inputFile1.getName() + " not found" + System.lineSeparator() +
                        "File " + inputFile2.getName() + " not found",
                errStream.toString().trim());

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
    }

    @Test
    public void mainTest98() throws Exception {
        File inputFile = createInputFile15();
        String args[] = {"not replaced", "replaced", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "First line: replaced" + System.lineSeparator() +
                "Second line: replaced" + System.lineSeparator() +
                "Third line: replaced" + System.lineSeparator() +
                "Last line: replaced" + System.lineSeparator();
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest99() throws Exception {
        File inputFile = createInputFile15();
        String args[] = {"-f", "not replaced", "replaced", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "First line: replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: not replaced" + System.lineSeparator();
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest100() throws Exception {
        File inputFile = createInputFile15();
        String args[] = {"-l", "not replaced", "replaced", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "First line: not replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: replaced" + System.lineSeparator();
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest101() throws Exception {
        File inputFile = createInputFile15();
        String args[] = {"-f", "-l", "not replaced", "replaced", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "First line: replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: replaced" + System.lineSeparator();
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest102() throws Exception {
        File inputFile = createInputFile15();
        String args[] = {"-l", "-f", "not replaced", "replaced", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "First line: replaced" + System.lineSeparator() +
                "Second line: not replaced" + System.lineSeparator() +
                "Third line: not replaced" + System.lineSeparator() +
                "Last line: replaced" + System.lineSeparator();
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest103() throws Exception {
        File inputFile = createInputFile16();
        String args[] = {"abab", "<repl>", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "<repl>ab";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest104() throws Exception {
        File inputFile = createInputFile16();
        String args[] = {"-l", "abab", "<repl>", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "ab<repl>";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest105() throws Exception {
        File inputFile = createInputFile16();
        String args[] = {"-f", "-l", "abab", "<repl>", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "<repl>ab";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest106() throws Exception {
        File inputFile = createInputFile16();
        String args[] = {"-f", "-f", "-f", "-l", "-l", "-l", "abab", "<repl>", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "<repl>ab";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest107() throws Exception {
        File inputFile = createInputFile16();
        String args[] = {"-l", "-f", "--", "abab", "<repl>", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "<repl>ab";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest108() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"Howdy", "Hello", "replace", "REPLACE", "sure it", "sure that it", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Hello Bill," + System.lineSeparator() +
                "This is a test file for the REPLACE utility" + System.lineSeparator() +
                "Let's make sure that it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest109() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"Howdy", "Hello", "Howdy", "Hey", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Hello Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest110() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-i", "Howdy", "Yo!", "howdy", "hello", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "Yo! Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "Let's make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And let's say \"Yo! bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest111() throws Exception {
        File inputFile = createInputFile17();
        String args[] = {"-f", "goal", "objective", "-i", "-f", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "The objective here is to replace string \"-f\" with" + System.lineSeparator() +
                "string \"-f\". Since we may also want to do multiple replacements," + System.lineSeparator() +
                "we will repeat the two strings here: -i and -f";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest112() throws Exception {
        File inputFile = createInputFile19();
        String args[] = {"--", "--", "++", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "++ ++ ++ ++";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest113() throws Exception {
        File inputFile = createInputFile19();
        String args[] = {"-f", "--", "--", "++", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "++ -- -- --";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest114() throws Exception {
        File inputFile = createInputFile19();
        String args[] = {"-l", "--", "--", "++", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "-- -- -- ++";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest115() throws Exception {
        File inputFile = createInputFile19();
        String args[] = {"-f", "-l", "--", "--", "++", "--", inputFile.getPath()};
        Main.main(args);
        String expected = "++ -- -- ++";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest116() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        File inputFile2 = temporaryFolder.newFile("_tmpfile.bck");
        inputFile1.deleteOnExit();
        inputFile2.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        FileWriter fileWriter2 = new FileWriter(inputFile2);
        fileWriter1.write("Content of file1");
        fileWriter2.write("Content of file2");
        fileWriter1.close();
        fileWriter2.close();

        String args1[] = {"Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);
        String args2[] = {"Content of ", "", "--", inputFile2.getPath()};
        Main.main(args2);

        String expected1 = "This is file1";
        String actual1 = getFileContent(inputFile1.getPath());
        String expected2 = "file2";
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile2.getPath())));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest117() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        File inputFile2 = temporaryFolder.newFile("_tmpfile.bck");
        inputFile1.deleteOnExit();
        inputFile2.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        FileWriter fileWriter2 = new FileWriter(inputFile2);
        fileWriter1.write("Content of file1");
        fileWriter2.write("Content of file2");
        fileWriter1.close();
        fileWriter2.close();

        String args1[] = {"-b", "Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);

        String expected1 = "Content of file1";
        String actual1 = getFileContent(inputFile1.getPath());

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertEquals("Not performing replace for " + inputFile1.getName() + ": Backup file already exists", errStream.toString().trim());
    }

    @Test
    public void mainTest118() throws Exception {
        File inputFile1 = temporaryFolder.newFile("_tmpfile");
        inputFile1.deleteOnExit();
        FileWriter fileWriter1 = new FileWriter(inputFile1);
        fileWriter1.write("Content of file1");
        fileWriter1.close();

        String args1[] = {"-b", "Content of ", "This is ", "--", inputFile1.getPath()};
        Main.main(args1);
        String args2[] = {"Content of file1", "This is a hack...", "--", inputFile1.getPath() + ".bck"};
        Main.main(args2);

        String expected1 = "This is file1";
        String actual1 = getFileContent(inputFile1.getPath());
        String expected2 = "This is a hack...";
        String actual2 = getFileContent(inputFile1.getPath() + ".bck");

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath())));
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest119() throws Exception {
        File inputFile = temporaryFolder.newFile("_tmpfile.bck");
        inputFile.deleteOnExit();
        FileWriter fileWriter = new FileWriter(inputFile);
        fileWriter.write("Content of file");
        fileWriter.close();

        String args[] = {"-b", "Content of ", "", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "file";
        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }

    @Test
    public void mainTest120() throws Exception {
        File inputFile = createInputFile11();
        String args[] = {"-i", "let's", "Let us", "let us", "we will", "--", inputFile
                .getPath()};
        Main.main(args);
        String expected = "Howdy Bill," + System.lineSeparator() +
                "This is a test file for the replace utility" + System.lineSeparator() +
                "we will make sure it has at least a few lines" + System.lineSeparator() +
                "so that we can create some interesting test cases..." + System.lineSeparator() +
                "And we will say \"howdy bill\" again!";
        String actual = getFileContent(inputFile.getPath());
        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
        assertEquals("", errStream.toString().trim());
    }




















}
