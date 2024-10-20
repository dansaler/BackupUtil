import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

class App extends TimerTask {
    static String dateToSave = ":) 09:36:30";
    static String DateToBeSaved = "";
    static String date = "";

    public static void main(String[] args) throws Exception {
        boolean checkPrevFilesState = checkPrevFilesState();
        if (LastDateExecuted() == false) {
            if (popup("Backup on " + getPrevDate() + " was nver run") == true) {
                System.out.println("Backup on " + getPrevDate() + " was nver run");
                @SuppressWarnings("unused")
                FolderHandling FH = new FolderHandling(true);
                ChangePrevFilesState(true);
            } else {
                System.out.println("Backup was Aborted");
                System.exit(0);
            }
        }

        if (checkPrevFilesState == true) {
            DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
            date = dateToSave.replace(":)", getDate());
            Date FinalDate = dateFormatter.parse("2024-08-28 10:24:00");// 2024-08-28 10:24:00
            DateToBeSaved = date;
            System.out.println("Next Backup on: " + FinalDate);
            Timer timer = new Timer();
            timer.schedule(new App(), FinalDate);
            ChangePrevFilesState(true);

        } else if (checkPrevFilesState == false) {
            if (popup("Last backup was incomplete or unsuccessful. Requesting to restart Backup.") == true) {
                System.out.println("Last backup was incomplete or unsuccessful. Backup will restart");
                @SuppressWarnings("unused")
                FolderHandling FH = new FolderHandling(true);
                ChangePrevFilesState(true);
            } else {
                System.out.println("Backup was Aborted");
                System.exit(0);
            }
        }
    }

    static boolean LastDateExecuted() throws IOException {
        File Directory = new File("C:\\Users\\dansa\\Downloads\\AutoBackupsData");
        File[] files = Directory.listFiles();
        String prevDate = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)) + ".txt".trim() + "";
        for (int i = 0; i < files.length; i++) {
            String temp = files[i].getName().trim() + "";
            if (prevDate.compareTo(temp) == 0) {
                System.out.println(
                        "Backup on " + getPrevDate() + " was run");

                return true;
            }
        }
        return false;
    }

    static boolean checkPrevFilesState() throws IOException {
        FileInputStream FIS = new FileInputStream(
                "C:\\Users\\dansa\\Downloads\\AutoBackupsData\\SaveFileStateCheck.txt");
        InputStreamReader ISR = new InputStreamReader(FIS);
        char[] arr1 = new char[FIS.available()];
        ISR.read(arr1);
        String temp = new String(arr1);
        if (temp.compareTo("false") == 0) {
            ISR.close();
            return false;

        } else {
            ISR.close();
            return true;
        }
    }

    @Override
    public void run() {

        try {
            @SuppressWarnings("unused")
            FolderHandling FH = new FolderHandling(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String getDate() {
        return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)) + "";
    }
    static String getPrevDate() {
        return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)) + "";
    }

    static boolean popup(String msg) {
        Object[] options = { "Yes", "No" };
        int choice = JOptionPane.showOptionDialog(null, "Do you want to run a backup of your system? ", msg,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options);

        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Proceeding with back up.", "Do not turn off Computer...",
                    JOptionPane.ERROR_MESSAGE);
            return true;

        } else if (choice == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(null, "Backup aborted");
            System.exit(0);
        }
        return false;
    }

    static void ChangePrevFilesState(boolean state) throws IOException {
        FileOutputStream FOS = new FileOutputStream("C:\\Users\\dansa\\Downloads\\AutoBackupsData\\SaveFileStateCheck.txt");
        OutputStreamWriter OSW = new OutputStreamWriter(FOS);
        BufferedWriter BOS = new BufferedWriter(OSW);
        BOS.write(state + "");
        BOS.close();
    }

    static void SaveDate(String date) throws IOException {
        FileOutputStream FOS = new FileOutputStream("C:\\Users\\dansa\\Downloads\\AutoBackupsData\\" + getPrevDate() + ".txt");
        OutputStreamWriter OSW = new OutputStreamWriter(FOS);
        BufferedWriter BOS = new BufferedWriter(OSW);
        System.out.println(getPrevDate());
        BOS.write("Backup Completed");
        BOS.close();
    }
}