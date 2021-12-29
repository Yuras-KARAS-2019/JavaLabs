package JavaCleanerLab1;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class FindJavaFiles extends RecursiveAction {
  private File _file;

  public FindJavaFiles(String dir){
    _file = new File(dir);
  }

  public void findFiles() throws IOException {
    String ext = ".java";
    String dir = _file.getPath();
    if (!_file.exists()) {
      System.out.println("[ WARNING ]" + dir + " folder does not exist");
    }
    File[] listFilesAndDirectories = _file.listFiles();
    for (File listFilesAndDirectory : listFilesAndDirectories) {
      if (listFilesAndDirectory.isDirectory()) {
        FindJavaFiles newHalf = new FindJavaFiles(listFilesAndDirectory.getPath());
        newHalf.fork();
      }
    }
    File[] listFiles = _file.listFiles(new MyFileNameFilter(ext));
    if (listFiles.length == 0) {
      System.out.println("[ INFO ] " + dir + " does not contain files *" + ext);
    } else {
      for (File f : listFiles) {
        deleteFreeSpace(f);
        System.out.println("[ INFO ] Файл: " + dir + File.separator + f.getName());
      }
    }
  }

  public static void deleteFreeSpace(File file) throws IOException {
    List<String> s = Files.readAllLines(Path.of(file.getAbsolutePath()), StandardCharsets.UTF_8);
    StringBuilder temp = new StringBuilder();
    for (var i : s)
    {
      temp.append(i);
    }
    temp = new StringBuilder(temp.toString().replaceAll("^ +| +$|( )+", "$1"));
    System.out.println(temp);
  }

  public static class MyFileNameFilter implements FilenameFilter {

    private String ext;

    public MyFileNameFilter(String ext) {
      this.ext = ext.toLowerCase();
    }

    @Override
    public boolean accept(File dir, String name) {
      return name.toLowerCase().endsWith(ext);
    }
  }

  @Override
  protected void compute() {
    try {
      System.out.println("------- New Task is running --------\n");
      findFiles();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
