package guuDebugger;

import guuDebugger.Commands.CallCommand;
import guuDebugger.Commands.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

class TheMethodWalker {
  enum Status {WAIT, GO, SKIP}
  private List<Command> commands = new ArrayList<>();

  void add(Command command) {
    commands.add(command);
  }

  Map<String, String> eval(Map<String, String> env, Map<String, TheMethodWalker> program,
      List<String> currentStackTrace, BlockingQueue<String> commandQueue) throws InterruptedException {
    Status readyToContinue = Status.WAIT;
    for (Command command : commands) {
      if (!readyToContinue.equals(Status.SKIP)) {
        readyToContinue = Status.WAIT;
      }
      while (readyToContinue.equals(Status.WAIT)) {
        String debugCommand = commandQueue.take();
        if (debugCommand.equals("i")) {
          readyToContinue = Status.GO;
        } else if (debugCommand.equals("o")) {
          readyToContinue = Status.SKIP;
        } else if (debugCommand.equals("trace")) {
          printTrace(currentStackTrace);
        } else if (debugCommand.equals("var")) {
          printValues(env);
        } else {
          System.out.println("this command is not supported, use: i / o / trace / var / exit");
        }
      }
      printLine(command);

      if (command instanceof CallCommand) {
        CallCommand callCommand = (CallCommand) command;
        currentStackTrace.add(callCommand.trace());
        env = program.get(callCommand.getMethod()).eval(env, program, currentStackTrace, commandQueue);
      } else {
        env = command.eval(env);
      }
    }
    return env;
  }

  private void printLine(Command command) {
    System.out.println(command.trace());
  }

  private void printTrace(List<String> currentStackTrace) {
    ListIterator<String> li = currentStackTrace.listIterator(currentStackTrace.size());
    while (li.hasPrevious()) {
      System.out.println(li.previous());
    }
  }

  private void printValues(Map<String, String> env) {
    for (Map.Entry<String, String> entry : env.entrySet()) {
      System.out.println(entry.getKey() + " = " + entry.getValue());
    }
    System.out.println();
  }
}
