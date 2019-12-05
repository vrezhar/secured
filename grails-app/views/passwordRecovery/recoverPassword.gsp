<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 04.12.19
  Time: 22:02
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
    <asset:stylesheet src="web_page/authorization/register/errors.css"></asset:stylesheet>
    <title></title>
</head>

<body>
<nav class="navbar navbar-expand-lg fixed-top navbar-custom sticky sticky-dark">
    <div class="container">
    <!-- LOGO -->
        <g:link class="navbar-brand logo" url="#">
            <i class="zmdi zmdi-navigation"></i> <span><g:message code="website.title"></g:message></span>
        </g:link>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <i class="zmdi zmdi-menu"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">


%{--            <ul class="nav navbar-nav navbar-right ml-auto">--}%
%{--                <li class="nav-item">--}%
%{--                    <g:link controller="login" class="nav navbar-nav navbar-right ml-auto">--}%
%{--                        <g:message code="website.login"></g:message>--}%
%{--                    </g:link>--}%
%{--                </li>--}%
%{--                <li class="nav-item">--}%
%{--                    <g:link controller="register" class="btn btn-inverse navbar-btn">--}%
%{--                        <g:message code="website.sign.up"></g:message>--}%
%{--                    </g:link>--}%
%{--                </li>--}%
%{--            </ul>--}%

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
                    <h3 class="text-center"> Reset your password </h3>
                    <g:form role="form" id="recovery_form" class="intro-form" method="POST" url="/password/recover?token=${command?.token?: "null"}">

                        <div class="form-group">
                            <g:passwordField type="password" class="form-control form-control-lg" name="password" id="password" value="${command?.password}" placeholder="Password"></g:passwordField>
                            <label class="error" id="password_errors" style="visibility: visible; display: inline-block">

                            </label>
                        </div>

                        <div class="form-group">
                            <g:passwordField type="password" class="form-control form-control-lg" name="confirm" id="confirm" placeholder="Confirm password" value="${command?.confirm}"></g:passwordField>
                            <label class="error" id="confirm_errors" style="visibility: visible; display: inline-block">

                            </label>
                        </div>


                        <span class="space-lg-only-1"></span>

                        <input type="submit" class="class=btn btn-primary btn-shadow btn-rounded w-lg" style="width: 90%; margin-left: 5%" id="submit" value = "Reset Password"/>

                        <span class="space-md-md"></span>
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
                    <g:link class="navbar-brand logo" url="#">
                        <i class="zmdi zmdi-navigation"></i> <span class="text-uppercase">TTReport</span>
                    </g:link>

%{--                    <ul class="list-inline social-circle">--}%
%{--                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-facebook"></i> </a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-twitter"></i> </a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-google-plus"></i> </a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-apple"></i> </a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> <i class="zmdi zmdi-instagram"></i> </a></li>--}%
%{--                    </ul>--}%

%{--                    <ul class="list-inline menu-list m-t-30">--}%
%{--                        <li class="list-inline-item"><g:link controller="main" action="aboutUs"> About Us</g:link></li>--}%
%{--                        <li class="list-inline-item"><a href=""> Help & Support</a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> Terms & Conditions</a></li>--}%
%{--                        <li class="list-inline-item"><a href=""> Privacy Policy</a></li>--}%
%{--                        <li class="list-inline-item"><g:link controller="main" action="contactUs"> Contact Us</g:link></li>--}%
%{--                    </ul>--}%
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

<asset:javascript src="recovery/async_validate.js"></asset:javascript>
<!-- Back to top -->
<a href="#" class="back-to-top"> <i class="zmdi zmdi-chevron-up"> </i> </a>

</body>
</html>