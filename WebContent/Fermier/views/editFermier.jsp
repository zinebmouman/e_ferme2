<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ajouter un fermier</title>

    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/admin/assets/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/admin/assets/css/sb-admin-2.min.css" rel="stylesheet">

</head>
<body>
     <!-- Page Wrapper -->
    <div id="wrapper">
 
        <!-- End of Sidebar -->
<jsp:include page="sidebar.jsp" />
        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
               <jsp:include page="Topbar.jsp" />
                <!-- End of Topbar -->

             <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                       
                

        <div class="container">
        <h1>Modifier les informations du Fermier</h1>
        
        <form action="fermiers" method="post">
            <!-- ID caché -->
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${fermier.id}">

            <!-- Nom -->
            <div class="form-group">
                <label for="nom">Nom :</label>
                <input type="text" id="nom" name="nom" value="${fermier.nom}" required>
            </div>

            <!-- Email -->
            <div class="form-group">
                <label for="email">Email :</label>
                <input type="email" id="email" name="email" value="${fermier.email}" required>
            </div>

            <!-- Login -->
            <div class="form-group">
                <label for="login">Login :</label>
                <input type="text" id="login" name="login" value="${fermier.login}" required>
            </div>

            <!-- Mot de passe -->
            <div class="form-group">
                <label for="password">Mot de passe :</label>
                <input type="password" id="password" name="password" value="${fermier.password}" required>
            </div>

            <!-- Téléphone -->
            <div class="form-group">
                <label for="telephone">Téléphone :</label>
                <input type="text" id="telephone" name="telephone" value="${fermier.telephone}" required>
            </div>

            <!-- Adresse -->
            <div class="form-group">
                <label for="address">Adresse :</label>
                <textarea id="address" name="address" rows="3" required>${fermier.address}</textarea>
            </div>

            <!-- Type Fermier -->
            <div class="form-group">
                <label for="typeFermies">Type de Fermier :</label>
                <input type="text" id="typeFermies" name="typeFermies" value="${fermier.typeFermies}" required>
            </div>

            <!-- Boutons -->
            <div class="form-actions">
                <button type="submit" class="btn btn-save">Enregistrer</button>
                <a href="fermiers?action=list" class="btn btn-cancel">Annuler</a>
            </div>
        </form>
    </div>
<
                    </div>

                 </div>
                 

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; Your Website 2024</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">Ã—</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="${pageContext.request.contextPath}/admin/assets/vendor/jquery/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/admin/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="${pageContext.request.contextPath}/admin/assets/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="${pageContext.request.contextPath}/admin/assets/js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="${pageContext.request.contextPath}/admin/assets/vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="${pageContext.request.contextPath}/admin/assets/js/demo/chart-area-demo.js"></script>
    <script src="${pageContext.request.contextPath}/admin/assets/js/demo/chart-pie-demo.js"></script>

</body>

</html>
