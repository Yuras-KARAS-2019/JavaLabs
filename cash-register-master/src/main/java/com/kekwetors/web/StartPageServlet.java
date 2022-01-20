package com.kekwetors.web;

import com.kekwetors.dao.DaoFactory;

import static com.kekwetors.dao.DaoFactory.*;

public class StartPageServlet extends AbstractBaseServlet {

    public void init() {
        DaoFactory factory = getDaoFactory(H2);
        DaoFactory.loadDriver();
        factory.launchDb(getServletContext().getRealPath(""));
    }
}
