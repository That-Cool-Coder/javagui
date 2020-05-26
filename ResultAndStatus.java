import java.util.ArrayList;

class ResultAndStatus {
    // very generic usable function

    String result;
    Status status;

    public ResultAndStatus(
        String result,
        Status status
    ) {
        this.result = result;
        this.status = status;
    }
}

class UserAndStatus {
    User user;
    Status status;

    public UserAndStatus(
        User user,
        Status status
    ) {
        this.user = user;
        this.status = status;
    }
}

class UserListAndStatus {
    // very specific but based on the base resultAndStatus with the types switched

    ArrayList<User> userList;
    Status status;

    public UserListAndStatus(
        ArrayList<User> userList,
        Status status
    ) {
        this.userList = userList;
        this.status = status;
    }
}

class BoolAndStatus {
    Boolean bool;
    Status status;

    public BoolAndStatus(
        Boolean bool,
        Status status
    ) {
        this.bool = bool;
        this.status = status;
    }
}

class NoteAndStatus {
    Note note;
    Status status;

    public NoteAndStatus(
        Note note,
        Status status
    ) {
        this.note = note;
        this.status = status;
    }
}