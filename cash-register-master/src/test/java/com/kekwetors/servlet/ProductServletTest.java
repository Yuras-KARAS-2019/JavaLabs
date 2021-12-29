package com.kekwetors.servlet;

import com.kekwetors.BaseTest;
import com.kekwetors.web.ProductServlet;
import lombok.SneakyThrows;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.mockito.Mockito.*;

public class ProductServletTest extends BaseTest {

  @Test
  @SneakyThrows
  public void doGetTest() {
    final ProductServlet servlet = new ProductServlet();

    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final PrintWriter printWriter = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(printWriter);

    servlet.doGet(request, response);
    verify(request, times(1)).getParameter("id");
    verify(request, times(0)).getReader();
    verify(response, times(1)).getWriter();
  }
}
