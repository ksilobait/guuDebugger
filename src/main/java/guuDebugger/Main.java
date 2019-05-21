package guuDebugger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("pass path to the file");
      return;
    }
    File file = new File(args[0]);
    TheParser parser = new TheParser();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      Map<String, TheMethodWalker> program = parser.parse(br);
      TheMethodWalker mainMethod = program.get("main");
      if (mainMethod == null) {
        throw new Exception("no main method");
      }

      BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
      Map<String, String> env = new HashMap<>();
      List<String> currentStackTrace = new ArrayList<>();
      currentStackTrace.add(parser.getMainLineNumber() + ": sub main");

      EvalTask evalTask = new EvalTask(mainMethod, env, program, currentStackTrace, commandQueue);
      Thread evalThread = new Thread(evalTask);
      ScannerTask scannerTask = new ScannerTask(commandQueue);
      Thread scannerThread = new Thread(scannerTask);

      evalThread.start();
      scannerThread.start();

      evalThread.join();
      System.out.println("program has finished execution");
      System.exit(0);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  static class ScannerTask implements Runnable {
    private BlockingQueue<String> commandQueue;

    ScannerTask(BlockingQueue<String> commandQueue) {
      this.commandQueue = commandQueue;
    }

    public void run() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("waiting for user input:");
      String input = scanner.nextLine();
      while (!input.equals("exit")) {
        commandQueue.add(input);
        input = scanner.nextLine();
      }
      System.exit(0);
    }
  }

  static class EvalTask implements Runnable {
    private TheMethodWalker mainMethod;
    private Map<String, String> env;
    private Map<String, TheMethodWalker> program;
    private List<String> currentStackTrace;
    private BlockingQueue<String> commandQueue;

    EvalTask(TheMethodWalker mainMethod, Map<String, String> env,
        Map<String, TheMethodWalker> program, List<String> currentStackTrace,
        BlockingQueue<String> commandQueue) {
      this.mainMethod = mainMethod;
      this.env = env;
      this.program = program;
      this.currentStackTrace = currentStackTrace;
      this.commandQueue = commandQueue;
    }

    public void run() {
      try {
        mainMethod.eval(env, program, currentStackTrace, commandQueue);
      } catch (InterruptedException ignored) {
      }
    }
  }
}
