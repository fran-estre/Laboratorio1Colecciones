import java.util.Date;

public class Movie {

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long oscarsCount; //Значение поля должно быть больше 0
    private Integer budget; //Значение поля должно быть больше 0, Поле не может быть null
    private int totalBoxOffice; //Значение поля должно быть больше 0
    private MpaaRating mpaaRating; //Поле может быть null
    private Person operator; //Поле может быть null


 public Movie(long id,String name,Coordinates coordinates,java.util.Date creationDate,long oscarsCount,Integer budget,int totalBoxOffice,MpaaRating mpaaRating,Person operator){
     this.id= id;
     this.name = name;
     this.coordinates = coordinates;
     this.creationDate=creationDate;
     this.oscarsCount = oscarsCount;
     this.budget = budget;
     this.totalBoxOffice=totalBoxOffice;
     this.mpaaRating = mpaaRating;
     this.operator = operator;
 }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public int getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public void setTotalBoxOffice(int totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }

    public void setOperator(Person operator) {
        this.operator = operator;
    }
}