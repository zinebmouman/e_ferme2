<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
hello fermier

<% String userId = request.getParameter("user_id"); %>
<a href="AjouterProduit.jsp?user_id=<%= userId %>">Ajouter produit</a>
c'est le fermier
</body>
</html>