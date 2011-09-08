package exercises.exposestaticmethod;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

/**
 * Expose Static Method: change an instance method that doesn't make use of any instance 
 * variables into a static method and call it.  Useful where it's hard to get an instance 
 * of the class under test and the significant logic fits the above criterion.
 *
 */
public class HardToInstantiateClassTest {

  @Test
  public void testUsefulMethod() {
    // Problem: can't instantiate this class without a lot more work
//    assertThat(new HardToInstantiateClass(db, config, socket).translate("ORA-00001"), 
//               is("Unique constraint violated. (Invalid data has been rejected)"));
  }

}
