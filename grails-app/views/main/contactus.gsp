<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 28.11.19
  Time: 23:47
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>


<body data-spy="scroll" data-target="#navbar-menu">
<!-- STRAT NAVBAR -->

<nav class="navbar navbar-expand-lg fixed-top navbar-custom sticky sticky-dark">
    <div class="container">
    <!-- LOGO -->
        <g:link class="navbar-brand logo" controller="main">
            <i class="zmdi zmdi-navigation"></i>
            <span>
                <g:message code="website.title"></g:message>
            </span>
        </g:link>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <i class="zmdi zmdi-menu"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav navbar-center nav-custom-left" id="mySidenav">
                <li class="nav-item dropdown">
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right ml-auto">
                <sec:ifNotLoggedIn>
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
                </sec:ifNotLoggedIn>
                <sec:ifLoggedIn>
                    <li class="nav-item">
                        <g:link controller="user" action="profile" class="nav navbar-nav navbar-right ml-auto">
                            <g:message code="website.home"></g:message>
                        </g:link>
                    </li>
                </sec:ifLoggedIn>
            </ul>

        </div>
    </div>
</nav>
<!-- END NAVBAR -->




<!-- HOME -->
<section class="home">
    <!-- <div class="bg-overlay"></div> -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">

                <div class="home-wrapper home-wrapper-sm text-center">
                    <h2 class="animated fadeInDown wow" data-wow-delay=".1s">
                        Contact <span class="text-colored">Us</span>
                    </h2>
                </div><!-- home wrapper -->

            </div> <!-- end col -->
        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
<!-- END HOME -->






<!-- CONTACT -->

<section class="section contact-page">

    <div class="container">

        <div class="row text-center">

            <div class="col-lg-4">
                <div class="contact-detail-box">
                    <div class="mb-3">
                        <i class="zmdi zmdi-layers zmdi-hc-3x text-colored"></i>
                    </div>

                    <h4>Get In Touch</h4>

                    <abbr title="Phone">P:</abbr> (123) 456-7890<br>
                    E: <a href="mailto:email@email.com" class="text-muted">email@email.com</a>
                </div>
            </div><!-- end col -->

            <div class="col-lg-4">
                <div class="contact-detail-box">
                    <div class="mb-3">
                        <i class="zmdi zmdi-pin zmdi-hc-3x"></i>
                    </div>
                    <h4>Our Location</h4>

                    <address>
                        795 Folsom Ave, Suite 600<br>
                        San Francisco, CA 94107<br>
                    </address>
                </div>
            </div><!-- end col -->

            <div class="col-lg-4">
                <div class="contact-detail-box">
                    <div class="mb-3">
                        <i class="zmdi zmdi-headset-mic zmdi-hc-3x text-colored"></i>
                    </div>

                    <h4>24x7 Support</h4>

                    <p class="mb-2">Industry's standard dummy text.</p>
                    <h5 class="text-muted">1234 567 890</h5>
                </div>
            </div><!-- end col -->

        </div>
        <!-- end row -->


        <div class="row">
            <div class="col-lg-6">
                <div class="contact-map">
                    <iframe src="https://www.google.com/maps/embed/v1/place?q=New+York+University&key=AIzaSyBSFRN6WWGYwmFi498qXXsD2UwkbmD74v4" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" style="width: 100%; height: 360px;"></iframe>
                </div>
            </div><!-- end col -->

        <!-- Contact form -->
            <div class="col-lg-6">
                <form role="form" name="ajax-form" id="ajax-form" action="https://formsubmit.io/send/coderthemes@gmail.com" method="post" class="form-main">

                    <div class="form-group">
                        <label for="name2" class="font-weight-bold">Name</label>
                        <input class="form-control" id="name2" name="name" onblur="if(this.value == '') this.value='Name'" onfocus="if(this.value == 'Name') this.value=''" type="text" value="Name">
                        <div class="error" id="err-name" style="display: none;">Please enter name</div>
                    </div> <!-- /Form-name -->

                    <div class="form-group">
                        <label for="email2" class="font-weight-bold">Email</label>
                        <input class="form-control" id="email2" name="email" type="text" onfocus="if(this.value == 'E-mail') this.value='';" onblur="if(this.value == '') this.value='E-mail';" value="E-mail">
                        <div class="error" id="err-emailvld" style="display: none;">E-mail is not a valid format</div>
                    </div> <!-- /Form-email -->

                    <div class="form-group">
                        <label for="message2" class="font-weight-bold">Message</label>
                        <textarea class="form-control" id="message2" name="message" rows="4" onblur="if(this.value == '') this.value='Message'" onfocus="if(this.value == 'Message') this.value=''">Message</textarea>

                        <div class="error" id="err-message" style="display: none;">Please enter message</div>
                    </div> <!-- /col -->

                    <div class="row">
                        <div class="col-lg-12">
                            <div id="ajaxsuccess" class="text-success">E-mail was successfully sent.</div>
                            <div class="error" id="err-form" style="display: none;">There was a problem validating the form please check!</div>
                            <div class="error" id="err-timedout">The connection to the server timed out!</div>
                            <div class="error" id="err-state"></div>
                            <button type="submit" class="btn btn-primary btn-shadow btn-rounded w-md" id="send">Submit</button>
                        </div> <!-- /col -->
                    </div> <!-- /row -->

                </form> <!-- /form -->
            </div> <!-- end col -->

        </div> <!-- end row -->

    </div> <!-- end Container -->
</section>

<!--END Contact -->



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
%{--<a href="#" class="back-to-top"> <i class="zmdi zmdi-chevron-up"> </i> </a>--}%


</body>
</html>