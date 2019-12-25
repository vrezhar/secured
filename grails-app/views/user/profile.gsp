<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 25.11.19
  Time: 22:12
--%>

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Welcome!</title>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:stylesheet src="web_page/profile/table.css"></asset:stylesheet>
</head>

<body data-spy="scroll" data-target="#navbar-menu">
<nav class="navbar navbar-expand-lg fixed-top navbar-custom sticky sticky-dark">
    <div class="container">
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
                <li class="nav-item">
                    <g:link controller="logout" class="navbar-btn">
                        <g:message code="website.log.out"></g:message>
                    </g:link>
                </li>
            </ul>

        </div>
    </div>
</nav>

<section class="section" id="home">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">
                        Account Info
                    </p>
                    <g:if test='${flash.message}'>
                        <div class="profile_message" id="profile_message" style="text-align: center">${flash.message}</div>
                    </g:if>
                    <div class="border"></div>
                </div>
            </div>
        </div>

        <div class="row text-center">
            <div class="col-lg-4">
                <div class="service-item animated fadeInLeft wow" data-wow-delay=".1s">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div>
                </div>
            </div>
        </div>


    </div>
</section>

<section class="section" id="features">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">
                        Register Company
                    </p>
                    <div class="border"></div>
                </div>
            </div>
        </div>

        <div class="row text-center">
            <div class="col-lg-4">
                <div class="service-item animated fadeInLeft wow" data-wow-delay=".1s">
                    <div class="service-detail">
                        <h4 class="mb-3">
                            <g:message code="website.home.features.name.dummy"></g:message>
                        </h4>
                        <p>
                            <g:message code="website.home.feature.dummy"></g:message>
                        </p>
                    </div>
                </div>
            </div>
        </div>


    </div>
</section>

<section class="section" id="faq">
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center">
                <div class="title-box">
                    <p class="title-alt">My Companies</p>
                    <div class="border"></div>
                </div>
            </div>
            <div class="col-lg-12">
                <g:if test="${user?.companies}">
                    <table class = "animated fadeInLeft wow" data-wow-delay=".1s">
                        <tr>
                            <td>
                                Id
                            </td>
                            <td>
                                Name
                            </td>
                            <td>
                                Token
                            </td>
                            <td></td>
                        </tr>
                        <g:each var="company" in="${user?.companies}">
                            <tr>
                                <td>
                                    ${company?.companyId}
                                </td>
                                <td>
                                    ${company?.name}
                                </td>
                                <td>
                                    ${company?.token}
                                </td>
                                <td id = "${company?.token}">
                                    <a href="#" >Show more</a>
                                </td>
                            </tr>
                            <tr id = "${company?.token}_description" style="visibility: hidden; display: none;">
                                <td>
                                    Address
                                </td>
                                <td>
                                    Date created
                                </td>
                                <td>
                                    Registered Products
                                </td>
                                <td></td>
                            </tr>
                            <tr id = "${company?.token}_details" style="visibility: hidden; display: none;">
                                <td>
                                    ${company?.address}
                                </td>
                                <td>
                                    ${company?.dateCreated}
                                </td>
                                <td>
                                    ${company?.products?.size()}
                                </td>
                                <td></td>
                            </tr>
                        </g:each>
                    </table>
                </g:if>
                <g:else>
                    <p>No Company registered</p>
                </g:else>
            </div>
        </div> <!-- end row -->
    </div>
</section>
<!-- END FAQ -->


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
<asset:javascript src="signature/verify.js"></asset:javascript>
<asset:javascript src="signature/onInput.js"></asset:javascript>
<asset:javascript src="signature/loader.js"></asset:javascript>
<asset:javascript src="profile/update.js"></asset:javascript>
<asset:javascript src="profile/show_more.js"></asset:javascript>
</body>
</html>