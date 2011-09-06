package tangled.util;

import tangled.model.OrderHeader;
import tangled.model.OrderSiteDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderHeaderFixture {
  private List<OrderHeader> headersCollection = new ArrayList<OrderHeader>();

  public List<OrderHeader> withAnEmptyListOfOrderHeaders() {
    return new ArrayList<OrderHeader>();
  }

  public List<OrderHeader> build() {
    return this.headersCollection;
  }

  public OrderHeaderFixture withAHeaderForProduct(String productName, Map<String, OrderSiteDetail> orderSiteDetails) {
    OrderHeader header = new OrderHeader(productName);
    header.setOrderSiteDetails(orderSiteDetails);
    headersCollection.add(header);
    return this;
  }
  
  public OrderHeaderFixture withAHeaderForProduct(String productName) {
    headersCollection.add(new OrderHeader(productName));
    return this;
  }
}
