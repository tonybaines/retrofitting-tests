package tangled.util;

import org.junit.*;
import tangled.model.OrderHeader;

import java.util.List;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class OrderUtilsCharacterisationTest {

  @Test(expected = NullPointerException.class)
  public void callingMergeHeadersWithNullValuesShouldThrowAnException() throws Exception {
    OrderUtils.mergeHeaders(null,null);
  }

  @Test
  public void callingMergeHeadersWithAnEmptyOrderHeaderListShouldReturnNull() throws Exception {
    assertThat(OrderUtils.mergeHeaders(new OrderHeaderFixture().withAnEmptyListOfOrderHeaders(), null),
               is(nullValue()));
  }

  @Test
  public void passingJustASingleOrderHeaderToMergeHeadersShouldReturnWHAT() throws Exception {
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT")
                                                                   .build();
    
    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(new OrderHeader("merged-for-tree")));
  }

}
