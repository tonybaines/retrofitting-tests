package tangled.util;

import tangled.model.OrderHeader;

import java.util.ArrayList;
import java.util.Collection;

public class OrderHeaderFixture {
  private Collection<OrderHeader> headersCollection = new ArrayList<OrderHeader>();

  public static OrderHeaderFixture listOfOrderHeaders() {
    return new OrderHeaderFixture();
  }

  public static Collection<OrderHeader> emptyListOfOrderHeaders() {
    return new ArrayList<OrderHeader>();
  }

  public Collection<OrderHeader> build() {
    return this.headersCollection;
  }
}
