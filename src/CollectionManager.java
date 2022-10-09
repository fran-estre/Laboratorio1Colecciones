import Entidades.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CollectionManager {

    public  static void help(){
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
                "print_ascending : вывести элементы коллекции в порядке возрастания\n"+
                "print_field_descending_oscars_count : вывести значения поля oscarsCount всех элементов в порядке убывания");
    }
    public static void info(Hashtable<Long, Movie> movieHashtable,String initialization){
        System.out.println("type: Hashtable<Long, Movie>");
        System.out.println("initialization: " + initialization);
        System.out.println("length: " + movieHashtable.size());
    }
    public static void show(Hashtable<Long, Movie> movieHashtable) {
        movieHashtable.forEach((k, v) -> System.out.println("\n" + v.toString()));
    }
    static Hashtable<Long, Movie> clear() {
        Hashtable<Long, Movie> movieHashtable = new Hashtable<>();
        return movieHashtable;
    }
    static void remove(Long id,Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(id)) {
            movieHashtable.remove(id);
            System.out.println("The movie was removed.");
        } else
            System.out.println("The movie don't exists.");
    }
    static void replaceIfGreater(Long id, long oscarsCount,Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(id))
            if (movieHashtable.get(id).getOscarsCount() < oscarsCount) {
                movieHashtable.get(id).setOscarsCount(oscarsCount);
                System.out.println("\nThe oscarsCount was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }
    static void replaceIfLower(Long id, long oscarsCount,Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(id))
            if (movieHashtable.get(id).getOscarsCount() > oscarsCount) {
                movieHashtable.get(id).setOscarsCount(oscarsCount);
                System.out.println("\nThe oscarsCount was replaced.");
                return;
            }
        System.out.println("\nNothing to replace.");
    }
    static void count_less_than_oscars_count(long num,Hashtable<Long, Movie> movieHashtable) {
         AtomicReference<Integer> counter = new AtomicReference<>(0);
        movieHashtable.forEach((k, v) -> {
            if (v.getOscarsCount() == num)
                counter.getAndSet(counter.get() + 1);
        });
        System.out.println("\nThere are " + counter.get() + " movies with less than "+num+" oscars.");
    }
    static void insert(Scanner keyboard,Hashtable<Long, Movie> movieHashtable) {
        Movie movie = createMovie(keyboard,movieHashtable);
        movieHashtable.put(movie.getId(), movie);
    }
    static void update(Long id, Scanner keyboard,Hashtable<Long, Movie> movieHashtable) {
        if (movieHashtable.containsKey(id)) {
            Movie movie = createMovie(keyboard,movieHashtable);
            movieHashtable.replace(id, movie);
            System.out.println("The movie was updated.");
        } else
            System.out.println("The movie don't exist.");
    }
    static void remove_greater_key(Long id,Hashtable<Long, Movie> movieHashtable) {
        Iterator<Map.Entry<Long, Movie>> it = movieHashtable.entrySet().iterator();
        Integer deleted = 0;
        while (it.hasNext()) {
            Map.Entry<Long, Movie> entry = it.next();
            if (entry.getKey() > id) {
                it.remove();
                deleted++;
            }
        }
        System.out.println("Deleted elements: " + deleted);
    }

    private static void save(String fileName,Hashtable<Long, Movie> movieHashtable) {
        String movie[] = new String[17];
        List<String[]> movies = new ArrayList<>();
        Enumeration<Long> e = movieHashtable.keys();
        while (e.hasMoreElements()) {
            long key = e.nextElement();
            Movie m= movieHashtable.get(key);
            movie[0]= String.valueOf(m.getId());
            movie[1]=String.valueOf(m.getName());
            movie[2]= String.valueOf(m.getCoordinates().getX());
            movie[3]=String.valueOf(m.getCoordinates().getY());
            movie[4]=String.valueOf(m.getCreationDate());
            movie[5]=String.valueOf(m.getOscarsCount());
            movie[6]=String.valueOf(m.getBudget());
            movie[7]=String.valueOf(m.getTotalBoxOffice());
            movie[8]=String.valueOf(m.getMpaaRating());
            movie[9]=String.valueOf(m.getOperator().getName());
            movie[10]=String.valueOf(m.getOperator().getHeight());
            movie[11]=String.valueOf(m.getOperator().getPassportID());
            movie[12]=String.valueOf(m.getOperator().getEyeColor());
            movie[13]=String.valueOf(m.getOperator().getLocation().getX());
            movie[14]=String.valueOf(m.getOperator().getLocation().getY());
            movie[15]=String.valueOf(m.getOperator().getLocation().getZ());
            movie[16]=String.valueOf(m.getOperator().getLocation().getName());
            movies.add(movie);
        }



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
    public static Movie createMovie(Scanner sc,Hashtable<Long, Movie> movieHashtable) {
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
            oscarsCount = readLong(sc, message, false);
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
            totalBoxOffice = readInt(sc, message, false);
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
            if (!mpaaRating.name().equals(rate)) {
                System.out.println("the rating is wrong.\n");
                getMpaaRating(sc);
            }
            rating = mpaaRating;
            break;
        }
        return rating;
    }
    public static Coordinates createCoordinates(Scanner sc) {
        String message = "Introduce the coordinate x:";
        Double x = readDouble(sc, message, true);
        message = "Introduce the coordinate y:";
        float y = readFloat(sc, message, false);
        while (y <= -43) {
            System.out.println("You have entered a wrong value, you can only enter values greater than -43.");
            y = readFloat(sc, message, false);
        }
        return new Coordinates(x, y);
    }
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
        } while (passport.isEmpty() || passport.length() >= 4);

        Color eye = createEye(sc);

        Location location = createLocation(sc);

        return new Person(name, height, passport, eye, location);
    }
    private static Location createLocation(Scanner sc) {
        String message = "Introduce the Location coordinate x:";
        Long x = readLong(sc, message, true);
        message = "Introduce the Location coordinate y:";
        float y = readFloat(sc, message, false);
        message = "Introduce the Location coordinate z:";
        Float z = readFloat(sc, message, true);
        message = "Introduce the Location name:";
        String name = readString(sc, message, true);

        return new Location(x, y, z, name);
    }
    private static Color createEye(Scanner sc) {
        System.out.println("Enter the eye color(RED, YELLOW, ORANGE, BROWN):");
        String color = sc.next();
        List<Color> colors = Arrays.asList(Color.values());
        Color colorEye = null;
        for (Color color1 : colors) {
            if (!color1.name().equals(color)) {
                System.out.println("The eye color is wrong.\n");
                createEye(sc);
            }
            colorEye = color1;
            break;
        }
        return colorEye;
    }
    public static Integer tryParseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not an int");
            return null;
        }
    }
    public static Long tryParseLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a long.");
            return null;
        }
    }
    private static Float tryParseFloat(String text) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a float.");
            return null;
        }
    }
    public static Double tryParseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            System.out.println("The value you entered is not a double");
            return null;
        }
    }

    /**
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
            x = tryParseLong(sc.nextLine());
            if (x == null) {
                System.out.println("The entered value cannot be null, " + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
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
            x = tryParseDouble(sc.nextLine());
            if (x == null) {
                System.out.println("The entered value cannot be null, " + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
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
            x = sc.nextLine();
            if (x == null) {
                System.out.println("The entered value cannot be null, " + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }

    /**
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
            x = tryParseInt(sc.nextLine());
            if (x == null) {
                System.out.println("The entered value cannot be null, " + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }
    /**
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
            x = tryParseFloat(sc.nextLine());
            if (x == null) {
                System.out.println("The entered value cannot be null, " + message);
            } else {
                continueReading = false;
            }
        } while (continueReading);
        return x;
    }
}
