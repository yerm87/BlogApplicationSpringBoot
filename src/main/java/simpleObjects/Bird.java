package simpleObjects;

public class Bird {
    private int id;
    private String name;

    public Bird(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
