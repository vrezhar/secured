<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 09.10.19
  Time: 19:13
--%>

<%@ page import="com.secured.auth.Role; com.secured.auth.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
</head>

<body>
<div class="body-wrap" data-template-mode="cards">
    <div id="st-container" class="st-container">

        <nav class="st-menu st-effect-1" id="menu-1">
            <div class="st-profile">
                <div class="st-profile-user-wrapper">
                    <div class="profile-user-image">
                        <asset:image src="icons/custom/login_3.png" class="img-circle"></asset:image>
                    </div>
                    <div class="profile-user-info">
                        <span class="profile-user-name">${user?.firstName + " " + user?.lastName}</span>
                        <span class="profile-user-email">${user?.username}</span>
                    </div>
                </div>
            </div>
        </nav>

        <div class="st-pusher">
            <div class="st-content">
                <div class="st-content-inner">

                    <!-- Header -->
                    <div class="header">
                        <!-- Navbar -->
                        <nav class="navbar navbar-expand-lg navbar--bold navbar-light bg-default  navbar--bb-1px">
                            <div class="container navbar-container">
                                <!-- Brand/Logo -->
                                <a class="navbar-brand" href="/profile">
                                    <asset:image src="grails.svg" class="" alt="Boomerang"></asset:image>
                                </a>

                                <div class="col-md-6">
                                    <span class="aux-text d-none d-md-inline-block">

                                    </span>
                                </div>
                                <div class="col-md-6">
                                    <span class="aux-text d-none d-md-inline-block">

                                    </span>
                                </div>

                                <div class="col-md-6">
                                    <nav class="top-navbar-menu">
                                        <ul class="top-menu" style="list-style: none">
                                            <li>
                                                <sec:ifLoggedIn>
                                                    <g:link controller="logout">
                                                        Logout
                                                    </g:link>
                                                </sec:ifLoggedIn>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </nav>
                    </div>

                    <section class="slice-sm sct-color-1">
                        <div class="profile">
                            <div class="container">
                                <div class="row cols-xs-space cols-sm-space cols-md-space">
                                    <div class="col-lg-4">
                                        <div class="sidebar sidebar--style-2 no-border">
                                            <div class="widget">
                                                <!-- Profile picture -->
                                                <div class="profile-picture profile-picture--style-2">
                                                    <asset:image class="img-center" src="icons/custom/login_3.png"> </asset:image>
                                                </div>

                                                <!-- Profile details -->
                                                <div class="profile-details">
                                                    <h2 class="heading heading-4 strong-500 profile-name">${user?.firstName + " " + user?.lastName}</h2>
                                                </div>

                                                <!-- Profile connect -->
                                                <div class="profile-connect mt-4">
                                                <g:if test="${user?.authorities?.contains(Role.findWhere(authority: "ROLE_ADMIN"))}">
                                                    <g:link controller="user" action="profile" class="btn btn-styled btn-block btn-rounded btn-base-1">Settings</g:link>
                                                    <g:link controller="user" action="createCompany" class="btn btn-styled btn-block btn-rounded btn-base-1">Create Company</g:link>
                                                    <g:link controller="user" action="showCompanies" class="btn btn-styled btn-block btn-rounded btn-base-1">My Companies</g:link>
                                                    <g:link controller="user" action="list" class="btn btn-styled btn-block btn-rounded btn-base-1">Users</g:link>
                                                </g:if>
                                                <g:else>
                                                    <sec:ifAllGranted roles="ROLE_USER">
                                                        <g:link controller="user" action="profile" class="btn btn-styled btn-block btn-rounded btn-base-1">Settings</g:link>
                                                        <g:link controller="user" action="createCompany" class="btn btn-styled btn-block btn-rounded btn-base-1">Create Company</g:link>
                                                        <g:link controller="user" action="showCompanies" class="btn btn-styled btn-block btn-rounded btn-base-1">My Companies</g:link>
                                                    </sec:ifAllGranted>
                                                </g:else>
                                                </div>

                                                <!-- Profile stats -->
                                                <div class="profile-stats clearfix">
                                                    <div class="stats-entry">
                                                        <span class="stats-count">${user?.companies?.size()}</span>
                                                        <span class="stats-label text-uppercase">Companies</span>
                                                    </div>
                                                    <div class="stats-entry">
                                                        <span class="stats-count">${user?.getBarCodes()}</span>
                                                        <span class="stats-label text-uppercase">Codes</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-8">
                                        <div class="main-content">

                                            <!-- Account settings -->
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <form class="form-default" data-toggle="validator" role="form">
                                                        <!-- General information -->
                                                        <div class="card no-border bg-transparent">
                                                            <div class="card-title px-0 pb-0 no-border">
                                                                <h3 class="heading heading-6 strong-600">
                                                                    General information
                                                                </h3>
                                                            </div>
                                                        <div class="card-body px-0">
                                                            <div class="row">
                                                                <div class="col-md-6 col-lg-4">
                                                                    <div class="form-group">
                                                                        <label class="control-label">First name</label>
                                                                        <input type="text" class="form-control form-control-lg" value="${user?.firstName}" disabled>
                                                                    </div>
                                                                </div>

                                                                <div class="col-md-6 col-lg-4">
                                                                    <div class="form-group">
                                                                        <label class="control-label">Last name</label>
                                                                        <input type="text" class="form-control form-control-lg" value="${user?.lastName}" disabled>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        <g:if test="${user?.authorities?.contains(Role.findWhere(authority: "ROLE_ADMIN"))}">
                                                            <div class="text-right">
                                                                <a href="#" class="btn btn-base-1">Edit...</a>
                                                            </div>
                                                            </div>
                                                        </g:if>
                                                        <g:else>
                                                            <sec:ifAllGranted roles="ROLE_USER">
                                                                <div class="text-right">
                                                                    <a href="#" class="btn btn-base-1">Edit...</a>
                                                                </div>
                                                                </div>
                                                            </sec:ifAllGranted>
                                                        </g:else>
                                                        </div>

                                                        <hr class="mt-0 mb-0">

                                                        <!-- Account info -->
                                                        <div class="card no-border bg-transparent">
                                                            <div class="card-title px-0 pb-0 no-border">
                                                                <h3 class="heading heading-6 strong-600">
                                                                    Account info
                                                                </h3>
                                                            </div>
                                                            <div class="card-body px-0">
                                                                <div class="row align-items-center">
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Email</label>
                                                                            <input type="email" class="form-control form-control-lg" value="${user?.username}" disabled>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-6 col-lg-4">
                                                                        <div class="form-group">
                                                                            <label class="control-label">Joined at</label>
                                                                            <input type="email" class="form-control form-control-lg" value="${user?.dateCreated}" disabled>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <hr class="mt-0 mb-0">

                                                    </form>
                                                </div>
                                            </div>

                                            <hr class="mt-0 mb-0">

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <!-- FOOTER -->
                    <footer id="footer" class="footer">
                        <div class="footer-top">
                            <div class="container">
                                <div class="row cols-xs-space cols-sm-space cols-md-space">
                                    <div class="col-lg-5">
                                        <div class="col">
                                            <div class="copyright mt-4">
                                                <p>
                                                    Copyright &copy; 2019<a href="http://localhost:8080" target="_blank">
                                                    <strong>TTReport</strong>
                                                </a> -
                                                All rights reserved
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </footer>
                </div>
            </div>
        </div><!-- END: st-pusher -->
    </div><!-- END: st-container -->
</div><!-- END: body-wrap -->

<!-- SCRIPTS -->
<div class="back-to-top btn-back-to-top">
    <a href="#" ><asset:image src="icons/custom/up.png"></asset:image></a>
</div>

</body>
</html>