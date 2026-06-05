package traffic;

import java.util.Scanner;

/** Entry point for the Traffic Management System console application. */
public class Main {

  /** Prompts for validated road count and interval, then runs the looped menu until the user quits. */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to the traffic management system!");
    System.out.println("Input the number of roads:");
    int numberOfRoads = readPositiveInt(scanner);

    System.out.println("Input the interval:");
    int interval = readPositiveInt(scanner);

    while (true) {
      printMenu();
      String option = scanner.nextLine().trim();
      switch (option) {
        case "1":
          System.out.println("Road added");
          scanner.nextLine();
          break;
        case "2":
          System.out.println("Road deleted");
          scanner.nextLine();
          break;
        case "3":
          System.out.println("System opened");
          scanner.nextLine();
          break;
        case "0":
          System.out.println("Bye!");
          return;
        default:
          System.out.println("Incorrect option");
          scanner.nextLine();
      }
    }
  }

  /** Loops reading lines until the user enters a positive integer, printing an error on each bad input. */
  private static int readPositiveInt(Scanner scanner) {
    while (true) {
      String input = scanner.nextLine().trim();
      try {
        int value = Integer.parseInt(input);
        if (value > 0) return value;
      } catch (NumberFormatException ignored) {}
      System.out.println("Error! Incorrect input. Try again:");
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
