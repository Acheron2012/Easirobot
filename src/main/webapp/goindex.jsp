<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<%--<jsp:redirect page="/web/statistics.do" />--%>
<% response.sendRedirect(path+"/web/statistics.do"); %>