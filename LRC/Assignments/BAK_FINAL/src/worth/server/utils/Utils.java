package worth.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

/**
 * Set of utils for the server
 */
public class Utils {

    /**
     * Command Line argument parser helper, every recognized command will store it's value in a Const parameter.
     * @param args main arguments
     */
    public static void parseArgs(String[] args) {
        try {
            CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            System.err.println("Error while trying to  parse arguments, exiting");
            System.exit(-1);
        }
    }

    // Creating folders where persistent information will be stored
    public static void setupEnviroment() {
        File resDir = new File(Const.RES_FOLDER);
        if(!resDir.exists())
            resDir.mkdir();
        File projDir = new File(Const.PROJECTS_FOLDER);
        if(!projDir.exists())
            projDir.mkdir();
    }

    // Self explanatory
    public static void clearBuf(ByteBuffer buf) {
        buf = ByteBuffer.allocate(Const.BYTEBUF_SIZE);
    }

    /**
     * Read all contents of a file given a filename
     * @param filename the name of the file to read
     * @return {@code File(filename) contents}
     * @throws IOException
     */
    public static String readFile(String filename) throws IOException {
        File f = new File(filename);
        if(!f.isFile()) throw new FileNotFoundException();
        FileInputStream fis = new FileInputStream(f);
        byte[] read = fis.readAllBytes();
        fis.close();
        return new String(read);
    }
}