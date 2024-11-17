<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <!-- Fonts and styles -->
  <link rel="shortcut icon" href="Client/images/favicon.ico" type="image/x-icon">
  <link rel="apple-touch-icon" href="Client/images/apple-touch-icon.png">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="Client/css/bootstrap.min.css">
  <!-- Site CSS -->
  <link rel="stylesheet" href="Client/css/style.css">
  <!-- Responsive CSS -->
  <link rel="stylesheet" href="Client/css/responsive.css">
  <!-- Custom CSS -->
  <link rel="stylesheet" href="Client/css/custom.css">
</head>

<body>
  <div class="min-vh-100 d-flex align-items-center justify-content-center" style="background-image: url('Client/images/gallery-img-09.jpg'); background-size: cover;">
    
    <div class="container position-relative z-index-1">
      <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8 col-sm-10">
          <div class="auth-form-box p-4 bg-white rounded shadow">
            <h2 class="text-center mb-4">Login</h2>
            <form action="login" method="post">
              <div class="form-group mb-3">
                <label for="username">Username</label>
                <input type="email" class="form-control" id="username" name="email" required>
              </div>
              <div class="form-group mb-3">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
              </div>
              <div class="text-center">
                <button type="submit" class="btn hvr-hover btn-primary w-100">Login</button>
              </div>
              <p class="text-center mt-3">Don't have an account? <a href="register.jsp">Sign up</a></p>
            </form>
          </div>
        </div>
      </div>
    </div>
    <footer class="footer position-absolute bottom-2 py-2 w-100">
      <div class="container">
        <div class="row align-items-center justify-content-lg-between">
          <div class="col-12 col-md-6 my-auto">
            <div class="copyright text-center text-sm text-white text-lg-start">
              © <script>document.write(new Date().getFullYear())</script>, made with <i class="fa fa-heart"></i> by Creative Tim
            </div>
          </div>
        </div>
      </div>
    </footer>
  </div>

  <!-- Core JS Files -->
  <script src="Client/js/jquery-3.2.1.min.js"></script>
  <script src="Client/js/popper.min.js"></script>
  <script src="Client/js/bootstrap.min.js"></script>
  <!-- ALL PLUGINS -->
  <script src="Client/js/jquery.superslides.min.js"></script>
  <script src="Client/js/bootstrap-select.js"></script>
  <script src="Client/js/inewsticker.js"></script>
  <script src="Client/js/bootsnav.js."></script>
  <script src="Client/js/images-loded.min.js"></script>
  <script src="Client/js/isotope.min.js"></script>
  <script src="Client/js/owl.carousel.min.js"></script>
  <script src="Client/js/baguetteBox.min.js"></script>
  <script src="Client/js/form-validator.min.js"></script>
  <script src="Client/js/contact-form-script.js"></script>
  <script src="Client/js/custom.js"></script>
</body>

</html>
