<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 25.11.19
  Time: 22:15
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>

    <title><g:message code='springSecurity.login.title'/></title>

    <asset:stylesheet src="web_page/authorization/login/inputs.css"></asset:stylesheet>
    <asset:stylesheet src="web_page/authorization/login/errors.css"></asset:stylesheet>

</head>

<body data-spy="scroll" data-target="#navbar-menu">
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
                    <h3 class="text-center"> Log In To Your Account</h3>

                    <form role="form" id="register_form" class="intro-form" method="POST" action="/login/authenticate" >

                        <span class="space-lg-only-1"></span>

                        <g:if test='${flash.message}'>
                            <div class="login_message" id="login_message" style="text-align: center">${flash.message}</div>
                        </g:if>

                        <div class="row ">
                            <div class="col-12">
                                <div class="form-group">
                                    %{--                                                <label>Email</label>--}%
                                    <input type="email" class="form-control form-control-lg" name="${usernameParameter ?: 'username'}" id="username" placeholder="Username"/>
                                </div>
                            </div>
                        </div>

                        <span class="space-lg-only-1"></span>

                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group ">
                                    %{--                                                <label>Password</label>--}%
                                    <input type="password" class="form-control form-control-lg" name="${passwordParameter ?: 'password'}" id="password" placeholder="Password"/>
                                </div>
                            </div>
                        </div>

                        <span class="space-lg-only-1"></span>

                        <div class="row">
                            <div class="col-lg-12">
                                <div class="checkbox">
                                    <input type="checkbox" id="chkRemember" <g:if test='${hasCookie}'>checked="checked"</g:if>>
                                    <label for="chkRemember">Remember me</label>
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="class=btn btn-primary btn-shadow btn-rounded w-lg" style="width: 90%; margin-left: 5%" id="submit" value= <g:message code="website.register.form.submit"></g:message>>Sign in</button>
                    </form>
%{--                    <g:form role="form" id="register_form" class="intro-form" method="POST" url="/register/confirm">--}%

%{--                        <div class="form-group">--}%
%{--                            <g:textField name="firstName" class="form-control form-control-lg" id="firstName" placeholder="First name" value="${user?.firstName}"></g:textField>--}%
%{--                            <label class="error"  id="firstName_errors" style="visibility: visible; display: inline-block">--}%
%{--                            </label>--}%
%{--                        </div>--}%

%{--                        <div class="form-group">--}%
%{--                            <g:textField type="text" class="form-control form-control-lg" name="lastName" id="lastName" value="${user?.lastName}" placeholder="Last name"></g:textField>--}%
%{--                            <label class="error" id="lastName_errors" style="visibility: visible; display: inline-block">--}%

%{--                            </label>--}%
%{--                        </div>--}%

%{--                        <span class="space-lg-only-1"></span>--}%

%{--                        <div class="form-group">--}%
%{--                            <g:textField type="email" class="form-control form-control-lg" name="username" id="username" value="${user?.username}" placeholder="Email"></g:textField>--}%
%{--                            <label class="error" id="username_errors" style="visibility: visible; display: inline-block">--}%

%{--                            </label>--}%
%{--                        </div>--}%


%{--                        <span class="space-lg-only-1"></span>--}%


%{--                        <div class="form-group">--}%
%{--                            <g:passwordField type="password" class="form-control form-control-lg" name="password" id="password" value="${user?.password}" placeholder="Password"></g:passwordField>--}%
%{--                            <label class="error" id="password_errors" style="visibility: visible; display: inline-block">--}%

%{--                            </label>--}%
%{--                        </div>--}%

%{--                        <div class="form-group">--}%
%{--                            <g:passwordField type="password" class="form-control form-control-lg" name="confirm" id="confirm" placeholder="Confirm password" value="${user?.confirm}"></g:passwordField>--}%
%{--                            <label class="error" id="confirm_errors" style="visibility: visible; display: inline-block">--}%

%{--                            </label>--}%
%{--                        </div>--}%


%{--                        <span class="space-lg-only-1"></span>--}%

%{--                        <input type="submit" class="class=btn btn-primary btn-shadow btn-rounded w-lg" style="width: 90%; margin-left: 5%" id="submit" value= <g:message code="website.register.form.submit"></g:message>/>--}%

%{--                        <span class="space-md-md"></span>--}%
%{--                    </g:form>--}%
                </div>
            </div>

        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
<!-- END HOME -->



