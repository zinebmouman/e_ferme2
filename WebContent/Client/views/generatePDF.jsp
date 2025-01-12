<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Générer le PDF</title>
</head>

<body>
    <h2>Votre PDF est en cours de génération...</h2>

    <%
        // Rediriger vers la servlet pour générer le PDF
        response.sendRedirect(request.getContextPath() + "/generatePDF");
    %>

</body>

</html>
