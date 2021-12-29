package JavaCleanerLab1;

import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class main {
  private static final int NUM_THREADS= 10;

  public static void main(String[] args) {
    System.out.println("--------------- Main thread is running ---------------\n ");
    Scanner in = new Scanner(System.in);
    System.out.println("Input directory path: ");
    String path = in.nextLine();
    path = "D:\\Course #3\\ParallelProgramming\\Labs\\Lab_2\\src";
    try {
      ForkJoinPool pool = new ForkJoinPool(NUM_THREADS);
      pool.invoke(new FindJavaFiles(path));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
