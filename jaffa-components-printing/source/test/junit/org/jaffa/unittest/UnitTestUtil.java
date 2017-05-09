package org.jaffa.unittest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.Parser;
import org.jaffa.util.OsScriptingBean;
import org.jaffa.util.URLHelper;


public class UnitTestUtil {
    
    private static final Logger log = Logger.getLogger(UnitTestUtil.class);

    private static final String JUNIT_PROPERTIES = "JUnit.properties";
    private static final String JUNIT_DATA_DIRECTORY = "junit.data.directory";
    private static final String JUNIT_TEMP_DIRECTORY = "junit.temp.directory";
    private static final String JUNIT_IMAGE_COMPARE = "junit.imagemagic.compare.command";
    private static final String JUNIT_IMAGE_CONVERT = "junit.imagemagic.convert.command";
    private static final String JUNIT_GHOSTSCRIPT = "junit.ghostview.command";
    
    private static Properties c_prop = null;
    
    private static void loadProperties() {
        if (c_prop == null) {
            c_prop = new Properties();
            InputStream propFile = null;
            try{
                ClassLoader cl = UnitTestUtil.class.getClassLoader();
                propFile = cl.getResourceAsStream(JUNIT_PROPERTIES);
                c_prop.load(propFile);
            } catch(java.io.IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (propFile != null)
                        propFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public static String getDataDirectory()  {
        loadProperties();
        String dataDirectory = c_prop.getProperty(JUNIT_DATA_DIRECTORY, null);
        return dataDirectory != null ? dataDirectory.replace('\\', '/') : null;
    }
    
    public static String getTempDirectory()  {
        loadProperties();
        String directory = c_prop.getProperty(JUNIT_TEMP_DIRECTORY, System.getProperty("java.io.tmpdir"));
        return directory != null ? directory.replace('\\', '/') : null;
    }

    /** byte-by-byte comparison of two files. Returns true if they are identical */
    //@TODO, if these is used to compare text files, we should ignore line endings!
    public static boolean areFilesIdentical(File f1, File f2) throws Exception {
        if (!f1.exists() || !f2.exists())
            return false;
        
        FileReader fr1 = new FileReader(f1);
        FileReader fr2 = new FileReader(f2);
        try {
            long counter=0;
            int x,y;
            do {
                counter++;
                x=fr1.read();
                y=fr2.read();
                if(x==y && x==-1)
                    return true;
            } while(x==y);
            log.error("File compare failed at byte " + counter);
            return false;
        } finally {
            fr1.close();
            fr2.close();
        }
    }
    
    /** byte-by-byte comparison of two files. Returns true if they are identical */    
    public static boolean areFilesIdentical(String one, String two) throws Exception {
        File f1 = new File(one);
        File f2 = new File(two);
        return areFilesIdentical(f1, f2);
    }


    /** Use the ImageMagick product to compare two image files **/
    public static double areImagesSimilar(String one, String two) throws Exception {
        File f1 = new File(one);
        File f2 = new File(two);
        return areImagesSimilar(f1,f2);
    }
    
    /** Use the ImageMagick product to compare two image files **/
    public static double areImagesSimilar(File f1, File f2) throws Exception {
        log.debug("Running image compare on " + f1.getAbsolutePath() + " and " + f2.getAbsolutePath() );
        loadProperties();
        String compareCmd = c_prop.getProperty(JUNIT_IMAGE_COMPARE, null);
        if(compareCmd==null) {
            String err = "Can't Compare two images: ImageMagick must be installed an the following property set: " + JUNIT_IMAGE_COMPARE;
            log.fatal(err);
            throw new UnsupportedOperationException(err);
        }
        File diff = File.createTempFile("diff-",".jpg", new File(getTempDirectory()) );
        OsScriptingBean os = new OsScriptingBean();
        os.setCommand(new String[] {compareCmd, "-metric rmse", f1.getAbsolutePath(), f2.getAbsolutePath(), diff.getAbsolutePath()} );
        int exitCode = os.exec();
        if(exitCode<0) {
            log.error("OS command for image compare failed with error code : " + exitCode);
            log.error("Standard output was: " + os.getOutput());
            log.error("Standard error was:  " + os.getError());
            throw new Exception("OS Command failed. Script was " + os.getScriptFile().getAbsolutePath());
        }
        Pattern p = Pattern.compile("-?\\d+\\.?\\d*");
        Matcher m = p.matcher(os.getOutput());
        if(m.find() && m.start()==0) {
            log.debug("Image (Root Mean Squared) Difference="+m.group());
            double rms = Parser.parseDecimal(m.group());
            if(rms>0) {
                String convertCmd = c_prop.getProperty(JUNIT_IMAGE_CONVERT, null);
                if(convertCmd!=null) {
                    os = new OsScriptingBean();
                    os.setCommand(new String[] {convertCmd, f1.getAbsolutePath(), f2.getAbsolutePath(),
                              "-compose difference","-composite","-normalize",diff.getAbsolutePath()} );
                    log.debug("Running image difference on " + f1.getAbsolutePath() + " and " + f2.getAbsolutePath() );
                    exitCode = os.exec();
                    if(exitCode>=0) {
                        log.info("Images are not identical: See delta file @ " + diff.getAbsolutePath());
                    }
                }
            } else
                diff.delete();
            return rms;
        } else {
            log.error("Failed to understand image compare output: " + os.getOutput());
            throw new Exception("Failed to understand image compare output: " + os.getOutput());
        }
    }

    /** Use the GhostScript and ImageMagick product to compare two PDFs by converting them first to image files **/
    public static double arePdfsSimilar(File f1, File f2) throws Exception {
        try {
            f1=convertPdfToJpg(f1);
            f2=convertPdfToJpg(f2);
            log.debug("Converted PDF to images to compare on " + f1.getAbsolutePath() + " and " + f2.getAbsolutePath() );
            double rms = areImagesSimilar(f1, f2);
            log.debug("Root Mean Squared Error = " + rms);
            return rms;
        } finally {
            f1.delete();
            f2.delete();
        }
    }

    /** Use the GhostScript and ImageMagick product to compare two PDFs by converting them first to image files **/
    public static double arePdfsSimilar(String one, String two) throws Exception {
        File f1 = new File(one);
        File f2 = new File(two);
        return areImagesSimilar( convertPdfToJpg(f1), convertPdfToJpg(f2) );
    }

    /** Convert a PDF to a static image so it can be compared **/
    public static File convertPdfToJpg(File f) throws Exception {
        loadProperties();
        String gsCmd = c_prop.getProperty(JUNIT_GHOSTSCRIPT, null);
        if(gsCmd==null) {
            String err = "Can't Convert PDF: Ghostscript must be installed an the following property set: " + JUNIT_GHOSTSCRIPT;
            log.fatal(err);
            throw new UnsupportedOperationException(err);
        }
        File jpg = new File(f.getAbsolutePath()+".jpg");
        // Don't do this again if the it exists and the source file is still older.
        if(jpg.exists() && jpg.lastModified()>f.lastModified()) {
            log.info("Didn't reprocess JPEG file for " + f.getAbsolutePath());
            return jpg;
        }
        OsScriptingBean os = new OsScriptingBean();
        os.setCommand(new String[] {gsCmd,"-dSAFER","-dBATCH","-dNOPAUSE","-sDEVICE=jpeg",
                                    "-sOutputFile=\""+jpg.getAbsolutePath()+"\"","-c save pop","-f \""+f.getAbsolutePath()+"\""} );
        int exitCode = os.exec();
        if(exitCode!=0) {
            log.error("OS command for PDF to JPEG failed with error code : " + exitCode);
            log.error("Standard output was: " + os.getOutput());
            log.error("Standard error was:  " + os.getError());
            throw new Exception("OS Command failed. Script was " + os.getScriptFile().getAbsolutePath());
        }
        log.debug("Converted PDF to " + jpg.getAbsolutePath());
        return jpg;
    }

    
    /** byte-by-byte comparison of two files. Returns %age of bytes that match */
    public static double areFilesSimilar(File f1, File f2) throws Exception {
        if (!f1.exists() || !f2.exists())
            return 0.0;
        long size = Math.max(f1.length(),f2.length());
        long matches = 0;
        FileReader fr1 = new FileReader(f1);
        FileReader fr2 = new FileReader(f2);
        try {
            int x,y;
            do {
                x=fr1.read();
                y=fr2.read();
                if(y==-1 || x==-1)
                    break;
                if(x==y)
                    matches++;
            } while(true);
        } finally {
            fr1.close();
            fr2.close();
        }
        return (matches*1.0)/(size*1.0);
    }

    /** byte-by-byte comparison of two files. Returns true if they are identical */    
    public static double areFilesSimilar(String fileOne, String fileTwo) throws Exception {
        return areFilesSimilar(new File(fileOne) , new File(fileTwo) );
    }

}
