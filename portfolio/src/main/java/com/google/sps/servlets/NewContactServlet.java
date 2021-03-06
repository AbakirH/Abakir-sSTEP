package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new tasks. */
@WebServlet("/new-contact")
public class NewContactServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    long number = Long.parseLong(request.getParameter("number"));

    Entity contactEntity = new Entity("Contact");
    contactEntity.setProperty("name", name);
    contactEntity.setProperty("email", email);
    contactEntity.setProperty("number", number);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(contactEntity);

    response.sendRedirect("/index.html");
  }
}