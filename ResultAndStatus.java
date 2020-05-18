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

class UserListAndStatus {
    // very specific but based on the base resultAndStatus with the types switched

    User[] userList;
    Status status;

    public UserListAndStatus(
        User[] userList,
        Status status
    ) {
        this.userList = userList;
        this.status = status;
    }
}