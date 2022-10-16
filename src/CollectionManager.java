import Entidades.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CollectionManager {

    /**
     * Shows the commands to the user
     */
    public static void help() {
        System.out.println("help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого\n" +
                "replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого\n" +
                "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный\n" +
                "count_less_than_oscars_count oscarsCount : вывести количество элементов, значение поля oscarsCount которых меньше заданного\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_field_descending_oscars_count : вывести значения поля oscarsCount всех элементов в порядке убывания");
    }

    /**
     * Gives information about the collection to the user
     *
     * @param movieHashtable collection
     * @param initialization initialization time
     */
    public static void info(Hashtable<Long, Movie> movieHashtable, String initialization) {
        System.out.println("type: Hashtable<Long, Movie>");
        System.out.println("initialization: " + initialization);
        System.out.println("length: " + movieHashtable.size());
    }

    /**
     * Shows the movies in the collection
     *
     * @param movieHashtable collection
     */
    public static void show(Hashtable<Long, Movie> movieHashtable) {
        movieHashtable.forEach((k, v) -> System.out.println("\n" + v.toString()));
    }

    /**
     * Clears the collection
     *
     * @return
     */
    static Hashtable<Long, Movie> clear() {
        Hashtable<Long, Movie> movieHashtable = new Hashtable<>();
        return movieHashtable;
    }

    /**
     * Remove an element from the collection by its key
     *
     * @param key            movie key
     * @param movieHashtable collection
     */
    static void remove(Long key, Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(key)) {
            movieHashtable.remove(key);
            System.out.println("The movie was removed.");
        } else
            System.out.println("The movie don't exists.");
    }

    /**
     * Replace value by key if new value is greater than old
     *
     * @param key            Movie key
     * @param oscarsCount    value to replace
     * @param movieHashtable collection
     */
    static void replaceIfGreater(Long key, long oscarsCount, Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(key))
            if (movieHashtable.get(key).getOscarsCount() < oscarsCount) {
                movieHashtable.get(key).setOscarsCount(oscarsCount);
                System.out.println("\nThe oscarsCount was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }

    /**
     * Replace value by key if new value is less than old
     *
     * @param key            Movie key
     * @param oscarsCount    value to replace
     * @param movieHashtable collection
     */
    static void replaceIfLower(Long key, long oscarsCount, Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(key))
            if (movieHashtable.get(key).getOscarsCount() > oscarsCount) {
                movieHashtable.get(key).setOscarsCount(oscarsCount);
                System.out.println("\nThe oscarsCount was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }

    /**
     * Display the number of elements whose oscarsCount field value is less than the given one
     *
     * @param num            the given value
     * @param movieHashtable collection
     */
    static void count_less_than_oscars_count(long num, Hashtable<Long, Movie> movieHashtable) {
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        movieHashtable.forEach((k, v) -> {
            if (v.getOscarsCount() == num)
                counter.getAndSet(counter.get() + 1);
        });
        System.out.println("\nThere are " + counter.get() + " movies with less than " + num + " oscars.");
    }

    /**
     * Add a new element with the given key
     *
     * @param keyboard       scanner
     * @param movieHashtable collection
     */
    static void insert(Scanner keyboard, Hashtable<Long, Movie> movieHashtable, long key) {
        Movie movie = createMovie(keyboard, movieHashtable);
        movieHashtable.put(key, movie);
    }

    static void update(Long id, Scanner keyboard, Hashtable<Long, Movie> movieHashtable) {
        for (Map.Entry<Long, Movie> entry : movieHashtable.entrySet()) {
            Movie movieA = movieHashtable.get(entry.getKey());
            if (movieA.getId() == id) {
                Movie movie = createMovie(keyboard, movieHashtable);
                movieHashtable.replace(entry.getKey(), movie);
                System.out.println("The movie was updated.");
                return;
            }
        }
        System.out.println("The movie don't exist.");
    }

    static void remove_greater_key(Long key, Hashtable<Long, Movie> movieHashtable) {
        Iterator<Map.Entry<Long, Movie>> it = movieHashtable.entrySet().iterator();
        Integer deleted = 0;
        while (it.hasNext()) {
            Map.Entry<Long, Movie> entry = it.next();
            if (entry.getKey() > key) {
                it.remove();
                deleted++;
            }
        }
        System.out.println("Deleted elements: " + deleted);
    }

    static void save(String fileName, Hashtable<Long, Movie> movieHashtable) throws IOException {
        FileOutputStream fout = new FileOutputStream(fileName);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        Iterator<Map.Entry<Long, Movie>> it = movieHashtable.entrySet().iterator();
        String movieString = "";
        while (it.hasNext()) {
            Map.Entry<Long, Movie> currentMovie = it.next();
            movieString += currentMovie.getKey() + "," + currentMovie.getValue().toCsv() + "\n";
        }

        byte b[] = movieString.getBytes();
        bout.write(b);
        bout.flush();
        bout.close();
        fout.close();
        System.out.println("Success");
    }

    private static Long getNewId(Hashtable<Long, Movie> movieHashtable) {
        Long maxKey = Long.valueOf(0);
        for (Map.Entry<Long, Movie> entry : movieHashtable.entrySet()) {
            if (entry.getKey() > maxKey) {
                maxKey = entry.getKey();
            }
        }
        maxKey++;
        return maxKey;
    }

    public static Movie createMovie(Scanner sc, Hashtable<Long, Movie> movieHashtable) {
        String message = "Introduce the name of the movie:";
        String name;
        do {
            name = readString(sc, message, true);
            if (name.isEmpty()) {
                System.out.println("The field cannot be empty.");
            }
        } while (name.isEmpty() == true);

        Coordinates coordinates = createCoordinates(sc);

        Date date;
        do {
            date = new Date();
            if (date == null) {
                System.out.println("The field cannot be null.");
            }
        } while (date == null);

        message = "Enter  the oscars count:";
        long oscarsCount;
        do {
            oscarsCount = readLong(sc, message, true);
            if (oscarsCount <= 0) {
                System.out.println("The value must be greater than 0");
            }
        } while (oscarsCount <= 0);


        message = "Enter the budget:";
        Integer budget;
        do {
            budget = readInt(sc, message, true);
            if (budget <= 0) {
                System.out.println("The value must be greater than 0");
            }
        } while (budget <= 0);

        message = "Enter the box office:";
        int totalBoxOffice;
        do {
            totalBoxOffice = readInt(sc, message, true);
            if (totalBoxOffice <= 0) {
                System.out.println("The value must be greater than 0");
            }
        } while (totalBoxOffice <= 0);
        MpaaRating rating = getMpaaRating(sc);
        Person operator = createPerson(sc);


        return new Movie(getNewId(movieHashtable), name, coordinates, date, oscarsCount, budget, totalBoxOffice, rating, operator);
    }

    private static MpaaRating getMpaaRating(Scanner sc) {
        System.out.println("Enter the Mpaa rating(  G, PG, PG_13, R, NC_17):");
        String rate = sc.next();
        List<MpaaRating> mpaaRatings = Arrays.asList(MpaaRating.values());
        MpaaRating rating = null;
        for (MpaaRating mpaaRating : mpaaRatings) {
            if (mpaaRating.name().equals(rate)) {
                rating = mpaaRating;
                return rating;
            }
        }
        System.out.println("The value that you entered was not correct.");
        return getMpaaRating(sc);
    }

    public static Coordinates createCoordinates(Scanner sc) {
        String message = "Introduce the coordinate x:";
        Double x = readDouble(sc, message, true);
        message = "Introduce the coordinate y:";
        float y = readFloat(sc, message, true);
        while (y <= -43) {
            System.out.println("You have entered a wrong value, you can only enter values greater than -43.");
            y = readFloat(sc, message, true);
        }
        return new Coordinates(x, y);
    }

    /**
     * Fills up a person object
     *
     * @param sc scanner
     * @return filled person
     */
    public static Person createPerson(Scanner sc) {
        String message = "Enter the person's name:";
        String name;
        do {
            name = readString(sc, message, true);
            if (name.isEmpty()) {
                System.out.println("The field cannot be empty.");
            }
        } while (name.isEmpty());

        message = "Enter the height of the person";
        Long height = readLong(sc, message, true);
        while (height <= 0) {
            System.out.println("You have entered a wrong value, you can only enter values greater than 0.");
            height = readLong(sc, message, true);
        }

        message = "Enter the person's passport id:";
        String passport;
        do {
            passport = readString(sc, message, true);
            if (passport.isEmpty()) {
                System.out.println("The field cannot be empty.");
            }
            if (passport.length() < 4) {
                System.out.println("The field length cannot be less than 4");
            }
        } while (passport.isEmpty() || passport.length() < 4);

        Color eye = createEye(sc);

        Location location = createLocation(sc);

        return new Person(name, height, passport, eye, location);
    }

    /**
     * Fills a location with its values
     *
     * @param sc scanner
     * @return filled location
     */
    private static Location createLocation(Scanner sc) {
        String message = "Introduce the Location coordinate x:";
        Long x = readLong(sc, message, true);
        message = "Introduce the Location coordinate y:";
        float y = readFloat(sc, message, true);
        message = "Introduce the Location coordinate z:";
        Float z = readFloat(sc, message, true);
        message = "Introduce the Location name:";
        String name = readString(sc, message, true);

        return new Location(x, y, z, name);
    }

    /**
     * Chooses a constant from EColor
     *
     * @param sc scanner
     * @return a constant from a color enum
     */
    private static Color createEye(Scanner sc) {
        System.out.println("Enter the eye color(RED, YELLOW, ORANGE, BROWN):");
        String color = sc.next();
        List<Color> colors = Arrays.asList(Color.values());
        Color colorEye = null;
        for (Color color1 : colors) {
            if (color1.name().equals(color)) {
                colorEye = color1;
                return colorEye;
            }
        }
        System.out.println("The eye color is wrong.\n");
        return createEye(sc);
    }

    /**
     * Transforms from a string to an Integer
     *
     * @param text a string
     * @return Integer
     */
    public static Integer tryParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not an int");
            return null;
        }
    }

    /**
     * Transforms from a string to a Long
     *
     * @param text a string
     * @return Long
     */
    public static Long tryParseLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a long.");
            return null;
        }
    }

    /**
     * Transforms from a string to a Float
     *
     * @param text a string
     * @return Float
     */
    private static Float tryParseFloat(String text) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a float.");
            return null;
        }
    }

    /**
     * Transforms from a string to a Double
     *
     * @param text a string
     * @return Double
     */
    public static Double tryParseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a double");
            return null;
        }
    }

    /**
     * Reads a Double object
     *
     * @param sc         scanner
     * @param message    indica que informacion se lee
     * @param obligatory true indica que continue leyendo hasta que sea distinto de null, false indica una sola lectura
     * @return long
     */
    public static Long readLong(Scanner sc, String message, boolean obligatory) {
        System.out.println(message);
        boolean continueReading = obligatory;
        Long x = null;
        do {
            x = tryParseLong(sc.next());
            if (x == null) {
                System.out.println(message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
     * Reads a Double object
     *
     * @param sc         scanner
     * @param message    indica que informacion se lee
     * @param obligatory true indica que continue leyendo hasta que sea distinto de null, false indica una sola lectura
     * @return double
     */
    public static Double readDouble(Scanner sc, String message, boolean obligatory) {
        System.out.println(message);
        boolean continueReading = obligatory;
        Double x = null;
        do {
            x = tryParseDouble(sc.next());
            if (x == null) {
                System.out.println(message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
     * Reads a String object
     *
     * @param sc         scanner
     * @param message    indica que informacion se lee
     * @param obligatory true indica que continue leyendo hasta que sea distinto de null, false indica una sola lectura
     * @return string
     */
    public static String readString(Scanner sc, String message, boolean obligatory) {
        System.out.println(message);
        boolean continueReading = obligatory;
        String x = null;
        do {
            x = sc.next();
            if (x == null) {
                System.out.println("The entered value cannot be null,\n" + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
     * Reads an Integer object
     *
     * @param sc         scanner
     * @param message    indica que informacion se lee
     * @param obligatory true indica que continue leyendo hasta que sea distinto de null, false indica una sola lectura
     * @return int
     */
    public static Integer readInt(Scanner sc, String message, boolean obligatory) {
        System.out.println(message);
        boolean continueReading = obligatory;
        Integer x = null;
        do {
            x = tryParseInt(sc.next());
            if (x == null) {
                System.out.println(message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
     * Reads a Float object
     *
     * @param sc         scanner
     * @param message    indica que informacion se lee
     * @param obligatory true indica que continue leyendo hasta que sea distinto de null, false indica una sola lectura
     * @return float
     */
    private static Float readFloat(Scanner sc, String message, boolean obligatory) {
        System.out.println(message);
        boolean continueReading = obligatory;
        Float x = null;
        do {
            x = tryParseFloat(sc.next());
            if (x == null) {
                System.out.println(message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    public static void print_ascending(Hashtable<Long, Movie> movieHashtable) {
        Iterator<Map.Entry<Long, Movie>> it = movieHashtable.entrySet().iterator();
        List<String> list = null;

    }

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Executes a script from a file
     *
     * @param fileNameEx     the script file name
     * @param movieHashtable collection
     * @param initialization initialization time
     * @param fileName       name of the file
     */
    static void executeScript(String fileNameEx, Hashtable<Long, Movie> movieHashtable, String initialization, String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileNameEx));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("\nCant read the file.");
            return;
        }

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(ANSI_BLUE + line + ANSI_RESET);
                executeCommand(line, movieHashtable, initialization, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\nCant read the file.");
            return;
        }
    }

    /**
     * Executes a command from a script
     *
     * @param currentCommand the command that will be executed
     * @param movieHashtable the collection
     * @param initialization initialization time
     * @param fileName       name of the file
     * @throws IOException
     */
    private static void executeCommand(String currentCommand, Hashtable<Long, Movie> movieHashtable, String initialization, String fileName) throws IOException {
        String[] parts = currentCommand.split(" ");
        switch (parts[0]) {
            case "exit":
                System.out.println("Goodbye.");
                break;
            case "help":
                help();
                break;
            case "info":
                info(movieHashtable, initialization);
                break;
            case "show":
                show(movieHashtable);
                break;
            case "clear":
                clear();
            case "save":
                save(fileName, movieHashtable);
                break;
            case "insert":
                System.out.println("The insert command isn't supported in the none-interactive mode.");
                break;
            case "update":
                System.out.println("The update command isn't supported in the none-interactive mode.");
                break;
            case "remove_key":
                if (parts.length != 2) {
                    System.out.println("The remove_key command is incomplete.");
                    break;
                }
                remove(Long.parseLong(parts[1]), movieHashtable);
                break;
            case "replace_if_greater":
                if (parts.length < 3) {
                    System.out.println("replace_if_greater");
                    break;
                }
                replaceIfGreater(Long.parseLong(parts[1]), Long.parseLong(parts[2]), movieHashtable);
                break;
            case "replace_if_lower":
                if (parts.length < 3) {
                    System.out.println("The command is incomplete, you need to enter the key and the age.");
                    break;
                }
                CollectionManager.replaceIfLower(Long.parseLong(parts[1]), Long.parseLong(parts[2]), movieHashtable);
                break;
            case "remove_greater_key":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the key.");
                    break;
                }
                CollectionManager.remove_greater_key(Long.parseLong(parts[1]), movieHashtable);
                break;
            case "count_less_than_oscars_count":
                if (parts.length < 2) {
                    System.out.println("The command is incomplete, you need to enter the value.");
                    break;
                }
                CollectionManager.count_less_than_oscars_count(Long.parseLong(parts[1]), movieHashtable);
                break;
            case "print_ascending":
                CollectionManager.print_ascending(movieHashtable);
                break;
            case "print_field_descending_oscars_count":

                break;
        }
    }
}