import java.io.Serializable;

class User implements Serializable {
    String name;
    String password;

    public User(
        String name,
        String password
    ) {
        this.name = name;
        this.password = password;
    }
}