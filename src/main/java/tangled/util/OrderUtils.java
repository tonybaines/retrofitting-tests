package tangled.util;

import tangled.model.OrderHeader;
import tangled.model.OrderSiteDetail;
import tangled.model.ProductSelection;

import java.util.Collection;
import java.util.Map;

public class OrderUtils {

  public static OrderHeader mergeHeaders(Collection<OrderHeader> orderHeaderList, Map<String, OrderHeader> orderHeaders) {
    OrderHeader consolidatedOrderHeader = null;
    for (OrderHeader orderHeader : orderHeaderList) {
      orderHeaders.put(orderHeader.getProductName(), orderHeader);
      if (consolidatedOrderHeader == null) {
        consolidatedOrderHeader = new OrderHeader(orderHeader);
        consolidatedOrderHeader.setProductName("merged-for-tree");
        continue;
      }
      for (String siteId : orderHeader.getOrderSiteDetails().keySet()) {
        OrderSiteDetail orderSiteDetail = orderHeader
            .getOrderSiteDetails().get(siteId);
        if (consolidatedOrderHeader.getOrderSiteDetails().get(siteId) == null) {
          consolidatedOrderHeader.getOrderSiteDetails().put(siteId,
              orderSiteDetail);
          continue;
        }
        for (String productName : orderSiteDetail
            .getProductSelections().keySet()) {
          ProductSelection productSelection = orderSiteDetail
              .getProductSelections().get(productName);
          // actually this check is not required as this product can
          // only exist in one orderheader meant for this product. but
          // what the hell!
          if (consolidatedOrderHeader.getOrderSiteDetails()
              .get(siteId).getProductSelections()
              .get(productName) == null) {
            consolidatedOrderHeader.getOrderSiteDetails()
                .get(siteId).getProductSelections()
                .put(productName, productSelection);
          }
        }
      }
    }

    return consolidatedOrderHeader;
  }

  
}
