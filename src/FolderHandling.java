import java.io.File;
import java.util.ArrayList;

class FolderHandling extends App {
   static int x = 0;
   private String subDirectoryName = "";//a temporary String holding the name of the sub directory 
   private ArrayList<String> tempList = new ArrayList<>();//Temporarily hold the files and folders at the specified sub Directory
   private String rootPath = "";//Path containg the "Main/root" directory which remains the same
   static ArrayList<String> subDirectoriesContent = new ArrayList<>();//ArrayList holding the entirity of folders at a specified level in the File hierarchy.
   static File directoryPath = new File("C:\\Users\\dansa\\Desktop\\Testing");
   ArrayList<String> subDirectoriesList = new ArrayList<>();
   FolderHandling(){
      
   }
   FolderHandling(boolean skip) throws Exception {
    //  x = 0;
      toFileScheduler(skip,"");
     
   }
 
   static void  toFileScheduler(boolean skip, String SubDirectoryName) throws Exception{
      FolderHandling FH = new FolderHandling();
      FH.setRootPath(SubDirectoryName);
      fileScheduler fs = new fileScheduler();
      File root = new File(FH.getRootPath());
      ArrayList<String> rootContent = new ArrayList<>();
      String list [] = root.list();
      for(int i = 0; i<list.length;i++){
         rootContent.add(list[i]);
      }
      
      ArrayList<String> subDirectories = FH.gettingSubDirectories(list);//storing all subdirectories at a specified level in subDirectories fetched from gettingSubDirectories
      CheckDate CD = new CheckDate();
      CD.createBackupTXTs(subDirectories);//Passing the list of Sub directores to a method to create balank txt's for any newly created folders
      FH.gettingFilesInSubDirectories(subDirectories);//getting entire list of files which reside in subdirectories at the specified level in the File hierarchy.
      fs.schedule("", FH.getRootPath(), rootContent, skip);
      for (int i = 0; i < subDirectories.size(); i++) {//loop for every sub directory
         FH.folderScheduler(i, subDirectories);//will pass the iteration number and the list of sub directory names to the folder scheduler
         FH.setRootPath(FH.getSubDirectoryName());
         fs.schedule(FH.getSubDirectoryName(), FH.getRootPath() + "\\", FH.getTempList(),skip);
      }
      SaveFiles.Exit();
   }

   void folderScheduler(int i, ArrayList<String> subDirectories) {
      for (; i < subDirectories.size();) {//i(counter) is passed from above method and is increased every iteration. Note that this loop will only terminate when break is called as i isn't being 
                                          //incremented
         setSubDirectoryName(subDirectories.get(i));//The sub directory name for the current iteration will be saved to the local variable subDirectoryName
         ArrayList<String> tempList = new ArrayList<>();
         for (; x < subDirectoriesContent.size(); x++) {//will loop for the number of values in subDirectoriesContent containg the enitrrtiy of folders in the sub directory, preserving the 
                                                      //the value x locally, hence acting as a pointer to the last compared value
            if (subDirectoriesContent.get(x).equals("BREAK") != true) {//When the value at the specifed index is BREAK, folder name added to temp arrayList
               tempList.add(subDirectoriesContent.get(x));
            } else {//When the value at the specifed index is BREAK, this indicates that the files following it, belong to another folder
               setTempList(tempList);//hence value of of temp arrayList is stored in local variable tempList
               x++;//x is incremented due to the coming break
               break;//breaks inner loop
               
            }
         }
         break;//breaks outer loop
      }
   }

   ArrayList<String> gettingSubDirectories(String list[]) {//Method to get entire list of SubDirectories at a specfied level in the file hierarchy
     // File root = new File(getRootPath());
     // list = root.list();//Array containing the names of every folder and file at that level
   
    //will hold the names of the sub directories
      for (int i = 0; i < list.length; i++) {//An iteration will occur for every name in list[]

         String checkPath = getRootPath() + "\\" + list[i]; //adding the file or folder name to the end of the root path
         File validation = new File(checkPath);//creating a file obj for that path
         // System.out.println(validation.list());
         String temp = validation.list() + "";//Will return null if a file as is not a valid path
         if (temp.compareTo("null") != 0) {
            subDirectoriesList.add(list[i]);//hence if not null, it must be a folder and it will be added to subDirectoriesList
         }
      }
      return subDirectoriesList;//after all iterations terminate, subDirectoriesList is returned
   }

   ArrayList<String> gettingFilesInSubDirectories(ArrayList<String> subDirectoriesList) {//passing the names of the sub directories as a parameter
      for (int i = 0; i < subDirectoriesList.size(); i++) {//will loop for the number of sub directoreis
         File subDirectoryList = new File(getRootPath() + "\\" + subDirectoriesList.get(i));//creating the path for the subdirectory at the specfied index of subDirectoriesList
         String tempList[] = subDirectoryList.list();//Temporarily holding the names of files in a specifc sub directory
         for (int x = 0; x < tempList.length; x++) {//loop for the num of files in tempList[]
            subDirectoriesContent.add(tempList[x]);//will store the files for that folder each time it loop preserving the values
         }
         subDirectoriesContent.add("BREAK");//Each consecutive number of x folder names for specified sub directory will be seperated by a BREAK
      }
      //System.out.println(subDirectoriesContent);
      return subDirectoriesContent;
   }

   void setSubDirectoryName(String subDirectoryName) {
      this.subDirectoryName = subDirectoryName;
   }

   File getNewPath(){
      File newPath  = new File(getRootPath() + "\\" +getSubDirectoryName());
      return newPath;
   }
   void setRootPath(String SubDirectoryName){
      this.rootPath = "C:\\Users\\dansa\\Desktop\\Testing" +"\\"+ SubDirectoryName;
   }
   String getRootPath(){
      return rootPath;
   }

   String getSubDirectoryName() {
      return subDirectoryName;
   }

   void setTempList(ArrayList<String> tempList) {
      this.tempList = tempList;
   }

   ArrayList<String> getTempList() {
      return tempList;
   }

}
