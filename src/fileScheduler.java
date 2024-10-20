import java.util.ArrayList;
import java.io.File;

@SuppressWarnings("unused")
class fileScheduler extends FolderHandling {
    long count = 0;
    long c = 0;
    int x = 0;
    private String contents[] = directoryPath.list();// Contains the file names in the specified location
    private boolean skipBackupConfirmation = false;
    ArrayList<String> subDirectoryPathList = new ArrayList<>();
    ArrayList<String> temp = new ArrayList<>();
    ArrayList<String> rootPathCOntainingSubDirectories = new ArrayList<>();

    void schedule(String subDirectoryName, String directoryPath, ArrayList<String> directoryContents, boolean skip)
            throws Exception {
        // System.out.println(directoryPath + " -> " + directoryContents);
        File path = new File(directoryPath);
        setContents(directoryContents);

        SaveFiles SF = new SaveFiles(skip, directoryPath, getContents(), subDirectoryName, skipBackupConfirmation);
        skipBackupConfirmation = true;

        subDirectoryPathList.add(directoryPath);
        // System.out.println(subDirectoryPathList);
        enableingRecursion(subDirectoryPathList, subDirectoryName);

    }

    void enableingRecursion(ArrayList<String> subDirectoryPathList, String subDirectoryName) {
        String rootPath = subDirectoryPathList.get((int) count);
        File file = new File(rootPath);
        String contentsInSubDirectory[] = file.list();
        if (count != 0) {
            setRootPath(subDirectoryName);// setting new root path on sub directory
            System.out.println(getRootPath());
            gettingSubDirectories(contentsInSubDirectory);// Sending list of contents in a sub directory and get the set
                                                          // the subdirectory in them to subDirectoriesList
            // System.out.println(subDirectoriesList);//geting sub directories in rew root
            // path
            boolean b = true;
            for (int i = 0; i < subDirectoriesList.size(); i++) {
                temp.add(subDirectoriesList.get(i));
                if (b = true && i == subDirectoriesList.size() - 1) {
                    rootPathCOntainingSubDirectories.add(getRootPath());
                    temp.add("BREAK");
                    b = false;
                }
            }
            subDirectoriesList.clear();
           // System.out.println(temp);// Arr List containg all subdirectories at a certian level in the file
                                     // hierarchy. "BREAK" is used to seperate Subdirectories for the root path
           // System.out.println(rootPathCOntainingSubDirectories);// contains the rootPAth corrilating to the
                                                                 // subdirectories befor the "BREAK"
            if(count == 5){
                for(int i = 0; i<temp.size(); i++){
                    if(temp.get(i).compareTo("BREAK") == 0){
                        for(; x<i ;x++){
                            subDirectoriesList.add(temp.get(x));
                        }  
                        recursionScheduler(rootPathCOntainingSubDirectories.get((int)c), subDirectoriesList);
                        subDirectoriesList.clear();
                        c++;
                    }
                }
                this.count = 0;
            }
        }
        count++;
    }
    void recursionScheduler(String rootPath,ArrayList<String> subDirectoriesList){
        System.out.println("asfdfdsdd" + rootPath + "\t" + subDirectoriesList);
      //  SaveFiles SF = new SaveFiles(false, rootPathCOntainingSubDirectories, getContents(), subDirectoryName, true);
     
    }

    void setContents(ArrayList<String> directoryContents) {
        String temp[] = new String[directoryContents.size()];
        for (int i = 0; i < directoryContents.size(); i++) {
            temp[i] = directoryContents.get(i);
        }
        this.contents = temp;
    }

    String[] getContents() {
        return contents;
    }
}
