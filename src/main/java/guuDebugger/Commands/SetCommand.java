package guuDebugger.Commands;

import java.util.Map;

public class SetCommand implements Command {
  private int line;
  private String variableName;
  private String newValue;

  public SetCommand(int line, String variableName, String newValue) throws Exception {
    this.line = line;
    this.variableName = variableName;
    this.newValue = newValue;
    try {
      int ignored = Integer.parseInt(newValue);
    } catch (NumberFormatException e) {
      throw new Exception("operator set supports only integers");
    }
  }

  static public String name() {
    return "set";
  }

  @Override
  public Map<String, String> eval(Map<String, String> env) {
    env.put(variableName, newValue);
    return env;
  }

  @Override
  public String trace() {
    return line + ": " + name() + " " + variableName + " " + newValue;
  }
}
