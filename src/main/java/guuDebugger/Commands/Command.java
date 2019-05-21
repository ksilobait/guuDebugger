package guuDebugger.Commands;

import java.util.Map;

public interface Command {

  Map<String, String> eval(Map<String, String> env);

  String trace();
}
