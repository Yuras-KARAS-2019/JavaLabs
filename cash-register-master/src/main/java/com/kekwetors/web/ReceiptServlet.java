package com.kekwetors.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kekwetors.converter.ReceiptConverter;
import com.kekwetors.converter.ReceiptProductConverter;
import com.kekwetors.dao.ReceiptDao;
import com.kekwetors.dao.model.ReceiptProduct;
import com.kekwetors.web.dto.ReceiptDto;
import com.kekwetors.web.dto.ReceiptProductDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@WebServlet("/api/v0/receipts")
public class ReceiptServlet extends AbstractBaseServlet {

  private static final ReceiptDao receiptDao = factory.getReceiptDao();
  private static final ReceiptConverter receiptConverter = new ReceiptConverter();
  private static final ReceiptProductConverter receiptProductConverter = new ReceiptProductConverter();

  @Override
  @SneakyThrows
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    if (id != null) {
      sendResponse(receiptConverter.toDto(receiptDao.getReceiptById(Integer.parseInt(id))), resp);
    } else {
      sendResponse(receiptDao.getAllReceipts().stream()
                      .map(receiptConverter::toDto)
                      .collect(Collectors.toList()),
              resp);
    }
  }

  @Override
  @SneakyThrows
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    String insertProductsRelatedToReceipt = req.getParameter("insertProducts");

    if (id != null && insertProductsRelatedToReceipt != null) {
      String JSONRequest = parseRequest(req);
      List<ReceiptProductDto> receiptProductDtos = objectMapper.readValue(JSONRequest, new TypeReference<>() {});

      for (ReceiptProductDto receiptProductDto : receiptProductDtos) {
        ReceiptProduct receiptProduct = receiptProductConverter.toModel(receiptProductDto);
        receiptDao.insertProductIntoReceipt(receiptProduct, Integer.parseInt(id));
      }

      sendResponse(receiptConverter.toDto(receiptDao.getReceiptById(Integer.parseInt(id))), resp);
    } else {
      String JSONRequest = parseRequest(req);
      ReceiptDto receiptDto = objectMapper.readValue(JSONRequest, ReceiptDto.class);
      sendResponse(receiptConverter.toDto(receiptDao.insertReceipt(receiptConverter.toModel(receiptDto))), resp);
    }
  }

  @Override
  @SneakyThrows
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    String JSONRequest = parseRequest(req);
    ReceiptDto receiptDto = objectMapper.readValue(JSONRequest, ReceiptDto.class);
    receiptDao.updateReceipt(receiptConverter.toModel(receiptDto));
    sendResponse(receiptConverter.toDto(receiptDao.getReceiptById(receiptDto.getId())), resp);
  }

  @Override
  @SneakyThrows
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    String id = req.getParameter("id");
    ReceiptDto deletedReceipt = receiptConverter.toDto(receiptDao.getReceiptById(Integer.parseInt(id)));
    receiptDao.deleteReceipt(Integer.parseInt(id));
    sendResponse(deletedReceipt, resp);
  }

}
