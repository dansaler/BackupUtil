import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class CheckDate extends fileScheduler {
    static ArrayList<String> notModifiedArrList = new ArrayList<>();// will contain file names who were not modified
    static ArrayList<String> prevModificationDates = new ArrayList<>();// will contain the previous modification dates
    ArrayList<Integer> numOfDates = new ArrayList<>();
    static String stringTemp = "";
    static String datesToSave = "";

    ArrayList<String> getNotModified(String contents[], String directoryPath, String subDirectoryName)
            throws IOException {
        gettingPrevDates(subDirectoryName);// setting the arrayList prevModificationDates to contain the previous
        // modification dates
        for (int j = 0; j < contents.length; j++) {

            FilesThatWereNotEditedOrAreNew(prevModificationDates, contents[j] + "",
                    getTheCurrentModifiedDate(j, contents, directoryPath));// sending the prev mod date, name of file at
                                                                           // index j
            // in contents along with the current date of modification
            // of the file and will repeate until every file in contents is checked

        }

        return notModifiedArrList;// ArrayList containg unmodified file names
    }

    static void gettingPrevDates(String subDirectoryName) throws IOException {// Fetching the file LastMoifiedDate and
                                                                              // reading thpse dates and
        // storing them in the ArrayList prevModificationDates
        String path = "C:\\Users\\dansa\\Downloads\\AutoBackupsData\\LastModifiedDate\\" + subDirectoryName
                + ".txt";
        FileInputStream FIS = new FileInputStream(path);
        InputStreamReader ISR2 = new InputStreamReader(FIS);

        char arrChar[] = new char[FIS.available()];
        ISR2.read(arrChar);
        char a = '\n';

        for (int x = 0; x < arrChar.length; x++) {
            if (arrChar[x] != a) {
                stringTemp = stringTemp + arrChar[x];
            } else {
                prevModificationDates.add(stringTemp);// prevModificationDates contains the previous modified dates
                                                      // contained in the file
                stringTemp = "";
            }
        }
        ISR2.close();
    }

    String getTheCurrentModifiedDate(int i, String contents[], String directoryPath) throws IOException {// Getting the
                                                                                                         // current
                                                                                                         // modification
                                                                                                         // date on the
        // files
        File directoryPath2 = new File(directoryPath + "\\" + contents[i]);
        return directoryPath2.lastModified() + "";
    }

    static void FilesThatWereNotEditedOrAreNew(ArrayList<String> prevModificationDates, String fileName,
            String currentModDate) {// will loop for the ammount of previous modification dates in the
        // strArrayList, then check if the current modification date is found or
        // matched with any of the previous ones. If yes the file whos current mod
        // -iffication date matches any previous one will be added to the not
        // moddified arrayList
        for (int i = 0; i < prevModificationDates.size(); i++) {
            // System.out.println(strArrayList.get(i) + " AND " + prevDate);
            if (prevModificationDates.get(i).contains(currentModDate) == true) {
                notModifiedArrList.add(fileName);
                // System.out.println(fileName + " remained THe Same ");

            }
        }
    }

    int getContentsLength() {
        prevModificationDates.clear();
        return getContents().length;// will loop for the number of file names in contents[]
    }

    void createBackupTXTs(ArrayList<String> subDirectories) throws IOException {
        String path = "C:\\Users\\dansa\\Downloads\\AutoBackupsData\\LastModifiedDate\\";// Path containg Sub
                                                                                         // directories with already
                                                                                         // saved txt files
        File tempFile = new File(path);
        String tempArr[] = tempFile.list();// Array containg Sub directories with already saved txt files
        ArrayList<String> subDirectoriesWithTxtFiles = new ArrayList<>();
        
        for (int i = 0; i < tempArr.length; i++) {
            subDirectoriesWithTxtFiles.add(tempArr[i]);// ArrayList containg Sub directories with already saved txt files
        }
           long fileDiffrence = (subDirectories.size()-subDirectoriesWithTxtFiles.size());

            for (int i = 0; i < subDirectoriesWithTxtFiles.size() + fileDiffrence; i++) {// Will loop for the total ammount of sub directoreies total
                String temp = subDirectories.get(i) + ".txt";// temp will hold a name of 1 of the files already saved

                
                if (subDirectoriesWithTxtFiles.contains(temp) == false) {//IF folder doesn't have a txt file with its name then, a txt file is created with it's name to store all last modification dates
                    //System.out.println(temp);                           //Of folders found in it.
                    FileOutputStream FOS = new FileOutputStream(path + temp);
                    OutputStreamWriter OSW = new OutputStreamWriter(FOS);
                    BufferedWriter BFW = new BufferedWriter(OSW);
                    BFW.write("");
                    BFW.close();
                }
            }
}

    void writeLastModified(String contents[], String directoryPath, String subDirectoryName) throws IOException {// Saving
                                                                                                                 // the
                                                                                                                 // new
                                                                                                                 // Modified
                                                                                                                 // dates
        FileOutputStream FOS = new FileOutputStream(
                "C:\\Users\\dansa\\Downloads\\AutoBackupsData\\LastModifiedDate\\" + subDirectoryName + ".txt");
        OutputStreamWriter OSW = new OutputStreamWriter(FOS);
        BufferedWriter BFW = new BufferedWriter(OSW);
        for (int i = 0; i < contents.length; i++) {
            datesToSave = datesToSave + getTheCurrentModifiedDate(i, contents, directoryPath) + "\n";
        }
        BFW.write(datesToSave, 0, contents.length * 14);
        datesToSave = "";
        BFW.close();
    }

}