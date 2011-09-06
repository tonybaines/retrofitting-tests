package tangled.util;

import tangled.model.OrderHeader;

import java.util.ArrayList;
import java.util.List;

public class OrderHeaderFixture {
  private List<OrderHeader> headersCollection = new ArrayList<OrderHeader>();

  public List<OrderHeader> withAnEmptyListOfOrderHeaders() {
    return new ArrayList<OrderHeader>();
  }

  public List<OrderHeader> build() {
    return this.headersCollection;
  }

  public OrderHeaderFixture withAHeaderForProduct(String productName) {
    headersCollection.add(new OrderHeader(productName));
    return this;
  }
}
