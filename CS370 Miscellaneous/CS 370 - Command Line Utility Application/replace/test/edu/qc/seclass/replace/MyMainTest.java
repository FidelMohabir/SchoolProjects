package edu.qc.seclass.replace;

import org.junit.*;
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

    // SOME UTILITIES

    // File createTmpFile() 
    // Creates a ​File​ object for a new temporary file in a platform independent way. 
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

    //String getFileContent(String filename) 
    //Returns a ​String​ object with the content of the specified file, 
    //which is useful for checking the outcome of a test case.
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
        String args[] = {"-f", "-l" , "!@#$", "!@34", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "!@34 %^&* 1234 5678 12#$ !@34";

        String expected2 = "She $31ls sea shells by the sea shore. !@34\n" +
                "The shells she sells are surely seashells. !!!!\n" +
                "So if she sells shells on the seashore, ????\n" +
                "I'm sure she $31ls seashore shells. !@34";

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






}
