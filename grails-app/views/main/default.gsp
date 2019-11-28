<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 25.11.19
  Time: 21:03
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Welcome!</title>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
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
<section class="home bg-gradient-1" id="home">
    <!-- <div class="bg-overlay"></div> -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">

                <div class="home-wrapper text-center">
                    <h2 class="animated fadeInDown wow text-white" data-wow-delay=".1s">
                        <g:message code="website.title"></g:message>
                        <span class="text-colored">
                            <g:message code="website.home.slogan"></g:message>
                        </span>
                    </h2>
                    <p class="animated fadeInDown wow text-light" data-wow-delay=".2s">
                        <g:message code="website.home.info"></g:message>
                    </p>
                    <sec:ifNotLoggedIn>
                        <g:link controller="register" class="btn btn-primary btn-shadow btn-rounded w-lg animated fadeInDown wow" data-wow-delay=".4s">
                            <g:message code="website.home.get.started"></g:message>
                        </g:link>
                    </sec:ifNotLoggedIn>
                    <div class="clearfix"></div>
                </div><!-- home wrapper -->

            </div> <!-- end col -->
        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
<!-- END HOME -->


<!-- FEATURES -->
<section class="section" id="features">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">
                        <g:message code="website.home.features"></g:message>
                    </p>
                    <div class="border"></div>
                </div>
            </div>
        </div> <!-- end row -->

        <div class="row text-center">
            <div class="col-lg-4">
                <div class="service-item animated fadeInLeft wow" data-wow-delay=".1s">
                    <img src="images/icons/cup.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->

            <div class="col-lg-4">
                <div class="service-item animated fadeInDown wow" data-wow-delay=".3s">
                    <img src="images/icons/diploma.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->

            <div class="col-lg-4">
                <div class="service-item animated fadeInRight wow" data-wow-delay=".5s">
                    <img src="images/icons/combo_chart.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->
        </div> <!--end row -->


        <div class="row text-center">
            <div class="col-lg-4">
                <div class="service-item animated fadeInLeft wow" data-wow-delay=".1s">
                    <img src="images/icons/space-rocket.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->

            <div class="col-lg-4">
                <div class="service-item animated fadeInDown wow" data-wow-delay=".3s">
                    <img src="images/icons/umbrella.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->

            <div class="col-lg-4">
                <div class="service-item animated fadeInRight wow" data-wow-delay=".5s">
                    <img src="images/icons/support.svg" width="48" alt="img">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div> <!-- /service-detail -->
                </div> <!-- /service-item -->
            </div> <!-- /col -->
        </div> <!--end row -->


    </div> <!-- end container -->
</section>
<!-- END FEATURES -->


<!-- PRICING -->
<section class="section" id="pricing">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt fadeIn animated wow" data-wow-delay=".1s">Pricing</p>
                    <h3 class="fadeIn animated wow" data-wow-delay=".2s">Select a Plan</h3>
                    <div class="border"></div>
                </div>
            </div>
        </div> <!-- end row -->

        <div class="row">

            <!--Pricing Column-->
            <article class="col-lg-3 pricing-column">
                <div class="inner-box fadeIn animated wow" data-wow-delay=".1s">
                    <div class="plan-header text-center">
                        <h3 class="plan-title">Basic</h3>
                        <h2 class="plan-price">$19</h2>
                        <div class="plan-duration">Per Month</div>
                    </div>
                    <ul class="plan-stats list-unstyled text-center">
                        <li>5 Projects</li>
                        <li>1 GB Storage</li>
                        <li>No Domain</li>
                        <li>1 User</li>
                        <li>24x7 Support</li>
                    </ul>

                    <div class="text-center">
                        <a href="#" class="btn btn-primary btn-shadow w-md btn-rounded">Buy Now</a>
                    </div>
                </div>
            </article>

            <!--Pricing Column-->
            <article class="col-lg-3 pricing-column">
                <div class="inner-box fadeIn animated wow" data-wow-delay=".2s">
                    <div class="plan-header text-center">
                        <h3 class="plan-title">Premium</h3>
                        <h2 class="plan-price">$29</h2>
                        <div class="plan-duration">Per Month</div>
                    </div>
                    <ul class="plan-stats list-unstyled text-center">
                        <li>5 Projects</li>
                        <li>1 GB Storage</li>
                        <li>No Domain</li>
                        <li>1 User</li>
                        <li>24x7 Support</li>
                    </ul>

                    <div class="text-center">
                        <a href="#" class="btn btn-primary btn-shadow w-md btn-rounded">Buy Now</a>
                    </div>
                </div>
            </article>

            <!--Pricing Column-->
            <article class="col-lg-3 pricing-column">
                <div class="inner-box fadeIn animated wow" data-wow-delay=".3s">
                    <div class="plan-header text-center">
                        <h3 class="plan-title">Developer</h3>
                        <h2 class="plan-price">$39</h2>
                        <div class="plan-duration">Per Month</div>
                    </div>
                    <ul class="plan-stats list-unstyled text-center">
                        <li>5 Projects</li>
                        <li>1 GB Storage</li>
                        <li>No Domain</li>
                        <li>1 User</li>
                        <li>24x7 Support</li>
                    </ul>

                    <div class="text-center">
                        <a href="#" class="btn btn-primary btn-shadow w-md btn-rounded">Buy Now</a>
                    </div>
                </div>
            </article>

            <!--Pricing Column-->
            <article class="col-lg-3 pricing-column">
                <div class="inner-box fadeIn animated wow" data-wow-delay=".4s">
                    <div class="plan-header text-center">
                        <h3 class="plan-title">Business</h3>
                        <h2 class="plan-price">$49</h2>
                        <div class="plan-duration">Per Month</div>
                    </div>
                    <ul class="plan-stats list-unstyled text-center">
                        <li>5 Projects</li>
                        <li>1 GB Storage</li>
                        <li>No Domain</li>
                        <li>1 User</li>
                        <li>24x7 Support</li>
                    </ul>

                    <div class="text-center">
                        <a href="#" class="btn btn-primary btn-shadow w-md btn-rounded">Buy Now</a>
                    </div>
                </div>
            </article>

        </div><!-- end row -->

    </div> <!-- end container -->
