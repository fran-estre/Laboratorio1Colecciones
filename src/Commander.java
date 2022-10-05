import Entidades.Movie;

import java.util.Hashtable;
import java.util.Scanner;

public class Commander {
    static Hashtable<Long, Movie> movieHashtable = new Hashtable<Long, Movie>();
    static String fileName;
    static String initialization;

    public static void handleConsoleCommand() {
        CollectionManager manager = new CollectionManager();
        Scanner keyboard = new Scanner(System.in);
        String currentCommand;
        System.out.println("Enter the command: ");
        while ((currentCommand = keyboard.nextLine()) != "exit") {
            String[] parts = currentCommand.split(" ");
            switch (parts[0]) {
                case "help":
                    manager.help();
                    break;
                case "info":
                    manager.info(movieHashtable, initialization);
                    break;
                case "show":
                    CollectionManager.show(movieHashtable);
                    break;
                case "clear":
                    CollectionManager.clear(movieHashtable);
                case "save":

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

                    break;
                case "count_less_than_oscars_count":
                    break;
                case "print_ascending":
                    break;
                case "print_field_descending_oscars_count":
                    break;
                case "insert":

                    break;
                case "update":
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

                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
            System.out.println("Enter the new command: ");
        }
        System.out.println("Goodbye.");
    }

}

