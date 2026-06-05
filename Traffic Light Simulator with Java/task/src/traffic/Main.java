package traffic;

import java.util.ArrayDeque;
import java.util.Scanner;

/** Entry point for the Traffic Management System console application. */
public class Main {

  private static volatile boolean systemState = false;
  private static volatile int seconds = 0;
  private static final ArrayDeque<String> queue = new ArrayDeque<>();

  /** Prompts for validated road count and interval, spawns QueueThread, then runs the looped menu. */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to the traffic management system!");
    System.out.println("Input the number of roads:");
    int numberOfRoads = readPositiveInt(scanner);

    System.out.println("Input the interval:");
    int interval = readPositiveInt(scanner);

    Thread queueThread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          Thread.sleep(1000);
          seconds++;
          if (systemState) {
            printSystemInfo(seconds, numberOfRoads, interval);
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
    queueThread.setName("QueueThread");
    queueThread.start();

    while (true) {
      printMenu();
      String option = scanner.nextLine().trim();
      switch (option) {
        case "1":
          System.out.println("Input road name:");
          String roadName = scanner.nextLine().trim();
          synchronized (queue) {
            if (queue.size() >= numberOfRoads) {
              System.out.println("Queue is full");
            } else {
              queue.add(roadName);
              System.out.println(roadName + " Added!");
            }
          }
          scanner.nextLine();
          break;
        case "2":
          synchronized (queue) {
            if (queue.isEmpty()) {
              System.out.println("Queue is empty");
            } else {
              System.out.println(queue.poll() + " deleted!");
            }
          }
          scanner.nextLine();
          break;
        case "3":
          systemState = true;
          scanner.nextLine();
          systemState = false;
          break;
        case "0":
          queueThread.interrupt();
          System.out.println("Bye!");
          return;
        default:
          System.out.println("Incorrect option");
          scanner.nextLine();
      }
    }
  }

  /** Prints elapsed seconds, settings, all queued road names (front to rear), and the Enter prompt. */
  private static void printSystemInfo(int s, int roads, int interval) {
    System.out.println("! " + s + "s. have passed since system startup !");
    System.out.println("! Number of roads: " + roads + " !");
    System.out.println("! Interval: " + interval + " !");
    synchronized (queue) {
      for (String road : queue) {
        System.out.println(road);
      }
    }
    System.out.println("! Press \"Enter\" to open menu !");
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
