package guuDebugger.Commands;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrintCommandTest {

  @Test
  public void trace() {
    Command command = new PrintCommand(109, "x");
    assertEquals("109: print x", command.trace());
  }
}
