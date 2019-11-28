<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Coderthemes">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>

    <!-- Favicon icon -->
    <link rel="shortcut icon" href="images/favicon.ico">

    <!-- Site Title -->
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
                        About <span class="text-colored">Us</span>
                    </h2>
                </div>
                <!-- home wrapper -->

            </div>
            <!-- end col -->
        </div>
        <!-- end row -->
    </div>
    <!-- end container -->
</section>
<!-- END HOME -->

<!-- About -->
<section class="section">
    <div class="container">

        <div class="row">

            <div class="col-lg-6">
                <asset:image src="about.jpg" alt="about Img" class="img-fluid about-img rounded"></asset:image>
            </div>
            <!-- end col -->

            <div class="col-lg-6">

                <div class="about-txt">
                    <h3>Vivamus malesuada ipsum ets nibh eveniet.</h3>

                    <p>Lacus erat tristique interdum at vitae augue. Morbi erat tellus, dapibus vitae euismod. Quis consequatur quia voluptas sit aspernatur aut odit aut fugit justo sed mauris vel, lobortis tempor lorem ac lacus iaculis quia voluptas sit aspernatur aut odit aut fugit justo.</p>

                    <p>Pellentesque vitae metus suscipit purus pharetra lacinia. In ac accumsan tellus. Praesent justo lorem, tincidunt sed mauris vel, lobortis tempor lorem. Pellentesque ac lacus iaculis erat tristique interdum at vitae augue. Morbi erat tellus, dapibus vitae euismod quis ets.</p>

                    <div class="fun_facts_box text-center">
                        <i class="zmdi zmdi-book-image text-colored fun_facts_icons"></i>
                        <p class="fun_facts_title"><span class="facts_numbers">1050</span>
                            <br>Jobs Completed</p>
                    </div>
                    <!--end fun_facts_box -->

                    <div class="fun_facts_box text-center">
                        <i class="zmdi zmdi-accounts-alt text-colored fun_facts_icons"></i>
                        <p class="fun_facts_title"><span class="facts_numbers">2137</span>
                            <br>Happy Clients</p>
                    </div>
                    <!--end fun_facts_box -->

                    <div class="fun_facts_box text-center">
                        <i class="zmdi zmdi-headset-mic text-colored fun_facts_icons"></i>
                        <p class="fun_facts_title"><span class="facts_numbers">24/7</span>
                            <br>Fast Support</p>
                    </div>
                    <!--end fun_facts_box -->
                </div>
                <!-- end about text -->

            </div>
            <!-- end col -->

        </div>
        <!-- end row -->

    </div>
    <!-- end container -->
</section>
<!-- END About -->

<!-- TEAM -->
<section class="section bg-gray">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">Team</p>
                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">Creative People</h3>
                    <div class="border"></div>
                </div>
            </div>
        </div>
        <!-- end row -->

        <div class="row">
            <div class="col-lg-10 offset-lg-1">
                <div class="row team text-center">
                    <!-- team-member -->
                    <div class="col-lg-4">
                        <div class="team-member animated fadeInDown wow" data-wow-delay=".1s">
                            <asset:image src="team/team-1.jpg" alt="team-member" class="img-fluid rounded-circle"></asset:image>
                            <h4>Holden McGroin</h4>
                            <p class="text-muted">Product Designer & Founder</p>
                        </div>
                    </div>

                    <!-- team-member -->
                    <div class="col-lg-4">
                        <div class="team-member animated fadeInDown wow" data-wow-delay=".3s">
                            <asset:image src="team/team-2.jpg" alt="team-member" class="img-fluid rounded-circle"></asset:image>
                            <h4>Mike Oxbigg</h4>
                            <p class="text-muted">Developer & Co-founder</p>
                        </div>
                    </div>

                    <!-- team-member -->
                    <div class="col-lg-4">
                        <div class="team-member animated fadeInDown wow" data-wow-delay=".5s">
                            <asset:image src="team/team-3.jpg" alt="team-member" class="img-fluid rounded-circle"></asset:image>
                            <h4>Eilean Dover</h4>
                            <p class="text-muted">UI/UX Designer</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>
</section>
<!-- END TEAM -->

<!-- TESTIMONIALS -->

<section class="section">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">Testimonials</p>
                    <h3 class="fadeIn animated wow" data-wow-delay=".1s">Happy Clients</h3>
                    <div class="border"></div>
                </div>
            </div>
        </div>
        <!-- end row -->

        <div class="row">
            <div class="col-lg-8 offset-lg-2 text-center">
                <div id="testi-carousel" class="owl-carousel owl-spaced">

                    <div class="zoomIn animated wow" data-wow-delay=".1s">
                        <h4 class="testimonials-txt">I have been using this template for all my company needs for the last 3 years and couldn’t be happier with their service and expertise. They’ve surpassed all of my expectations and customer service!</h4>
                        <div class="clientblock">
                            <p><strong>Mark Wainright</strong>
                                <br> Company
                            </p>
                        </div>
                    </div>

                    <div class="zoomIn animated wow" data-wow-delay=".1s">
                        <h4 class="testimonials-txt">I have been using this template for all my company needs for the last 3 years and couldn’t be happier with their service and expertise. They’ve surpassed all of my expectations and customer service!</h4>
                        <div class="clientblock">
                            <p><strong>Mark Wainright</strong>
                                <br> Company
                            </p>
                        </div>
                    </div>

                    <div class="zoomIn animated wow" data-wow-delay=".1s">
                        <h4 class="testimonials-txt">I have been using this template for all my company needs for the last 3 years and couldn’t be happier with their service and expertise. They’ve surpassed all of my expectations and customer service!</h4>
                        <div class="clientblock">
                            <p><strong>Mark Wainright</strong>
                                <br> Company
                            </p>
                        </div>
                    </div>

                    ` </div>
                <!-- end testi -->

            </div>
            <!-- end col -->
        </div>
        <!-- end row -->
    </div>
    <!-- end container -->
</section>

<!--END TESTIMONIALS -->

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

<script>
    /* ==============================================
          Owl carousel for testimonials
          =============================================== */
    $(document).ready(function() {
        $("#testi-carousel").owlCarousel({
            // Most important owl features
            items: 1,
            itemsCustom: false,
            itemsDesktop: [1199, 1],
            itemsDesktopSmall: [980, 1],
            itemsTablet: [768, 1],
            itemsTabletSmall: false,
            itemsMobile: [479, 1],
            singleItem: false,
            startDragging: true,
            autoPlay: true
        });
    });
</script>

</body>

</html>