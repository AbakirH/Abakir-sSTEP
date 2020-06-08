// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
  private final List<String> comments = new ArrayList<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String json = new Gson().toJson(comments);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String commentText = getComment(request);
    comments.add(commentText);
    response.sendRedirect("/index.html");
  }

  private String getComment(HttpServletRequest request) {
    try{
      if(request.getParameter("comment") == null){
        throw new Exception("Wrong Parameter path"); 
      }
      String comment = request.getParameter("comment");
      return comment;
    }catch(Exception path){
      String comment = "Please check your code, because the path to get the comment is wrong";
      return comment;
    }
    
  }
    
}
