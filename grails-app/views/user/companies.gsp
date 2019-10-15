<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 14.10.19
  Time: 22:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <title></title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:stylesheet src="web_page/profile/table.css"></asset:stylesheet>
    <asset:javascript src="profile/show_more.js"></asset:javascript>
</head>

<body>
<div class="body-wrap" style="background: transparent;">

    <div id="st-container" class="st-container">

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

        <section class="slice sct-color-2">
            <div class="container">
                <div class="row justify-content-center">
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
            </div>
        </section>
    </div>
</div>
</body>
</html>