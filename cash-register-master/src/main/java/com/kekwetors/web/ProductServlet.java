package com.kekwetors.web;

import com.kekwetors.converter.ProductConverter;
import com.kekwetors.dao.ProductDao;
import com.kekwetors.web.dto.ProductDto;
import lombok.SneakyThrows;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;


@WebServlet("/api/v0/products")
public class ProductServlet extends AbstractBaseServlet {

  private static final ProductDao productDao = factory.getProductDao();
  private static final ProductConverter productConverter = new ProductConverter();

  @Override
  @SneakyThrows
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    String isFrontend = req.getParameter("isFrontend");
    if(id != null) {
      sendResponse(productConverter.toDto(productDao.getProductById(Integer.parseInt(id))), resp);
    } else {
      if (isFrontend != null) {
        req.setAttribute("products", productDao.getAllProducts().stream().map(productConverter::toDto).collect(Collectors.toList()));
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/productsView.jsp");
        dispatcher.forward(req, resp);
      } else {
        sendResponse(productDao.getAllProducts().stream()
                        .map(productConverter::toDto)
                        .collect(Collectors.toList()),
                resp);
      }
    }
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String JSONRequest = parseRequest(req);
    ProductDto productDto = objectMapper.readValue(JSONRequest, ProductDto.class);
    sendResponse(productConverter.toDto(productDao.insertProduct(productConverter.toModel(productDto))), resp);
  }

  @Override
  @SneakyThrows
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    String JSONRequest = parseRequest(req);
    ProductDto productDto = objectMapper.readValue(JSONRequest, ProductDto.class);
    productDao.updateProduct(productConverter.toModel(productDto));
    sendResponse(productConverter.toDto(productDao.getProductById(productDto.getId())),  resp);
  }

  @Override
  @SneakyThrows
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    ProductDto deletedProduct = productConverter.toDto(productDao.getProductById(Integer.parseInt(id)));
    productDao.deleteProduct(Integer.parseInt(id));
    sendResponse(deletedProduct,  resp);
  }
}
