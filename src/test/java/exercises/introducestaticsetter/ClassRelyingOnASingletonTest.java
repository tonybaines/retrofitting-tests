package exercises.introducestaticsetter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

/**
 * Introduce Static Setter: Especially useful when dealing with dependencies on a Singleton [GoF].
 * Add a static setter method to inject an instance of the singleton, use it to replace expensive
 * behaviour with a test-double
 * 
 * - Add a new setter method to the Singleton that sets the value of the private instance field
 * - Weaken the access permission on the Singleton constructor to allow subclassing
 * - In the test case, call the static setter, injecting a subclass (e.g. anonymous inner class) 
 *   of the class under test, overriding the with stub behaviour   
 *
 */
public class ClassRelyingOnASingletonTest {

  @Test
  public void test() {
    // Problem: We don't want to use the expensive singleton implementation for unit tests
    assertThat(new ClassRelyingOnASingleton().greet("Tony"), is("Hello Tony"));
  }

}
