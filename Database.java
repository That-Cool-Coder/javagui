import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

class Database {
    String dataDirPath;
    String userFilePath;
    String infoDirPath;

    Util util;

    public Database(
        String dataDirPath,
        String userFilePath,
        String infoDirPath
    ) {
        this.util = new Util();

        this.dataDirPath = dataDirPath;
        this.userFilePath = userFilePath;
        this.infoDirPath = infoDirPath;

        this.initDataDirIfNeeded(false);
    }

    // Main callables
    // --------------

    public BoolAndStatus passwordCorrect(
        String username,
        String password
    ) {
        // returns Status.warning if user not found

        Status status = Status.WARNING;
        Boolean passwordCorrect = false;

        UserListAndStatus uls = this.readUserFile(false);
        
        User user = this.findUserByName(username, uls.userList);
        if (user != null) {
            // (if user found)

            if (password.equals(user.password)) {
                passwordCorrect = true;
            }
            else {
                passwordCorrect = false;
            }
            status = Status.OK;
        }
        else {
            // (if user not found)
            status = Status.WARNING;
        }
        return new BoolAndStatus(passwordCorrect, status);
    }

    public Status createNewAcct(
        String username,
        String password
    ) {
        // returns OK if new acct was made, WARNING if username is duplicated, or FATAL_ERROR
        Status status = Status.WARNING;

        UserListAndStatus uls = this.readUserFile(false);

        if (uls.status == Status.OK) {
            User user = this.findUserByName(username, uls.userList);
            if (user == null) {
                // (if user not already existing)
                User newUser = new User(username, password);
                uls.userList.add(newUser);
                this.saveUserList(uls.userList);
                status = Status.OK;
            }
            else {
                // (if user already exists)
                status = Status.WARNING;
            }
        }
        else {
            status = Status.FATAL_ERROR;
        }
        return status;
    }

    public Status saveNote(Note note) {
        Status status = Status.WARNING;

        try (FileOutputStream fos = new FileOutputStream(this.infoDirPath + note.fileName + ".dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // write object to file
            oos.writeObject(note);
            status = Status.OK;

        } catch (IOException ex) {
            this.util.printStrLn("Error saving note");
            status = Status.FATAL_ERROR;
            // don't crash as it's not a open error and doesn't affect other files (I think)
        }

        return status;
    }


    // Init functions
    // --------------

    private Status initDataDirIfNeeded(boolean errorHandled) {
        // initialise the dir that holds program data if it doesn't exist already

        Status status = Status.WARNING;

        File dir = new File(this.dataDirPath);
        if (! dir.exists()) {
            boolean success = dir.mkdirs();
            if (success) {
                status = Status.OK;
            }
            else {
                if (! errorHandled) {
                    this.util.printStrLn("Could not make data dir");
                    Main.safelyCrash();
                }
                status = Status.FATAL_ERROR;
            }
        }
        else {
            // if dir already exists, set status to OK so that the next part will
            // know that everything is ok
            status = Status.OK;
        }
        
        if (status == Status.OK) {
            this.initDataFilesIfNeeded();
        }
        return status;
    }

    private void initDataFilesIfNeeded() {
        // this assumes that the main dir has already been made/validated
        
        // if the user file doesn't exist, init it with an empty arrayList
        File file = new File(this.userFilePath);
        if (! file.exists()) {
            ArrayList<User> userList = new ArrayList<User>();
            this.saveUserList(userList);
        }

        // set up info dir
        File dir = new File(this.infoDirPath);
        if (! dir.exists()) {
            boolean success = dir.mkdirs();
            // if success do nothing
            // else say error
            if (! success) {
                this.util.printStrLn("Could not make info dir");
                Main.safelyCrash();
            }
        }
    }

    // Generic file edit functions
    // ---------------------------

    private BoolAndStatus makeFileChecks(
        String pathToFile,
        boolean errorHandled
    ) {
        // this makes a file if it doesn't exist, uses a try/catch,
        // returns an status. safely crashes if errorHandled is false

        Status status = Status.WARNING; // start with status = warning for least of evils
        boolean fileMade = false;

        File file = new File(pathToFile);
        System.out.println(file.exists());
        if (! file.exists()) {
            try {
                file.createNewFile();
                status = Status.OK;
                fileMade = true;
            }
            catch (IOException e){
                if (! errorHandled) {
                    this.util.printStrLn("ERROR: could not make file " + pathToFile);
                    Main.safelyCrash();
                }
                status = Status.FATAL_ERROR;
            }
        }
        return new BoolAndStatus(fileMade, status);
    }

    // Small reader functions
    // ----------------------

    private UserListAndStatus readUserFile(boolean errorHandled) {
        ArrayList<User> userList = new ArrayList<User>();
        Status status = Status.WARNING;

        try {           
            FileInputStream fis = new FileInputStream(this.userFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);         
            userList = (ArrayList<User>)ois.readObject();
            ois.close();
            status = Status.OK;    
        }       
        catch (IOException e) {
            if (! errorHandled) {
                this.util.printStrLn("Fatal Error");
                e.printStackTrace();
            }
            status = Status.FATAL_ERROR;
        }       
        catch (ClassNotFoundException e) {
            if (! errorHandled) {
                this.util.printStrLn("Fatal Error");
                e.printStackTrace();
            }
        }
        return new UserListAndStatus(userList, status);
    }

    //private void

    private void saveUserList(ArrayList<User> userList) {
        // !DEBUG!
        try {
            // not complete - add bool errorHandled, status returns
            FileOutputStream fout = new FileOutputStream(this.userFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(userList);
            fout.close();
        }
        catch (IOException e) {
            Main.safelyCrash();
        }
    }

    // Small data-manipulation functions
    // ---------------------------------

    private User findUserByName(
        String username,
        ArrayList<User> userList
    ) {
        // returns null if user is not found, assumes no duplicate usernames

        User user = null;

        for (int idx = 0; idx < userList.size(); idx ++) {
            User crntUser = userList.get(idx);

            if (crntUser.name.equalsIgnoreCase(username)) {
                user = crntUser;
                break;
            }
        }

        return user;
    }
}