package traffic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Entry point for the Traffic Management System console application. */
public class Main {

  private static volatile boolean systemState = false;
  private static volatile int seconds = 0;
  private static final ArrayDeque<Road> queue = new ArrayDeque<>();
  private static final ArrayDeque<Road> rotation = new ArrayDeque<>();
  private static int timer = 0;

  /** A road tracked by name and identity, so the same name can occupy distinct queue slots. */
  private static class Road {
    final String name;

    Road(String name) {
      this.name = name;
    }
  }

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
          synchronized (queue) {
            if (systemState) {
              printSystemInfo(seconds, numberOfRoads, interval);
            }
            if (!rotation.isEmpty()) {
              timer--;
              if (timer == 0) {
                rotation.addLast(rotation.pollFirst());
                timer = interval;
              }
            }
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
              Road road = new Road(roadName);
              queue.addLast(road);
              rotation.addLast(road);
              if (rotation.size() == 1) {
                timer = interval;
              }
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
              Road removed = queue.pollFirst();
              boolean wasOpen = rotation.peekFirst() == removed;
              rotation.remove(removed);
              if (wasOpen && !rotation.isEmpty()) {
                timer = interval;
              }
              System.out.println(removed.name + " deleted!");
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

  /** Prints elapsed seconds, settings, each road's open/closed state and countdown, and the Enter prompt; caller must hold the queue's monitor. */
  private static void printSystemInfo(int s, int roads, int interval) {
    System.out.println("! " + s + "s. have passed since system startup !");
    System.out.println("! Number of roads: " + roads + " !");
    System.out.println("! Interval: " + interval + " !");
    List<Road> order = new ArrayList<>(rotation);
    for (Road road : queue) {
      int position = order.indexOf(road);
      boolean open = position == 0;
      int remaining = open ? timer : timer + (position - 1) * interval;
      System.out.println("Road \"" + road.name + "\" will be "
              + (open ? "open" : "closed") + " for " + remaining + "s.");
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
