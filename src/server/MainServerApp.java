package server;

import common.utility.Printer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainServerApp {
    public static Logger LOGGER;

    public static void main(String[] args) {
        setLogger();
        Server server = new Server();
        server.run();
    }

    public static void setLogger() {
        Printer printer = new Printer();
        String file = System.getenv("ConfigFile");
        File srcFile = new File(file);
        try {
            FileInputStream fis = new FileInputStream(srcFile.getAbsoluteFile());
            LogManager.getLogManager().readConfiguration(fis);
            LOGGER = Logger.getLogger(MainServerApp.class.getSimpleName());
        } catch (FileNotFoundException ex) {
            printer.printError("Файл для логирования не найден");
        } catch (IOException ex) {
            printer.printError("Произошла ошибка чтения конфигурации файла для логирования");
        }
    }
}
