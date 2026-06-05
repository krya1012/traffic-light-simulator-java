package traffic;

import java.util.Scanner;

/** Entry point for the Traffic Management System console application. */
public class Main {

  /** Prompts for road count and interval, then runs the looped menu until the user quits. */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to the traffic management system!");
    System.out.println("Input the number of roads:");
    int numberOfRoads = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Input the interval:");
    int interval = scanner.nextInt();
    scanner.nextLine();

    while (true) {
      printMenu();
      String option = scanner.nextLine().trim();
      switch (option) {
        case "1":
          System.out.println("Road added");
          break;
        case "2":
          System.out.println("Road deleted");
          break;
        case "3":
          System.out.println("System opened");
          break;
        case "0":
          System.out.println("Bye!");
          return;
      }
    }
  }

  /** Prints the five-line menu to stdout. */
  private static void printMenu() {
    System.out.println("Menu:");
    System.out.println("1. Add road");
    System.out.println("2. Delete road");
    System.out.println("3. Open system");
    System.out.println("0. Quit");
  }
}
