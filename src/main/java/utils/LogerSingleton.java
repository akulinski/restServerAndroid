package utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogerSingleton {

    private FileHandler fileHandler = null;

    public Logger getLogger() {
        return logger;
    }

    private Logger logger;
    private static LogerSingleton instance = null;

    private LogerSingleton() {

        logger = Logger.getLogger(LogerSingleton.class.getName());
        setUpLogger();

    }

    public static synchronized LogerSingleton getInstance() {

        if (instance == null) {
            instance = new LogerSingleton();
        }

        return instance;
    }

    private void setUpLogger() {
        try {
            fileHandler = new FileHandler("logs.log");
            logger.addHandler(fileHandler);

            SimpleFormatter formatter = new SimpleFormatter();

            fileHandler.setFormatter(formatter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
