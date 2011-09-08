package exercises.adaptparameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DifficultParameter {

  public void handleRestRequest(HttpServletRequest request,
      HttpServletResponse response) {
    String requestType = request.getHeader("request-type");

    switch (RequestType.valueOf(requestType)) {
    case GET:
      response.addHeader("repsonse-type", "GET");
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      break;
    case PUT:
      response.addHeader("repsonse-type", "PUT");
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      break;
    case POST:
      response.addHeader("repsonse-type", "POST");
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      break;
    case DELETE:
      response.addHeader("repsonse-type", "DELETE");
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      break;
    default:
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      throw new IllegalArgumentException("I can't handle requests of type "
          + requestType);
    }
  }
}
