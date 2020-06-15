package com.google.sps.servlets;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/translate")
public class TranslateServlet extends HttpServlet {
    List<Object> list = new ArrayList<Object>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      // Get the request parameters.
        list = (ArrayList<Object>) request.getAttribute("comment");
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(list));
    }
  }
  