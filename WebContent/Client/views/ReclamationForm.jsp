<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="css.jsp" />
    <jsp:include page="maintophomme.jsp" />
    <jsp:include page="headerhomme.jsp" />

<!-- Start Contact Us -->
<div class="contact-box-main">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-sm-12">
                <div class="contact-form-right">
                    <h2>Ajouter une Réclamation</h2>
                    <p>Veuillez remplir le formulaire ci-dessous pour soumettre votre réclamation.</p>
                    <form action="${pageContext.request.contextPath}/AjouterReclamation" method="POST">
                        <div class="row">
                            <!-- Champ pour l'ID du consommateur (caché si non nécessaire) -->
                            <input type="hidden" name="consommateurId" value="${param.user_id}" />

                            <div class="col-md-12">
                                <div class="form-group">
                                    <textarea class="form-control" name="contenu" placeholder="Votre réclamation" rows="4" required></textarea>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="submit-button text-center">
                                    <button class="btn hvr-hover" type="submit">Soumettre la Réclamation</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-lg-4 col-sm-12">
                <div class="contact-info-left">
                    <h2>CONTACT INFO</h2>
                    <p>Pour toute question ou assistance, n'hésitez pas à nous contacter.</p>
                    <ul>
                        <li>
                            <p><i class="fas fa-map-marker-alt"></i>Adresse : 123 Rue de l'Exemple, Casablanca, Maroc</p>
                        </li>
                        <li>
                            <p><i class="fas fa-phone-square"></i>Téléphone : <a href="tel:+212-123456789">+212 123 456 789</a></p>
                        </li>
                        <li>
                            <p><i class="fas fa-envelope"></i>Email : <a href="mailto:support@exemple.com">support@exemple.com</a></p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Contact Us -->
<jsp:include page="footer.jsp" />
    <jsp:include page="js.jsp" />
</body>
</html>