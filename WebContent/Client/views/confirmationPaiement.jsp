<%@ page contentType="text/html; charset=UTF-8" language="java" import="com.JAVA.Servlet.PDFGenerator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment</title>

    <link rel="stylesheet" type="text/css" href="https://www.paypalobjects.com/webstatic/en_US/developer/docs/css/cardfields.css" />
    <!-- Charger le SDK PayPal -->
    <script src="https://www.paypal.com/sdk/js?client-id=AStxJzbxzdnKTFjUasvDlDKnozV_vLO1xbYZRUMf_h90GuvUjEMqZDvEfezMTwjbOt2jV4NP4VHCdKDg&currency=MAD&components=buttons&enable-funding=venmo"></script>
</head>

<body>
    <jsp:include page="css.jsp" />
    <jsp:include page="maintophomme.jsp" />
    <jsp:include page="headerhomme.jsp" />

    
     
    <script src="https://www.paypal.com/sdk/js?client-id=BAAbf_1JrGCOqRpFhvTXnRZX2lTHqrxPh0my7dVVp-91fAz80Hk5t2Z0RaQ6NWTDjFUCsxp3JvuiqE_n6E&components=hosted-buttons&disable-funding=venmo&currency=USD"></script>
    <div id="paypal-container-AW9YK8VEJLYNC"></div>

    <script>
        paypal.HostedButtons({
            hostedButtonId: "AW9YK8VEJLYNC",
        }).render("#paypal-container-AW9YK8VEJLYNC");
    </script>

    <jsp:include page="footer.jsp" />
    <jsp:include page="js.jsp" />
</body>
</html>
