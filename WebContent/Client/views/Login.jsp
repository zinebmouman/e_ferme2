<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <!-- Fonts and styles -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/Client/images/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/Client/images/apple-touch-icon.png">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Client/css/bootstrap.min.css">
  <!-- Site CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Client/css/style.css">
  <!-- Responsive CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Client/css/responsive.css">
  <!-- Custom CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Client/css/custom.css">
</head>

<body>
  <!-- Start Login Form -->
  <div class="contact-box-main">
    <div class="container">
    <div style="display: flex; justify-content: center;">
     <a class="navbar-brand"  href="index.html"><img style="display: flex; justify-content: center;" src="${pageContext.request.contextPath}/Client/images/logo.png" class="logo" alt=""></a>
      </div>
      <div class="row justify-content-center">
        <div class="col-lg-6 col-sm-12">
          <div class="contact-form-right">
            <h2>Sign in</h2>
            <p>Please enter your credentials to Sign in.</p>
            <form action="${pageContext.request.contextPath}/login" method="post">
              <div class="form-group">
                <input type="email" class="form-control" id="username" name="email" placeholder="Email" required data-error="Please enter your email">
                <div class="help-block with-errors"></div>
              </div>
              <div class="form-group">
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required data-error="Please enter your password">
                <div class="help-block with-errors"></div>
              </div>
              <div class="submit-button text-center">
                <button class="btn hvr-hover" type="submit">Sign in</button>
                <div class="clearfix"></div>
              </div>
              <p class="text-center mt-3">Don't have an account? <a href="${pageContext.request.contextPath}/Client/views/Inscrire.jsp">Sign up</a></p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- End Login Form -->

  <!-- Core JS Files -->
  <script src="${pageContext.request.contextPath}/Client/js/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/popper.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/bootstrap.min.js"></script>
  <!-- ALL PLUGINS -->
  <script src="${pageContext.request.contextPath}/Client/js/jquery.superslides.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/bootstrap-select.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/inewsticker.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/bootsnav.js."></script>
  <script src="${pageContext.request.contextPath}/Client/js/images-loded.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/isotope.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/owl.carousel.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/baguetteBox.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/form-validator.min.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/contact-form-script.js"></script>
  <script src="${pageContext.request.contextPath}/Client/js/custom.js"></script>
</body>

</html>
