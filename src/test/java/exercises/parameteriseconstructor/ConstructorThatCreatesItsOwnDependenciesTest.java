package exercises.parameteriseconstructor;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

/**
 * Parameterise Constructor: Introduce an overloaded constructor that allows expensive
 * dependencies to be injected, use this constructor for testing
 * 
 * - Make a copy of the expensive constructor
 * - Add a parameter to the copy to allow a dependency to be injected, remove the call
 *   to new ...()
 * - Change the original constructor to delegate to the new constructor, passing in the 
 *   instance it creates with new ...()
 * - Use the new constructor in your test case, injecting a subclass (e.g. anonymous inner class) 
 *   of the class under test, overriding the with stub behaviour
 *
 */
public class ConstructorThatCreatesItsOwnDependenciesTest {

  @Test
  public void test() {
    // Problem: the act of creating an instance of the class under test also means
    // creating much more
    assertThat(new ConstructorThatCreatesItsOwnDependencies().messageOfTheDay(),
        is("Keep calm and carry on coding"));
  }

}
