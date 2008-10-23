<%-- 
    Document   : index
    Created on : Oct 10, 2008, 4:03:47 PM
    Author     : p62287946
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-cache, must-revalidate");
response.setHeader("Expires", "Sat, 26 Jul 1997 05:00:00 GMT"); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<% 
pageContext.forward("oddoneout");
//response.addHeader("Location:", arg1)
%>
