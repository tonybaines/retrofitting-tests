package tangled.util;

import org.junit.*;
import tangled.model.OrderHeader;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class OrderUtilsCharacterisationTest {

  @Test(expected = NullPointerException.class)
  public void callingMergeHeadersWithNullValuesShouldThrowAnException() throws Exception {
    OrderUtils.mergeHeaders(null,null);
  }

  @Test
  public void callingMergeHeadersWithAnEmptyOrderHeaderListShouldReturnNull() throws Exception {
    assertThat(OrderUtils.mergeHeaders(OrderHeaderFixture.emptyListOfOrderHeaders(), null),
               is(nullValue()));
  }

  @Test
  public void passingJustASingleOrderHeaderToMergeHeadersShouldReturnWHAT() throws Exception {
    Collection<OrderHeader> orderHeaders = OrderHeaderFixture.listOfOrderHeaders()
                                                             .
                                                             .build();
    assertThat(OrderUtils.mergeHeaders(orderHeaders, null), is(not(nullValue())));
  }

}
