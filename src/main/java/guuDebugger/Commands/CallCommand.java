package guuDebugger.Commands;

import java.util.Map;

public class CallCommand implements guuDebugger.Commands.Command {
  static public String name() {
    return "call";
  }

  private int line;
  private String method;

  public CallCommand(int line, String method) {
    this.line = line;
    this.method = method;
  }

  public String getMethod() {
    return method;
  }

  @Override
  public Map<String, String> eval(Map<String, String> env) {
    return env;
  }

  @Override
  public String trace() {
    return line + ": " + name() + " " + method;
  }
}
