package guuDebugger.Commands;

import java.util.Map;

public class PrintCommand implements Command {
  private int line;
  private String variable;

  public PrintCommand(int line, String variable) {
    this.line = line;
    this.variable = variable;
  }

  static public String name() {
    return "print";
  }

  @Override
  public Map<String, String> eval(Map<String, String> env) {
    System.out.println(variable + " = " + env.get(variable));
    return env;
  }

  @Override
  public String trace() {
    return line + ": " + name() + " " + variable;
  }
}
