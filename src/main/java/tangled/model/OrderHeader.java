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
}
