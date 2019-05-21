package guuDebugger.Commands;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class SetCommandTest {
  @Test
  public void eval() {
    try {
      Command command = new SetCommand(108, "x", "5");
      Map<String, String> env = new HashMap<>();
      env = command.eval(env);
      assertEquals("5", env.get("x"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void eval2() {
    try {
      Command command = new SetCommand(108, "x", "newValue");
      Map<String, String> env = new HashMap<>();
      env = command.eval(env);
      fail();
    } catch (Exception ignored) {
    }
  }


  @Test
  public void trace() throws Exception {
    Command command = new SetCommand(108, "x", "4");
    assertEquals("108: set x 4", command.trace());
  }
}
