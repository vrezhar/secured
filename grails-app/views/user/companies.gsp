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
    <asset:stylesheet src="web_page/profile/table.css"></asset:stylesheet>
</head>

<body ng-app="myApp">
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
                                                        <button  class="btn btn-styled btn-block btn-rounded btn-base-1" id="settings">Settings</button>
                                                        <button  class="btn btn-styled btn-block btn-rounded btn-base-1" id="create">Create Company</button>
                                                        <button  class="btn btn-styled btn-block btn-rounded btn-base-1 btn-green" id="show">My Companies</button>
                                                        <g:link controller="user" action="list" class="btn btn-styled btn-block btn-rounded btn-base-1">Users</g:link>
                                                    </g:if>
                                                    <g:else>
                                                        <sec:ifAllGranted roles="ROLE_USER">
                                                            <button  class="btn btn-styled btn-block btn-rounded btn-base-1" id="settings">Settings</button>
                                                            <button  class="btn btn-styled btn-block btn-rounded btn-base-1" id="create">Create Company</button>
                                                            <button  class="btn btn-styled btn-block btn-rounded btn-base-1 btn-green" id="show">My Companies</button>
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

                                            <div class="row justify-content-center" id="show_wrapper">
                                                <div class="col-lg-12">
                                                    <div class="card form-card form-card--style-2">
                                                        <div class="form-header text-center">
                                                            <div class="form-header-icon">
                                                                <asset:image src="icons/custom/companies.png"></asset:image>
                                                            </div>
                                                            <div class="card-title">
                                                                <div class="text-center px-2">
                                                                    <h1 class="heading heading-4 strong-400 mb-4">Your Companies</h1>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <span class="space-md-md"></span>

                                                        <div class="card-body">

                                                            <span class="space-md-md"></span>

                                                            <div class="row">
                                                                <div class = "col-md-12" id="companies" style=" padding: 1% 1% 1% 1%;">
                                                                    <table>
                                                                        <g:each var="company" in="${user?.companies}">
                                                                            <tr>
                                                                                <td>
                                                                                    Id: ${company?.companyId}
                                                                                </td>
                                                                                <td>
                                                                                    Token: ${company?.token}
                                                                                </td>
                                                                                <td id = "${company?.token}">
                                                                                    <a href="#" class="btn">Show more</a>
                                                                                </td>
                                                                            </tr>
                                                                            <tr id = "${company?.token}_details" style="visibility: hidden; display: none;">
                                                                                <td>
                                                                                    Address: ${company?.address}
                                                                                </td>
                                                                                <td>
                                                                                    Date created: ${company?.dateCreated}
                                                                                </td>
                                                                                <td>
                                                                                    Products registered: ${company?.products?.size()}
                                                                                </td>
                                                                            </tr>
                                                                        </g:each>
                                                                    </table>
                                                                </div>
                                                            </div>

                                                            <span class="space-lg-only-1"></span>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row justify-content-center" id="create_wrapper" style="display: none; visibility: hidden;">
                                                <div class="col-lg-12">
                                                    <div class="card form-card form-card--style-2">
                                                        <div class="form-header text-center">
                                                            <div class="form-header-icon">
                                                                <asset:image src="icons/custom/signature_2.png"></asset:image>
                                                            </div>
                                                        </div>

                                                        <span class="space-md-md"></span>
                                                        <div class="card-body">
                                                            <div class="card-title">
                                                                <div class="text-center px-2">
                                                                    <h1 class="heading heading-4 strong-400 mb-4">Copy your signature here</h1>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    <g:textArea type="text" class="form-control form-control-lg" name="signature"  placeholder="Your signature..." id="signature"></g:textArea>
                                                                </div>
                                                            </div>

                                                            <div class="row" style="visibility: hidden; display: none;" id="loader">
                                                                <div class="spinner">

                                                                </div>
                                                                <label>Please  wait...</label>
                                                            </div>

                                                            <span class="space-md-md"></span>

                                                            <div class="row">
                                                                <div class="company" id="company" style="visibility: hidden; display: none;">
                                                                    <div class="card" style="margin-left: 5%; width: 200%">
                                                                        <p class="c-gray-light" style="font-size: large">
                                                                            <label for="companyId" style="font-size: large">Company Id: </label>
                                                                            <b type="text" class="text_" id = "companyId"></b>
                                                                        </p>
                                                                        <p class="c-gray-light" style="font-size: large">
                                                                            <label for="address" style="font-size: large">Address: </label>
                                                                            <b type="text" class="text_" id="address" ></b>
                                                                        </p>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <span class="space-lg-only-1"></span>

                                                            <div class="row">
                                                                <div class="col-md-2" id="confirm" style="visibility: hidden; display: none;">
                                                                    <input class="btn btn-styled btn-lg btn-circle btn-success mt-1" type="submit"  onclick="confirm()" value="Confirm">
                                                                </div>
                                                                <div class="col-md-2" id="cancel" style="visibility: hidden; display: none;">
                                                                    <input class="btn btn-styled btn-lg btn-circle btn-danger mt-1" type="submit"  onclick="cancel()" value="Cancel">
                                                                </div>
                                                            </div>

                                                            <span class="space-lg-only-1"></span>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!-- Account settings -->
                                            <div class="row" id="settings_wrapper" style="display: none;visibility: hidden">
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
                                            </div>
                                        </div>

                                        <hr class="mt-0 mb-0">

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
<asset:javascript src="signature/verify.js"></asset:javascript>
<asset:javascript src="signature/onInput.js"></asset:javascript>
<asset:javascript src="signature/loader.js"></asset:javascript>
<asset:javascript src="profile/update.js"></asset:javascript>
<asset:javascript src="profile/show_more.js"></asset:javascript>
</body>
</html>