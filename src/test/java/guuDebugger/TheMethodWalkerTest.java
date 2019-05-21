package guuDebugger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Test;

public class TheMethodWalkerTest {

  @Test
  public void parse1() {
    TheParser parser = new TheParser();
    String test = "sub main\n"
        + "    set a 1\n"
        + "    call foo\n"
        + "    print a\n"
        + "\n"
        + "sub foo\n"
        + "    set a 2";
    BufferedReader reader = new BufferedReader(new StringReader(test));
    try {
      Map<String, TheMethodWalker> program = parser.parse(reader);
      Map<String, String> env = new HashMap<>();
      TheMethodWalker main = program.get("main");
      List<String> currentStackTrace = new LinkedList<>();
      BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
      commandQueue.add("o");
      commandQueue.add("o");
      commandQueue.add("o");
      commandQueue.add("o");
      env = main.eval(env, program, currentStackTrace, commandQueue);
      assertEquals("2", env.get("a"));
    } catch (Exception e) {
      System.err.println(e.getMessage());
      fail();
    }
  }

  @Test
  public void parse2() {
    TheParser parser = new TheParser();
    String test = "sub f1\n"
        + "set a 1\n"
        + "call f2\n"
        + "\n"
        + "sub f2\n"
        + "set a 2\n"
        + "\n"
        + "sub main\n"
        + "call f1\n"
        + "print a\n"
        + "\n"
        + "sub f3\n"
        + "set a 8";
    BufferedReader reader = new BufferedReader(new StringReader(test));
    try {
      Map<String, TheMethodWalker> program = parser.parse(reader);
      Map<String, String> env = new HashMap<>();
      TheMethodWalker main = program.get("main");
      List<String> currentStackTrace = new LinkedList<>();
      BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
      commandQueue.add("o");
      commandQueue.add("o");
      commandQueue.add("o");
      commandQueue.add("o");
      env = main.eval(env, program, currentStackTrace, commandQueue);
      assertEquals("2", env.get("a"));
    } catch (Exception e) {
      System.err.println(e.getMessage());
      fail();
    }
  }
}
