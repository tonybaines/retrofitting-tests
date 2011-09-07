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
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((productSelections == null) ? 0 : productSelections.hashCode());
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
    OrderSiteDetail other = (OrderSiteDetail) obj;
    if (productSelections == null) {
      if (other.productSelections != null)
        return false;
    } else if (!productSelections.equals(other.productSelections))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "OrderSiteDetail [productSelections=" + productSelections + "]";
  }
}
