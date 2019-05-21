package guuDebugger;

import guuDebugger.Commands.CallCommand;
import guuDebugger.Commands.Command;
import guuDebugger.Commands.PrintCommand;
import guuDebugger.Commands.SetCommand;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

class TheParser {
  private static String methodsName = "sub";
  private int mainLineNumber;

  Map<String, TheMethodWalker> parse(BufferedReader br) throws Exception {
    Map<String, TheMethodWalker> code = new HashMap<>();
    TheMethodWalker currentMethod = new TheMethodWalker();
    String currentMethodName = null;

    String currentString;
    int lineNumber = -1;
    while ((currentString = br.readLine()) != null) {
      lineNumber++;
      currentString = currentString.trim();
      if (currentString.length() == 0) {
        continue;
      }
      String[] splitted = currentString.split(" ");
      if (methodsName.equals(splitted[0])) {
        if (currentMethodName != null) {
          code.put(currentMethodName, currentMethod);
        }
        currentMethodName = splitted[1];
        if (splitted[1].equals("main")) {
          mainLineNumber = lineNumber;
        }
        currentMethod = new TheMethodWalker();
      } else {
        currentMethod.add(parseCommand(splitted, lineNumber));
      }
    }
    code.put(currentMethodName, currentMethod);
    return code;
  }

  private Command parseCommand(String[] splitted, int lineNumber) throws Exception {
    String commandName = splitted[0];
    Command command;
    if (PrintCommand.name().equals(commandName)) {
      command = new PrintCommand(lineNumber, splitted[1]);
    } else if (SetCommand.name().equals(commandName)) {
      command = new SetCommand(lineNumber, splitted[1], splitted[2]);
    } else if (CallCommand.name().equals(commandName)) {
      command = new CallCommand(lineNumber, splitted[1]);
    } else {
      throw new Exception(commandName + " is an unknown command");
    }
    return command;
  }

  int getMainLineNumber() {
    return mainLineNumber;
  }
}