%{--<!-- FEATURES -->--}%
%{--<section class="section" id="features">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-12 text-center">--}%
%{--                <div class="title-box">--}%
%{--                    <p class="title-alt">01.</p>--}%
%{--                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">How It Works ?</h3>--}%
%{--                    <div class="border"></div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div> <!-- end row -->--}%

%{--        <div class="row text-center">--}%
%{--            <div class="col-lg-4">--}%
%{--                <div class="service-item animated fadeInLeft wow" data-wow-delay=".1s">--}%
%{--                    <h2 class="mb-4"><i class="zmdi zmdi-collection-item-1 text-colored"></i></h2>--}%
%{--                    <div class="service-detail">--}%
%{--                        <h4 class="mb-3">Strategy Solutions</h4>--}%
%{--                        <p>We put a lot of effort in design, as it’s the most important ingredient of successful website.Sed ut perspiciatis unde omnis iste natus error sit.</p>--}%
%{--                    </div> <!-- /service-detail -->--}%
%{--                </div> <!-- /service-item -->--}%
%{--            </div> <!-- /col -->--}%

%{--            <div class="col-lg-4">--}%
%{--                <div class="service-item animated fadeInDown wow" data-wow-delay=".3s">--}%
%{--                    <h2 class="mb-4"><i class="zmdi zmdi-collection-item-2 text-colored"></i></h2>--}%
%{--                    <div class="service-detail">--}%
%{--                        <h4 class="mb-3">Digital Design</h4>--}%
%{--                        <p>We put a lot of effort in design, as it’s the most important ingredient of successful website.Sed ut perspiciatis unde omnis iste.</p>--}%
%{--                    </div> <!-- /service-detail -->--}%
%{--                </div> <!-- /service-item -->--}%
%{--            </div> <!-- /col -->--}%

%{--            <div class="col-lg-4">--}%
%{--                <div class="service-item animated fadeInRight wow" data-wow-delay=".5s">--}%
%{--                    <h2 class="mb-4"><i class="zmdi zmdi-collection-item-3 text-colored"></i></h2>--}%
%{--                    <div class="service-detail">--}%
%{--                        <h4 class="mb-3">SEO</h4>--}%
%{--                        <p>We put a lot of effort in design, as it’s the most important ingredient of successful website.Sed ut perspiciatis unde omnis iste.</p>--}%
%{--                    </div> <!-- /service-detail -->--}%
%{--                </div> <!-- /service-item -->--}%
%{--            </div> <!-- /col -->--}%
%{--        </div> <!--end row -->--}%


%{--    </div> <!-- end container -->--}%
%{--</section>--}%
%{--<!-- END FEATURES -->--}%


%{--<!-- FEATURES-ALT -->--}%
%{--<section class="section bg-gray" id="features">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-6">--}%
%{--                <div class="feature-detail">--}%

%{--                    <div class="title-box">--}%
%{--                        <p class="title-alt">02.</p>--}%
%{--                        <h3 class="fadeIn animated wow" data-wow-delay=".1s">Fully Responsive Design</h3>--}%
%{--                        <div class="border"></div>--}%
%{--                    </div>--}%

%{--                    <p class="text-muted">Proin a velit aliquam vitae malesuada rutrum. Aenean ullamcorper placerat porttitor velit aliquam vitae. Aliquam a augue suscipit, bibendum luctus neque laoreet rhoncus ipsum, ullamcorper.</p>--}%

%{--                    <ul class="zmdi-hc-ul">--}%
%{--                        <li class="fadeIn animated wow" data-wow-delay=".1s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Fully Responsive design.</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".2s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Based on Bootstrap 4.3.1</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".3s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Many variants to choose from</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".4s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Built with HTML5 & CSS3</span></li>--}%

%{--                    </ul>--}%

%{--                </div>--}%
%{--            </div>--}%

%{--            <div class="col-lg-6">--}%
%{--                <img src="images/macbook.png" class="img-fluid fadeIn animated wow" data-wow-delay=".2s">--}%
%{--            </div>--}%

%{--        </div>--}%
%{--    </div>--}%
%{--</section>--}%
%{--<!-- END FEATURES-ALT -->--}%


%{--<!-- FEATURES-ALT -->--}%
%{--<section class="section" id="features">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-6">--}%
%{--                <img src="images/tablet.png" class="img-fluid fadeIn animated wow center-block" data-wow-delay=".2s">--}%
%{--            </div>--}%

%{--            <div class="col-lg-6">--}%
%{--                <div class="feature-detail">--}%

%{--                    <div class="title-box">--}%
%{--                        <p class="title-alt">03.</p>--}%
%{--                        <h3 class="fadeIn animated wow" data-wow-delay=".1s">Clean and Ultra Modern Design</h3>--}%
%{--                        <div class="border"></div>--}%
%{--                    </div>--}%

%{--                    <p class="text-muted">Proin a velit aliquam vitae malesuada rutrum. Aenean ullamcorper placerat porttitor velit aliquam vitae. Aliquam a augue suscipit, bibendum luctus neque laoreet rhoncus ipsum, ullamcorper.</p>--}%

%{--                    <p class="text-muted">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sint nulla facilis voluptatem delectus alias officia iure.</p>--}%

%{--                    <ul class="zmdi-hc-ul">--}%
%{--                        <li class="fadeIn animated wow" data-wow-delay=".1s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Fully Responsive design.</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".2s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Based on Bootstrap 4.3.1</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".3s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Many variants to choose from</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".4s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Built with HTML5 & CSS3</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".5s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Commented and clean code</span></li>--}%

%{--                    </ul>--}%

%{--                    <a href="" class="btn btn-primary btn-shadow btn-rounded w-lg animated fadeInDown wow" data-wow-delay=".4s">Get Started</a>--}%
%{--                </div>--}%
%{--            </div>--}%

%{--        </div>--}%
%{--    </div>--}%
%{--</section>--}%
%{--<!-- END FEATURES-ALT -->--}%


%{--<!-- FEATURES-ALT -->--}%
%{--<section class="section bg-gray" id="features">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-6">--}%
%{--                <div class="feature-detail">--}%

%{--                    <div class="title-box">--}%
%{--                        <p class="title-alt">04.</p>--}%
%{--                        <h3 class="fadeIn animated wow" data-wow-delay=".1s">Clean and Ultra Modern Design</h3>--}%
%{--                        <div class="border"></div>--}%
%{--                    </div>--}%

%{--                    <ul class="zmdi-hc-ul">--}%
%{--                        <li class="fadeIn animated wow" data-wow-delay=".1s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Fully Responsive design.</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".2s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Based on Bootstrap 4.3.1</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".3s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Many variants to choose from</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".4s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Built with HTML5 & CSS3</span></li>--}%

%{--                        <li class="fadeIn animated wow" data-wow-delay=".5s"><i class="zmdi-hc-li zmdi zmdi-caret-right-circle text-colored"></i><span class="text-muted">Commented and clean code</span></li>--}%

%{--                    </ul>--}%

%{--                    <a href="" class="btn btn-primary btn-shadow btn-rounded w-lg animated fadeInDown wow" data-wow-delay=".4s">Get Started</a>--}%
%{--                </div>--}%
%{--            </div>--}%

%{--            <div class="col-lg-6">--}%
%{--                <img src="images/macbook.png" class="img-fluid fadeIn animated wow" data-wow-delay=".2s">--}%
%{--            </div>--}%

%{--        </div>--}%
%{--    </div>--}%
%{--</section>--}%
%{--<!-- END FEATURES-ALT -->--}%





%{--<!-- FAQ -->--}%
%{--<section class="section" id="faq">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-12 text-center">--}%
%{--                <div class="title-box">--}%
%{--                    <p class="title-alt">Faq</p>--}%
%{--                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">Simple Questions</h3>--}%
%{--                    <div class="border"></div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div> <!-- end row -->--}%

%{--        <div class="row m-t-30">--}%
%{--            <div class="col-lg-5 offset-lg-1">--}%
%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInLeft wow" data-wow-delay=".1s">--}%
%{--                    <h4 class="question" data-wow-delay=".1s">What is Lorem Ipsum?</h4>--}%
%{--                    <p class="answer">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>--}%
%{--                </div>--}%

%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInLeft wow" data-wow-delay=".3s">--}%
%{--                    <h4 class="question">Why use Lorem Ipsum?</h4>--}%
%{--                    <p class="answer">Lorem ipsum dolor sit amet, in mea nonumes dissentias dissentiunt, pro te solet oratio iriure. Cu sit consetetur moderatius intellegam, ius decore accusamus te. Ne primis suavitate disputando nam. Mutat convenirete.</p>--}%
%{--                </div>--}%

%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInLeft wow" data-wow-delay=".5s">--}%
%{--                    <h4 class="question">How many variations exist?</h4>--}%
%{--                    <p class="answer">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>--}%
%{--                </div>--}%

%{--            </div>--}%
%{--            <!--/col-md-5 -->--}%

%{--            <div class="col-lg-5">--}%
%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInRight wow" data-wow-delay=".2s">--}%
%{--                    <h4 class="question">Is safe use Lorem Ipsum?</h4>--}%
%{--                    <p class="answer">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>--}%
%{--                </div>--}%

%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInRight wow" data-wow-delay=".4s">--}%
%{--                    <h4 class="question">When can be used?</h4>--}%
%{--                    <p class="answer">Lorem ipsum dolor sit amet, in mea nonumes dissentias dissentiunt, pro te solet oratio iriure. Cu sit consetetur moderatius intellegam, ius decore accusamus te. Ne primis suavitate disputando nam. Mutat convenirete.</p>--}%
%{--                </div>--}%

%{--                <!-- Question/Answer -->--}%
%{--                <div class="question-q-box">Q.</div>--}%
%{--                <div class="animated fadeInRight wow" data-wow-delay=".6s">--}%
%{--                    <h4 class="question">License &amp; Copyright</h4>--}%
%{--                    <p class="answer">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>--}%
%{--                </div>--}%

%{--            </div>--}%
%{--            <!--/col-md-5-->--}%
%{--        </div>--}%

%{--    </div>--}%
%{--</section>--}%
%{--<!-- END FAQ -->--}%


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
                        <li class="list-inline-item"><a href=""> About Us</a></li>
                        <li class="list-inline-item"><a href=""> Help & Support</a></li>
                        <li class="list-inline-item"><a href=""> Terms & Conditions</a></li>
                        <li class="list-inline-item"><a href=""> Privacy Policy</a></li>

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