package com.kekwetors.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kekwetors.dao.DaoFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static com.kekwetors.dao.DaoFactory.H2;
import static com.kekwetors.dao.DaoFactory.getDaoFactory;

@Slf4j
public class AbstractBaseServlet extends HttpServlet {

  protected final static DaoFactory factory = getDaoFactory(H2);
  protected final static ObjectMapper objectMapper = new ObjectMapper();

  protected static <T> void sendResponse(T responseObject, HttpServletResponse response) throws IOException {
    String JSONResponse = objectMapper.writeValueAsString(responseObject);

    response.setContentType("application/json");
    response.getWriter().write(JSONResponse);
  }

  @SneakyThrows
  protected String parseRequest(HttpServletRequest req) {
    String requestBody = req.getReader()
            .lines()
            .collect(Collectors.joining(""));
    log.info("RequestBody: [{}]", requestBody);
    return requestBody;
  }
}