</section>
<!-- END PRICING -->

%{--<!-- CLIENTS -->--}%
%{--<section class="bg-gradient-1">--}%
%{--    <div class="container clients section">--}%
%{--        <div class="row">--}%
%{--            <div class="col-lg-12 text-center">--}%
%{--                <div class="title-box">--}%
%{--                    <p class="title-alt">Testimonials</p>--}%
%{--                    <h3 class="fadeIn animated wow text-white" data-wow-delay=".1s">Happy Clients</h3>--}%
%{--                    <div class="border"></div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div> <!-- end row -->--}%

%{--        <div class="row text-center">--}%
%{--            <div class="col-lg-12">--}%
%{--                <div class="">--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".1s">--}%
%{--                        <a href="#"><img alt="01 brand" src="images/clients/01_brand.png"></a>--}%
%{--                    </div>--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".2s">--}%
%{--                        <a href="#"><img alt="02 brand" src="images/clients/02_brand.png"></a>--}%
%{--                    </div>--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".3s">--}%
%{--                        <a href="#"><img alt="03 brand" src="images/clients/03_brand.png"></a>--}%
%{--                    </div>--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".4s">--}%
%{--                        <a href="#"><img alt="04 brand" src="images/clients/04_brand.png"></a>--}%
%{--                    </div>--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".5s">--}%
%{--                        <a href="#"><img alt="05 brand" src="images/clients/05_brand.png"></a>--}%
%{--                    </div>--}%
%{--                    <div class="brand-item animated fadeInLeft wow" data-wow-delay=".6s">--}%
%{--                        <a href="#"><img alt="06 brand" src="images/clients/06_brand.png"></a>--}%
%{--                    </div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div>--}%
%{--    </div>--}%
%{--</section>--}%
%{--<!-- END CLIENTS -->--}%

