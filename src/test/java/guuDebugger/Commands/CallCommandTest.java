package guuDebugger.Commands;

import static org.junit.Assert.*;

import org.junit.Test;

public class CallCommandTest {

  @Test
  public void getMethod() {
    CallCommand command = new CallCommand(107, "func");
    assertEquals("func", command.getMethod());
  }

  @Test
  public void trace() {
    CallCommand command = new CallCommand(107, "func");
    assertEquals("107: call func", command.trace());
  }
}
