package tangled.model;

import java.util.HashMap;
import java.util.Map;

public class OrderSiteDetail implements java.io.Serializable {

	private static final long serialVersionUID = -4397734267040106745L;

  private Map<String, ProductSelection> productSelections = new HashMap<String, ProductSelection>();

  /**
	 * @return the orderProductSelections
	 */
	public Map<String, ProductSelection> getProductSelections() {
		return productSelections;
	}

	public void setProductSelections(
			Map<String, ProductSelection> productSelections) {
		this.productSelections = productSelections;
	}

  @Override
  public String toString() {
    return "OrderSiteDetail [productSelections=" + productSelections + "]";
  }
}