%{--<!-- TEAM -->--}%
%{--<section class="section bg-gray" id="team">--}%
%{--    <div class="container">--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-12 text-center">--}%
%{--                <div class="title-box">--}%
%{--                    <p class="title-alt">Team</p>--}%
%{--                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">Creative People</h3>--}%
%{--                    <div class="border"></div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </div> <!-- end row -->--}%

%{--        <div class="row">--}%
%{--            <div class="col-lg-10 offset-lg-1">--}%
%{--                <div class="row team text-center">--}%
%{--                    <!-- team-member -->--}%
%{--                    <div class="col-lg-4">--}%
%{--                        <div class="team-member animated fadeInDown wow" data-wow-delay=".1s">--}%
%{--                            <img src="images/team/team-1.jpg" alt="team-member" class="img-fluid rounded-circle">--}%
%{--                            <h4>Holden McGroin</h4>--}%
%{--                            <p class="text-muted">Product Designer & Founder</p>--}%
%{--                        </div>--}%
%{--                    </div>--}%

%{--                    <!-- team-member -->--}%
%{--                    <div class="col-lg-4">--}%
%{--                        <div class="team-member animated fadeInDown wow" data-wow-delay=".3s">--}%
%{--                            <img src="images/team/team-2.jpg" alt="team-member" class="img-fluid rounded-circle">--}%
%{--                            <h4>Mike Oxbigg</h4>--}%
%{--                            <p class="text-muted">Developer & Co-founder</p>--}%
%{--                        </div>--}%
%{--                    </div>--}%

%{--                    <!-- team-member -->--}%
%{--                    <div class="col-lg-4">--}%
%{--                        <div class="team-member animated fadeInDown wow" data-wow-delay=".5s">--}%
%{--                            <img src="images/team/team-3.jpg" alt="team-member" class="img-fluid rounded-circle">--}%
%{--                            <h4>Eilean Dover</h4>--}%
%{--                            <p class="text-muted">UI/UX Designer</p>--}%
%{--                        </div>--}%
%{--                    </div>--}%

%{--                </div>--}%
%{--            </div>--}%
%{--        </div>--}%

%{--    </div>--}%
%{--</section>--}%
%{--<!-- END TEAM -->--}%

<!-- FAQ -->
<section class="section" id="faq">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">Faq</p>
                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">
                        <g:message code="website.home.faq"></g:message>
                    </h3>
                    <div class="border"></div>
                </div>
            </div>
        </div> <!-- end row -->

        <div class="row m-t-30">
            <div class="col-lg-5 offset-lg-1">
                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInLeft wow" data-wow-delay=".1s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>

                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInLeft wow" data-wow-delay=".3s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>

                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInLeft wow" data-wow-delay=".5s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>
            </div>
            <!--/col-lg-5 -->

            <div class="col-lg-5">
                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInRight wow" data-wow-delay=".2s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>

                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInRight wow" data-wow-delay=".4s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>

                <!-- Question/Answer -->
                <div class="question-q-box">Q.</div>
                <div class="animated fadeInRight wow" data-wow-delay=".6s">
                    <h4 class="question" data-wow-delay=".1s">
                        <g:message code="website.home.faq.dummy.name"></g:message>
                    </h4>
                    <p class="answer">
                        <g:message code="website.home.faq.dummy.question"></g:message>
                    </p>
                </div>

            </div>
            <!--/col-lg-5-->
        </div>

    </div>
</section>
<!-- END FAQ -->


<!-- FOOTER -->
<footer class="section bg-gray footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-3">
                <h5>
                    <g:message code="website.title"></g:message>
                </h5>
                <ul class="list-unstyled">
                    <li>
                        <a href="">
                            <g:message code="website.footer.about.us"></g:message>
                        </a>
                    </li>
                </ul>
            </div>

            <div class="col-lg-3">
                <h5>
                    <g:message code="website.footer.social"></g:message>
                </h5>
                <ul class="list-unstyled">
                    <li><a href="">Facebook</a></li>
                    <li><a href="">Twitter</a></li>
                    <li><a href="">Vk</a></li>
                </ul>
            </div>

            <div class="col-lg-3">
                <h5>Support</h5>
                <ul class="list-unstyled">
                    <li><a href="">Help & Support</a></li>
                    <li><a href="">Privacy Policy</a></li>
                    <li><a href="">Terms & Conditions</a></li>
                </ul>
            </div>

            <div class="col-lg-3">
                <h5>
                    <g:message code="website.footer.contact.us"></g:message>
                </h5>
                <address style="color: #000000">
                    795 Folsom Ave, Suite 600<br>
                    San Francisco, CA 94107<br>
                    <abbr title="Phone">Phone:</abbr> <g:message code="website.footer.contact.number"></g:message> <br/>
                    Email: <a href="mailto: <g:message code="website.footer.contact.email"></g:message> "> <g:message code="website.footer.contact.email"></g:message> </a>
                </address>
            </div>

        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="footer-alt">
                    <div class="float-left pull-none ">
                        <g:link class="navbar-brand logo" controller="main">
                            <i class="zmdi zmdi-navigation"></i>
                            <span>
                                <g:message code="website.title"></g:message>
                            </span>
                        </g:link>

                    </div>
                    <div class="float-right pull-none ">
                        <p class="pull-right text-muted m-b-0">
                            <g:message code="website.copyright"></g:message>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- END FOOTER -->


<!-- Back to top -->
<a href="#" class="back-to-top"> <i class="zmdi zmdi-chevron-up"> </i> </a>
</body>
</html>