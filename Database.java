import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

class Database {
    String dataDirPath;
    String userFilePath;
    String infoFilePath;

    Util util;

    public Database(
        String dataDirPath,
        String userFilePath,
        String infoFilePath
    ) {
        this.util = new Util();

        this.dataDirPath = dataDirPath;
        this.userFilePath = userFilePath;
        this.infoFilePath = infoFilePath;

        this.initDataDirIfNeeded();

    }

    public void initDataDirIfNeeded() {
        File dir = new File(this.dataDirPath);
        if (! dir.exists()) {
            boolean success = dir.mkdirs();
            if (success) {
                this.initDataFilesIfNeeded();
            }
            else {
                this.util.printStrLn("Could not make data dir");
            }
        }
    }

    public void initDataFilesIfNeeded() {
        // this assumes that the dir has already been made/validated
        
        this.makeFileChecks(this.userFilePath);
        this.makeFileChecks(this.infoFilePath);
    }

    public boolean makeFileChecks(String pathToFile) {
        boolean success = false;

        File file = new File(pathToFile);
        if (! file.exists()) {
            try {
                file.createNewFile();
                success = true;
            }
            catch (IOException e){
                this.util.printStrLn("ERROR: could not make file " + pathToFile);
                success = false;
            }
        }
        return success;
    }

    public ResultAndStatus readInfoFile() {
        // set up vars so that they will still exist if error occurs
        String content = "";
        Status status = Status.FATAL_ERROR;

        try {
            File myObj = new File(this.infoFilePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content += data;
            }
            myReader.close();
            status = Status.OK;

        }
        catch (FileNotFoundException e) {
            status = Status.FATAL_ERROR;
            // !DEBUG!
            e.printStackTrace();
            Main.safelyCrash();
        }

        ResultAndStatus returnVal = new ResultAndStatus(content, status);
        return returnVal;
    }

    //private UserListAndStatus readUserList() {
        
    //}

    private Status saveUserList(User[] userList) {
        Status status = Status.FATAL_ERROR;

        try {
            File userFile = new File(this.userFilePath);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFile));
            oos.writeObject(userList);
            oos.flush();
            oos.close();
            status = Status.OK;
        }
        catch (IOException e) {
            status = Status.FATAL_ERROR;
            // !DEBUG!
            e.printStackTrace();
            Main.safelyCrash();
        }

        return status;
    }
}