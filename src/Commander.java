import Entidades.Movie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Scanner;

public class Commander {
    static Hashtable<Long, Movie> movieHashtable = new Hashtable<Long, Movie>();
    static String fileName;
    static String initialization;

    /**
     * Handles the commands written by the user
     * @param args environment variable.the filename
     * @throws IOException if there is an interruption or failed process
     */
    public static void handleConsoleCommand(String[] args) throws IOException {
        fileName = getFileName(args);
        if (fileName == null) return;
        StringBuilder stringBuilder = new StringBuilder();
        Scanner keyboard = new Scanner(System.in);
        String currentCommand;
        System.out.println("Enter the command: ");
        while (continuar(stringBuilder)) {
            currentCommand = stringBuilder.toString();
            stringBuilder.delete(0, currentCommand.length());
            String[] parts = currentCommand.split(" ");
            switch (parts[0]) {
                case "help":
                    CollectionManager.help();
                    break;
                case "info":
                    CollectionManager.info(initialization);
                    break;
                case "show":
                    CollectionManager.show(movieHashtable);
                    break;
                case "clear":
                    movieHashtable = CollectionManager.clear();
                case "save":
                    CollectionManager.save(fileName, movieHashtable);
                    break;
                case "remove_key":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the key.");
                        break;
                    }
                    Long key;
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.remove(key, movieHashtable);
                    break;
                case "execute_script":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the filename that contain the commands.");
                        break;
                    }
                    CollectionManager.executeScript(parts[1], movieHashtable, initialization, fileName);
                    break;
                case "replace_if_greater":
                    if (parts.length < 3) {
                        System.out.println("The command is incomplete, you need to enter the key and the age.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    long oscarsCount;
                    try {
                        oscarsCount = Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.replaceIfGreater(key, oscarsCount, movieHashtable);
                    break;
                case "replace_if_lower":
                    if (parts.length < 3) {
                        System.out.println("The command is incomplete, you need to enter the key and the age.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    try {
                        oscarsCount = Long.parseLong(parts[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.replaceIfLower(key, oscarsCount, movieHashtable);
                    break;
                case "remove_greater_key":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the key.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.remove_greater_key(key, movieHashtable);
                    break;
                case "count_less_than_oscars_count":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the value.");
                        break;
                    }
                    long value;
                    try {
                        value = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.count_less_than_oscars_count(value, movieHashtable);
                    break;
                case "print_ascending":
                    CollectionManager.print_ascending(movieHashtable);
                    break;
                case "print_field_descending_oscars_count":
                    CollectionManager.print_field_descending_oscars_count(movieHashtable);
                    break;
                case "insert":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the key.");
                        break;
                    }
                    try {
                        key = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.insert(keyboard, movieHashtable, key);
                    break;
                case "update":
                    if (parts.length < 2) {
                        System.out.println("The command is incomplete, you need to enter the key.");
                        break;
                    }
                    long id;
                    try {
                        id = Long.parseLong(parts[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The command is invalid.");
                        break;
                    }
                    CollectionManager.update(id, keyboard, movieHashtable);
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
            System.out.println("Enter the new command:");

        }
        System.out.println("Goodbye.");
    }

    /**
     * Gets the file name and read its content, otherwise closes the program
     *
     * @param args environment variable.the filename
     * @return returns the file name
     */
    private static String getFileName(String[] args) {
        String fileName;
        if (args.length == 0) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter file name: ");
            fileName = keyboard.nextLine();
        } else {
            fileName = args[0];
        }
        File f = new File(fileName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (!f.exists()) {
            System.out.println("The file doesn't exists.");
            return null;
        } else {
            movieHashtable = new CsvReader().read(fileName);
            if (movieHashtable == null) {
                System.out.println("There was a problem reading the file.");
                return null;
            }
            initialization = sdf.format(f.lastModified());
        }
        return fileName;
    }

    /**
     * Reads the commands and sends them to process
     * @param commando StringBuilder
     * @return returns true if  the command is different from exit.
     */
    static boolean continuar(StringBuilder commando) {
        Scanner keyboard = new Scanner(System.in);
        String currentCommand = keyboard.nextLine();
        if (currentCommand.isEmpty()) {
            currentCommand = null;
        }
        if (currentCommand == null) {
            return true;
        }
        commando.append(currentCommand);
        return !Objects.equals(currentCommand, "exit");
        // si es  exit es !true osea false y se termina el loop, su no es exit es !false  por lo que es true

    }
}

