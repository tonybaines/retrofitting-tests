package tangled.util;

import tangled.model.OrderHeader;
import tangled.model.OrderSiteDetail;

import java.util.ArrayList;
import java.util.HashMap;
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

  public OrderHeaderFixture withAHeaderForProduct(String productName, Map<String, OrderSiteDetail> ...orderSiteDetails) {
    OrderHeader header = new OrderHeader(productName);
    Map<String, OrderSiteDetail> mergedOrderSiteDetails = new HashMap<String, OrderSiteDetail>();
    for (Map<String, OrderSiteDetail> orderSiteDetail : orderSiteDetails) {
      mergedOrderSiteDetails.putAll(orderSiteDetail);
    }
    header.setOrderSiteDetails(mergedOrderSiteDetails);
    headersCollection.add(header);
    return this;
  }
  
  public OrderHeaderFixture withAHeaderForProduct(String productName) {
    headersCollection.add(new OrderHeader(productName));
    return this;
  }
}
