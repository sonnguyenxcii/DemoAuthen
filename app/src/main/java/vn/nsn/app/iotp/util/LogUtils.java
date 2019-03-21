package vn.nsn.app.iotp.util;

import android.os.Environment;
import android.util.Log;

import com.centagate.module.common.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
    private static String filePath;
    private static String LOG_FILE_NAME = "ocb";

    public static void generateLog() {

        File logFolder = new File(Environment.getExternalStorageDirectory(), "ocb");
        if (!logFolder.exists()) {
            logFolder.mkdir();
        }
        String filename = "myapp_log_" + new Date().getTime() + ".log";

        File logFile = new File(logFolder, filename);

        try {
            String[] cmd = new String[]{"logcat", "-f", logFile.getAbsolutePath(), "-v", "time", "ActivityManager:W", "myapp:D"};
            Runtime.getRuntime().exec(cmd);
//            Toaster.shortDebug("Log generated to: " + filename);
//            return logFile;
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

//        return null;
    }


    public static void printLog() {
//        generateLog();
//        Process logcat;
//        final StringBuilder log = new StringBuilder();
//        try {
//            logcat = Runtime.getRuntime().exec(new String[]{"logcat", "-d"});
//            BufferedReader br = new BufferedReader(new InputStreamReader(logcat.getInputStream()), 4 * 1024);
//            String line;
//            String separator = System.getProperty("line.separator");
//            while ((line = br.readLine()) != null) {
//                log.append(line);
//                log.append(separator);
//            }
//            logToFile(log.toString());
//        } catch (Exception e) {
////            e.printStackTrace();
//        }
    }

    public static void logToFile(String message) {
        OutputStream out = null;
        if (message != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dateChar = formatter.format(date);
            message = dateChar + message;
            message = message + "\n\r";
//            if (filePath == null) {
//                filePath = findLastLogFilePath(Environment.getExternalStorageDirectory() + File.separator + "Ocb" + File.separator);
//            }
////
//            File logFile = new File(filePath);
//            if (!Utils.isParentPathExist(logFile)) {
//                Utils.createPath(logFile);
//            } else if (Utils.isFileExist(logFile, false) && logFile.length() + (long) message.length() > 1000000L) {
//                filePath = GetNextLogFilePath(Environment.getExternalStorageDirectory() + File.separator + "Ocb" + File.separator);
//                logFile = new File(filePath);
//            }
            String filename = "iotp.log";
            File logFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "Ocb" + File.separator);

            File logFile = new File(logFolder, filename);
            try {
                out = new BufferedOutputStream(new FileOutputStream(logFile, true));
                out.write(message.getBytes());
                out.flush();
            } catch (IOException var12) {
                Log.e("Centagate", "Failed write log file");
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException var11) {

                }

            }

        }
    }

    private static String findLastLogFilePath(String path) {
        int i = 0;
        String lastLogFileName = "";
        if (!path.trim().endsWith(File.separator)) {
            path = path + File.separator;
        }

        while (true) {
            String tmpFileName = path + LOG_FILE_NAME + "_" + i + ".log";
            if (!Utils.isFileExist(tmpFileName, false)) {
                if (i == 0) {
                    lastLogFileName = tmpFileName;
                }

                return lastLogFileName;
            }

            lastLogFileName = tmpFileName;
            ++i;
        }
    }

    private static String GetNextLogFilePath(String path) {
        int i = 0;
        String newLogFilePath = "";
        if (!path.trim().endsWith(File.separator)) {
            path = path + File.separator;
        }

        while (true) {
            String tmpFileName = path + LOG_FILE_NAME + "_" + i + ".log";
            if (!Utils.isFileExist(tmpFileName, false)) {
                return tmpFileName;
            }

            ++i;
        }
    }
}
