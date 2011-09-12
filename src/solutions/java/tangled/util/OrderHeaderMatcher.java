package tangled.util;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import tangled.model.OrderHeader;
import tangled.model.OrderSiteDetail;

public class OrderHeaderMatcher extends TypeSafeMatcher<OrderHeader> {

  private final OrderHeader expectedOrderHeader;

  public OrderHeaderMatcher(String headerLabel,
      Map<String, OrderSiteDetail> ...orderSiteDetails) {
      expectedOrderHeader = new OrderHeader(headerLabel);
      Map<String, OrderSiteDetail> mergedOrderSiteDetails = new HashMap<String, OrderSiteDetail>();
      for (Map<String, OrderSiteDetail> orderSiteDetail : orderSiteDetails) {
        mergedOrderSiteDetails.putAll(orderSiteDetail);
      }
      expectedOrderHeader.setOrderSiteDetails(mergedOrderSiteDetails);
  }

  @Override
  public boolean matchesSafely(OrderHeader item) { 
    return this.expectedOrderHeader.equals(item); 
  }
  
  public void describeTo(Description description) {
    description.appendText("An OrderHeader " + this.expectedOrderHeader);
  }

  @Factory
  public static <T> Matcher<OrderHeader> aHeaderMatching(String headerLabel, Map<String, OrderSiteDetail> ...orderSiteDetails) {
    return new OrderHeaderMatcher(headerLabel, orderSiteDetails);
  }

}