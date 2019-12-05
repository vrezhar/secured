<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 05.12.19
  Time: 15:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
<nav class="navbar navbar-expand-lg fixed-top navbar-custom sticky sticky-dark">
    <div class="container">
    <!-- LOGO -->
        <g:link class="navbar-brand logo" controller="main">
            <i class="zmdi zmdi-navigation"></i> <span><g:message code="website.title"></g:message></span>
        </g:link>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <i class="zmdi zmdi-menu"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">


            <ul class="nav navbar-nav navbar-right ml-auto">
                <li class="nav-item">
                    <g:link controller="login" class="nav navbar-nav navbar-right ml-auto">
                        <g:message code="website.login"></g:message>
                    </g:link>
                </li>
                <li class="nav-item">
                    <g:link controller="register" class="btn btn-inverse navbar-btn">
                        <g:message code="website.sign.up"></g:message>
                    </g:link>
                </li>
            </ul>

        </div>
    </div>
</nav>
<!-- END NAVBAR -->


<!-- HOME -->
<section class="home home-form-left" id="home">
    <!-- <div class="bg-overlay"></div> -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="home-wrapper">
                    <h3 class="text-center"> Forgot your password? </h3>
                    <g:form role="form" id="email_form" class="intro-form" method="POST" url="/recover/email">
                        <span class="space-lg-only-1"></span>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-lg-10">
                                    <g:textField type="email" class="form-control form-control-lg" name="email" id="email" value="${command?.email}" placeholder="Email"></g:textField>
                                    <label class="error" id="username_errors" style="visibility: visible; display: inline-block">

                                    </label>
                                </div>
                                <div class="col-lg-2">
                                    <input type="submit" class="class=btn btn-primary btn-shadow btn-rounded w-lg" style="width: 90%; margin-left: 5%" id="submit" value= <g:message code="website.forgot.password.button"></g:message>>
                                </div>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>

        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
<!-- END HOME -->


<!-- FOOTER -->
<footer class="section bg-gray footer footer-sm">
    <div class="container">


        <div class="row">
            <div class="col-lg-12">
                <div class="text-center">
                    <g:link class="navbar-brand logo" controller="main">
                        <i class="zmdi zmdi-navigation"></i> <span class="text-uppercase">TTReport</span>
                    </g:link>

                    <ul class="list-inline social-circle">
                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-facebook"></i> </a></li>
                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-twitter"></i> </a></li>
                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-google-plus"></i> </a></li>
                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-apple"></i> </a></li>
                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-instagram"></i> </a></li>
                    </ul>

                    <ul class="list-inline menu-list m-t-30">
                        <li class="list-inline-item"><g:link controller="main" action="aboutUs"> About Us</g:link></li>
                        <li class="list-inline-item"><a href=""> Help & Support</a></li>
                        <li class="list-inline-item"><a href=""> Terms & Conditions</a></li>
                        <li class="list-inline-item"><a href=""> Privacy Policy</a></li>
                        <li class="list-inline-item"><g:link controller="main" action="contactUs"> Contact Us</g:link></li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-4">
                <ul class="list-inline menu-list m-t-30">
                    <li class="text-muted m-b-0"><g:message code="website.copyright"></g:message></li>
                </ul>
            </div>

        </div>
        <!-- end row -->

    </div>
</footer>

<!-- END FOOTER -->


<!-- Back to top -->
<a href="#" class="back-to-top"> <i class="zmdi zmdi-chevron-up"> </i> </a>


</body>
</html>