import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

class Database {
    String acctFilePath;
    String infoFilePath;

    public Database(
        String acctFilePath,
        String infoFilePath
    ) {     
        this.acctFilePath = acctFilePath;
        this.infoFilePath = infoFilePath;
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

        } catch (FileNotFoundException e) {
            status = Status.FATAL_ERROR;
            // !DEBUG!
            e.printStackTrace();
        }

        ResultAndStatus returnVal = new ResultAndStatus(content, status);
        return returnVal;
    }
}