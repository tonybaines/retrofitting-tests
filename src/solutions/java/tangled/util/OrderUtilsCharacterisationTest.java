package tangled.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tangled.model.OrderHeader;
import tangled.model.OrderSiteDetail;

public class OrderUtilsCharacterisationTest {

  @Test(expected = NullPointerException.class)
  public void callingMergeHeadersWithNullValuesShouldThrowAnException() throws Exception {
    OrderUtils.mergeHeaders(null,null);
  }

  @Test
  public void callingMergeHeadersWithAnEmptyOrderHeaderListShouldReturnNull() throws Exception {
    assertThat(OrderUtils.mergeHeaders(new OrderHeaderFixture().withAnEmptyListOfOrderHeaders(), null),
               is(nullValue()));
  }

  
  /*
   * Try with a single header, assert something (NPE => needs a map for 2nd param)
   * Add the header, test fails
   * Fix the assertion to match reality
   */
  @Test
  public void passingJustASingleOrderHeaderToMergeHeadersShouldReturnASingleMergedHeader() throws Exception {
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT")
                                                             .build();
    
    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(new OrderHeader("merged-for-tree")));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void passingASingleHeaderWithSiteDetailsShouldReturnAMergedHeaderWithTheSiteDetails() throws Exception {
    Map<String, OrderSiteDetail> siteDetails = new OrderSiteFixture().forSite("site1")
                                                                     .withProduct("prod1", "detail1")
                                                                     .build();
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT", siteDetails)
                                                             .build();

    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(OrderHeaderMatcher.aHeaderMatching("merged-for-tree", siteDetails)));
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void passingTwoHeadersWithDifferentSiteDetailsShouldReturnAMergedHeaderWithTheSiteDetails() throws Exception {
    Map<String, OrderSiteDetail> siteDetails1 = new OrderSiteFixture().forSite("site1")
                                                                      .withProduct("prod1", "detail1")
                                                                      .build();
    Map<String, OrderSiteDetail> siteDetails2 = new OrderSiteFixture().forSite("site2")
        .withProduct("prod2", "detail2")
        .build();
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT1", siteDetails1)
                                                             .withAHeaderForProduct("TEST-PRODUCT2", siteDetails2)
                                                             .build();

    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(OrderHeaderMatcher.aHeaderMatching("merged-for-tree", siteDetails1, siteDetails2)));
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void passingTwoHeadersWithTheSameSiteDetailsShouldReturnAMergedHeaderWithTheSiteDetailsAppearingOnceOnly() throws Exception {
    Map<String, OrderSiteDetail> siteDetails1 = new OrderSiteFixture().forSite("site1")
                                                                      .withProduct("prod1", "detail1")
                                                                      .build();
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT1", siteDetails1)
                                                             .withAHeaderForProduct("TEST-PRODUCT2", siteDetails1)
                                                             .build();

    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(OrderHeaderMatcher.aHeaderMatching("merged-for-tree", siteDetails1)));
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void passingTwoHeadersWithTheSameSiteButDifferentDetailsShouldReturnAMergedHeader() throws Exception {
    List<OrderHeader> orderHeaders = new OrderHeaderFixture().withAHeaderForProduct("TEST-PRODUCT1", new OrderSiteFixture().forSite("site1")
                                                                                                                           .withProduct("prod1", "detail1")
                                                                                                                           .build())
                                                             .withAHeaderForProduct("TEST-PRODUCT2", new OrderSiteFixture().forSite("site1")
                                                                                                                           .withProduct("prod2", "detail2")
                                                                                                                           .build())
                                                             .build();

    Map<String, OrderSiteDetail> expectedSiteDetails = new OrderSiteFixture().forSite("site1")
        .withProduct("prod1", "detail1")
        .withProduct("prod2", "detail2")
        .build();
    assertThat(OrderUtils.mergeHeaders(orderHeaders, new HashMap<String, OrderHeader>()), is(OrderHeaderMatcher.aHeaderMatching("merged-for-tree", expectedSiteDetails)));
  }
  

}
