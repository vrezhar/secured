<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:41
--%>

<%@ page import="com.ttreport.auth.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of all users</title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:stylesheet src="web_page/admin/table.css"></asset:stylesheet>
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
                                <div class="card-title">
                                    <div class="text-center px-2">
                                        <h1 class="heading heading-4 strong-400 mb-4">All Users</h1>
                                    </div>
                                </div>
                            </div>

                            <span class="space-md-md"></span>

                            <div class="card-body">

                                <span class="space-md-md"></span>

                                <div class="row">
                                    <div class = "col-md-12" id="companies" style=" padding: 1% 1% 1% 1%;">
                                        <table>
                                            <tr>
                                                <td>Username</td>
                                                <td>First Name</td>
                                                <td>Last Name</td>
                                                <td>Profile status</td>
                                            </tr>
                                            <g:each var="user" in="${User.list()}">
                                                <tr>
                                                    <td><a href = "${createLink(uri: "/user/show?id=${user.id}")}">${user.username}</a></td>
                                                    <td>${user.firstName}</td>
                                                    <td>${user.lastName}</td>
                                                    <g:if test="${user.enabled}">
                                                        <td id="${user.username}" class="btn btn-base-1 btn-green enable_toggler" style="display: table-cell;">Enabled</td>
                                                    </g:if>
                                                    <g:elseif test="${!user.enabled}">
                                                        <td id="${user.username}" class="btn btn-base-1 btn-red disable_toggler"  style="display: table-cell;">Disabled</td>
                                                    </g:elseif>
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
<asset:javascript src="admin/grant_ability.js"></asset:javascript>
</body>
</html>