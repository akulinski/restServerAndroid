package core.users;

public abstract class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    User(String name, int id)
    {
        this.id=id;
        this.name=name;
    }

    User()
    {

    }

}
