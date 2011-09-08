package exercises.extractandoverridecall;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * Extract and Override Call: Pull out the part of a method that makes it hard to test into a new
 * method, then subclass and override the new method to test the original method more easily.
 * 
 * - Use the 'Extract Method' automated refactoring in your IDE to create a new (protected) method,
 *   wrapping the difficult code
 * - In the test case, create a new instance of a subclass (e.g. anonymous inner class) of the 
 *   class under test, overriding the new protected method with stub behaviour
 *
 */
public class OneComplicatedCallOutTest {

  @Test
  public void test() throws Exception {
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    // Problem: the method under test uses a call to an expensive/complicated service
    assertThat(new OneComplicatedCallOut().isThisMyLuckyDay("Tony", (Date)formatter.parse("02/07/1970")),
        is(false));
  }

}
