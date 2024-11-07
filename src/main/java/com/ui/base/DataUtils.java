package com.ui.base;

import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log
public class DataUtils {
    private static final String configPropertyPath = "/config.properties";
    private static final String EncryptionYaml = "/properties/Encryption_test.yaml";
    private static Properties props = null;
    private static String OS = null;

    /**
     * Read values from properties file
     *
     * @param propertyName Property key to read value for
     * @return Return read value of property key
     */
    public static String getPropertyValue(String propertyName) {
        try {
            props = getAllProperty(configPropertyPath, EncryptionYaml);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return props.getProperty(propertyName);
    }

    /**
     * Get current date in simple format. Eg : Tuesday, September 16, 2019
     *
     * @return Today's date in simple format
     */
    public static String getCurrentSimpleDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH);
        Date date = new Date();
        return df.format(date);
    }

    /**
     * Get current date in simple format. Eg : yyyy-MM-dd
     *
     * @return mentioned date format
     */
    public static String getLocalDate(String dateFormat) {
        LocalDateTime ldt = LocalDateTime.now();
        return  DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH).format(ldt);
    }


    /**
     * Return Custom Date by adding number days passed as argument
     *
     * @param days Number of days to add to current date
     * @return Return new date after adding number of days passed in argument
     */
    public static String getCustomDate(int days) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        Date cusDate = cal.getTime();
        return df.format(cusDate);
    }

    public static void executeShellScript(String pathToFile) {
        Process p;
        try {
            p = Runtime.getRuntime().exec("sudo bash " + pathToFile);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void scrollToTopOfPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollTop)");

    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void typeKeys(String data) {
        // TODO Handle other Operating Systems
        Robot robot;
        try {
            robot = new Robot();
            StringSelection stringSelection = new StringSelection(data);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);
            clear();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            sleep(1);
            pressEnter();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        // TODO Handle other Operating Systems
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_A);
            robot.keyPress(KeyEvent.VK_DELETE);
            robot.keyRelease(KeyEvent.VK_DELETE);
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            sleep(1);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void pressEnter() {
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static void pasteItem() {
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void zoomOutTo80Percent() {
        Robot robot;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static void pressDownArrow(int noOfTimesToPress) {
        for (int i = 0; i <= noOfTimesToPress; i++) {
            Robot robot;
            try {
                robot = new Robot();
                robot.keyPress(KeyEvent.VK_DOWN);
                robot.keyRelease(KeyEvent.VK_DOWN);
                sleep(300);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load The Properties files into project
     *
     * @param fileNames Property File name
     * @return Returns the all the loaded files
     * @throws Exception
     */
    public static Properties getAllProperty(String... fileNames) throws Exception {
        DataUtils portalUtils = new DataUtils();
        if (props == null) {
            props = new Properties();
            for (String fileName : fileNames) {
                InputStream file = portalUtils.getFileFromResources(fileName);
                if (file != null) {
                    props.load(file);
                    log.info("File with name " + fileName + " Loaded");
                } else {
                    log.info("No File with name " + fileName + " Loaded");
                }
            }
        }
        return props;
    }

    /**
     * @param fileName gets file from classpath, resources folder
     * @return returns the File object
     */
    public InputStream getFileFromResources(String fileName) {
        return getClass().getResourceAsStream(fileName);

    }

    public String getFilePath(String fileName) {
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        return file.getAbsolutePath();
    }

    public String getFileLocation(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String absolutePath = file.getAbsolutePath();

        return absolutePath;
    }

    public static String readFile(String pathToFile, String fileName) {
        Path fullPath = Paths.get(pathToFile, fileName);

        try {
            String contentsOfFile = Files.lines(fullPath).collect(Collectors.joining("\n"));
            log.info("Input File Path  " + fullPath);
            return contentsOfFile;
        } catch (IOException e) {
            log.info(fullPath.toString());
        }
        return "";
    }
    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }
    public static boolean isWindows() {
        return getOsName().toUpperCase().contains("WIN");
    }

    public static boolean isUnix() {
        return getOsName().toUpperCase().contains("UNIX");
    }

    public static void pressEsc() {
        Robot robot;
        try {
            robot = new Robot();
            robot.delay(400);
            robot.keyPress(KeyEvent.VK_ESCAPE);
            sleep(2);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static File getLatestFileFromDownloadsList(String directoryFilePath) {
        {
            File directory = new File(directoryFilePath);
            File[] files = directory.listFiles(File::isFile);
            long lastModifiedTime = Long.MIN_VALUE;
            File latestFile = null;

            if (files != null) {
                for (File file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        latestFile = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }
            log.info("Latest file from Directory " +  latestFile.getAbsolutePath());
            return latestFile;
        }
    }
    public static void deleteFilesFromDirectory(String directoryFilePath) {
        {
            File directory = new File(directoryFilePath);
            File[] files = directory.listFiles(File::isFile);
            if (files != null) {
                for (File file : files) {
                    try {
                        FileUtils.forceDelete(file);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    public static String waitTillFileDownloads(String downloadDir, String fileExtension, long timeOut) {
        String downloadedFileName = null;
        boolean valid = true;
        boolean found = false;

        try {
            Path downloadFolderPath = Paths.get(downloadDir);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            long startTime = System.currentTimeMillis();

            do {
                WatchKey watchKey;
                watchKey = watchService.poll(timeOut, TimeUnit.SECONDS);
                long currentTime = (System.currentTimeMillis() - startTime) / 1000;
                if (currentTime > timeOut) {
                    log.info("Download operation timed out.. Expected file was not downloaded");
                    return downloadedFileName;
                }
                for (WatchEvent event : watchKey.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        String fileName = event.context().toString();
                        if (fileName.endsWith(fileExtension)) {
                            downloadedFileName = fileName;
                            log.info("Downloaded file found with extension " + fileExtension + ". File name is " + fileName);
                            Thread.sleep(500);
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    return downloadedFileName;
                } else {
                    currentTime = (System.currentTimeMillis() - startTime) / 1000;
                    if (currentTime > timeOut) {
                        log.info("Failed to download expected file");
                        return downloadedFileName;
                    }
                    valid = watchKey.reset();
                }
            } while (valid);
        } catch (InterruptedException e) {
            log.info("Interrupted error - " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            log.info("Download operation timed out.. Expected file was not downloaded");
        } catch (Exception e) {
            log.info("Error occured - " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedFileName;
    }
    public static String getCurrentQuarter() {
        Calendar c = Calendar.getInstance(Locale.US);
        int month = c.get(Calendar.MONTH);
        return (month >= Calendar.JANUARY && month <= Calendar.MARCH) ? "Q1" :
                (month >= Calendar.APRIL && month <= Calendar.JUNE) ? "Q2" :
                        (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ? "Q3" :
                                "Q4";
    }
    public static String getLastDayOfQuarter() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        String year = Integer.toString(c.get(Calendar.YEAR));
        return (month >= Calendar.JANUARY && month <= Calendar.MARCH) ? year+"03-31" :
                (month >= Calendar.APRIL && month <= Calendar.JUNE) ? year+"06-30" :
                        (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) ? year+"09-30" :
                                year+"12-31";
    }
}
