import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;

class SaveFiles extends fileScheduler {
   static int k = 0;
   static String modified = "";
   static String outputPath1 = "C:\\Users\\dansa\\Desktop\\Testing output";
   static int transferedFileCounter = 0;
   static double start;
   SaveFiles(boolean skip, String directoryPath, String contents[], String subDirectoryName,
         boolean skipBackupConfirmation) throws Exception {
      k = 0;

      if (Start(skip, skipBackupConfirmation) == true) {
         CheckDate CD = new CheckDate();

         for (int i = 0; i < contents.length; i++) {// will loop for the number of file names in contents[]
            modified = sourcePath(contents, directoryPath, subDirectoryName);// modified will hold the name of a
                                                                             // modified file or contain "Found"
            // indicating that it was found in the not modified list hence unedited

            if (modified.equals("Found") == false) {// if modified
               System.out.println(modified);
               String inputPath1 = directoryPath + "\\" + modified;// new path created on modified
               File out = new File(inputPath1);
               // System.out.println(out);
               // File in = new File(outputPath1);
               File in = new File(outputPath1 + "\\" + subDirectoryName);

               try {
                  String temp = out.list() + "";
                  if (temp.compareTo("null") == 0)// if temp is a file
                     FileUtils.copyFileToDirectory(out, in);// Transfers file to Directory
                  else {// else if folder.sub directory

                     in = new File(outputPath1 + "\\" + subDirectoryName + "\\" + modified + "\\");

                     // System.out.println("Making a directory on: " + in);
                     in.mkdirs();// create sub directory on name at the specified location

                  }
               } catch (IOException e) {
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(null,
                        "Backup aborted. Specfied File path may be invalid", "RUN RIME ERROR!!",
                        JOptionPane.ERROR_MESSAGE);
                  System.exit(0);
               }
            }
            k++;// the lacally stored counter k is incremented and the process is repeated for a
                // diffrent file name
         }
         CD.writeLastModified(contents, directoryPath, subDirectoryName);// saving the new dates of editing
      }
   }

   String sourcePath(String contents[], String directoryPath, String subDirectoryName) throws IOException {
      boolean exit = true;
      CheckDate CD = new CheckDate();

      String Content = contents[k];// getting the filename at the specified index. k is used as a persistant
                                   // pointer

      ArrayList<String> notModified = CD.getNotModified(contents, directoryPath, subDirectoryName);// notModified will
                                                                                                   // store list of all
                                                                                                   // file names which
                                                                                                   // were unmodified
      do {
         try {
            for (int i = 0; i < CD.getContentsLength(); i++) {// will loop for the number of file names in contents[]
               if (notModified.get(i).equals(Content) == true) {// A file name from the unmodified list at a specfied
                                                                // index will be compared the name recived from
                                                                // getContents. Contetns will be
                                                                // compared to every unModified File name
                  exit = true;// exit changed to true
                  notModified.clear();
                  return "Found";

               }
            }
            exit = true;
         } catch (Exception e) {// Whenever a file is modified notModified becomes smaller while contents
                                // remains the same, hence An arrayoutofboundsexception is bound to happen
            exit = false;// exit is changed to false preventing the exit of the do while loop
            notModified.add("null");// Null is added to not modified and for loop is repeated. If there are more
                                    // thatn 1 modified file, this proccess will repeat
         }
      } while (exit != true);
      transferedFileCounter++;
      notModified.clear();
      return Content;// returns the name of the file which WAS Modified
   }

   boolean Start(boolean skip, boolean skipBackupConfirmation) {
      if (skipBackupConfirmation == false) {// if getSkipBackupConfirmation != true the user wont be prompted again
         // after the first time to agree to a backup
         if (skip == true || popup("Weekly backup requested") == true) {
            System.out.println("Copying Files....");
            start = System.nanoTime();

            return true;
         } else {
            return false;
         }
      }
      return true;
   }



   static void Exit() throws IOException {
      double stop = System.nanoTime();
      JOptionPane.showMessageDialog(null, "Backup Complete :)",
            "-------------------------- <:D --------------------------", JOptionPane.INFORMATION_MESSAGE);
      System.out.println("Backup complete");
      System.out.println("Exceution Time: " + (stop - start) / (1000000000));
      SaveDate(App.date);// Sve the date as the name on a txt file to keep track of backups
      ChangePrevFilesState(true);
      System.out.println("Transfered files: " + transferedFileCounter);
      System.exit(0);
   }
}
