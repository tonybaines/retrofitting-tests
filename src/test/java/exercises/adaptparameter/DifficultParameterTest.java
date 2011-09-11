package exercises.adaptparameter;

import org.junit.Test;

/**
 * Adapt Parameter: use the Adaptor [GoF] pattern to extract an interface for the 
 * difficult parameter(s) defining just the subset of methods that are used in the
 * production code.
 * 
 * - Write a default implementation of the interface that delegates to the 'real' parameter
 * - Create a new method in the production class that accepts the adapted parameters and 
 *   contains the original business logic
 * - Change the original method to uses the default implementation to wrap the original 
 *   parameters and call the new method
 *   
 * Tests can now call the new method with stub instances of the adaptor interface (perhaps
 * with additional methods for sensing)
 */
public class DifficultParameterTest {
  @Test
  public void shouldHandleGetRequestsAppropriately() throws Exception {
    // Problem: HttpServletRequest and HttpServletResponse aren't easy to stub out
    //new DifficultParameter().handleRestRequest(request, response);
  }

}
