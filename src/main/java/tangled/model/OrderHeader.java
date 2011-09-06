package tangled.model;

import java.util.HashMap;
import java.util.Map;

public class OrderHeader implements java.io.Serializable {

	private static final long serialVersionUID = -6049273461319124365L;

  private String productName;
  private Map<String, OrderSiteDetail> orderSiteDetails = new HashMap<String, OrderSiteDetail>();

	/**
	 * @param productName
	 */
	public OrderHeader(String productName) {
		super();
		this.productName = productName;
		this.orderSiteDetails = new HashMap<String, OrderSiteDetail>();
  }

	/**
	 * Copy constructor (only copying basic elements in use!!)
	 *
	 * @param orderHeader
	 */
	public OrderHeader(OrderHeader orderHeader) {
		super();
		// TODO: copy all elements.
		this.productName = orderHeader.productName;
		// not deep copying this intentionally.
		this.orderSiteDetails = orderHeader.orderSiteDetails;
	}

  /**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

  public Map<String, OrderSiteDetail> getOrderSiteDetails() {
		return this.orderSiteDetails;
	}

	public void setOrderSiteDetails(
			Map<String, OrderSiteDetail> orderSiteDetails) {
		this.orderSiteDetails = orderSiteDetails;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((orderSiteDetails == null) ? 0 : orderSiteDetails.hashCode());
    result = prime * result
        + ((productName == null) ? 0 : productName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrderHeader other = (OrderHeader) obj;
    if (orderSiteDetails == null) {
      if (other.orderSiteDetails != null)
        return false;
    } else if (!orderSiteDetails.equals(other.orderSiteDetails))
      return false;
    if (productName == null) {
      if (other.productName != null)
        return false;
    } else if (!productName.equals(other.productName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "OrderHeader [productName=" + productName + ", orderSiteDetails="
        + orderSiteDetails + "]";
  }
	
	
}
