package tangled.util;

import java.util.HashMap;
import java.util.Map;

import tangled.model.OrderSiteDetail;
import tangled.model.ProductSelection;

public class OrderSiteFixture {

  private String siteId;
  private Map<String, ProductSelection> products = new HashMap<String, ProductSelection>();

  public OrderSiteFixture forSite(String siteId) {
    this.siteId = siteId;
    return this;
  }

  public OrderSiteFixture withProduct(String productName, String productSelectionName) {
    this.products .put(productName, new ProductSelection(productSelectionName));
    return this;
  }

  public Map<String, OrderSiteDetail> build() {
    final OrderSiteDetail orderSiteDetail = new OrderSiteDetail();
    orderSiteDetail.setProductSelections(products);
    return new HashMap<String, OrderSiteDetail>() {{
      put(siteId, orderSiteDetail);
    }};
  }

}
