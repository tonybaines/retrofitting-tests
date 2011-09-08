package exercises.subclassandoverridemethod;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Subclass and Override Method: One method in the class under test is causing problems.
 * Test a subclass instead, overriding the troublesome method.
 *
 */
public class OneBadAppleTest {

  @Test
  public void test() {
    // Problem: the result of the roll isn't reproducible
    OneBadApple instance = new OneBadApple();
//    final int expected = 1;
//    OneBadApple instance = new OneBadApple() {
//      @Override
//      protected int getRandomNumber() {return expected;}
//    };
    assertThat(instance.roll(), is("One"));
  }

}
