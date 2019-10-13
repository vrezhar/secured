<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 13.10.19
  Time: 21:28
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:stylesheet src="web_page/authorization/register/register.css"></asset:stylesheet>
    <asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
    <asset:stylesheet src="web_page/authorization/register/errors.css"></asset:stylesheet>
    <title> Register </title>
</head>

<body>
<!-- MAIN WRAPPER -->
<div class="body-wrap" style="background: transparent;">
    <div id="st-container" class="st-container">

        <section class="slice sct-color-2">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-12">
                        <div class="card form-card form-card--style-2">
                            <div class="form-header text-center">
                                <div class="form-header-icon">
                                    <i class="icon ion-log-in"></i>
                                </div>
                            </div>
                            <div class="card-title">
                                <div class="text-center px-2">
                                    <h4 class="heading heading-4 strong-400 mb-4">Create a new account</h4>
                                </div>
                            </div>
                            <div class="card-body">
                                <g:form class="form-default" method="POST" url="/register/confirm" id="register_form">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <g:textField name="firstName" class="form-control form-control-lg" id="firstName" placeholder="First name" value="${user?.firstName}"></g:textField>
                                            <ul class="error"  id="firstName_errors" style="visibility: visible; display: inline-block">
                                                <li> </li>
                                            </ul>
                                        </div>

                                        <div class="col-md-6">
                                            <g:textField type="text" class="form-control form-control-lg" name="lastName" id="lastName" value="${user?.lastName}" placeholder="Last name"></g:textField>
                                            <ul class="error" id="lastName_errors" style="visibility: visible; display: inline-block">
                                                <li> </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <span class="space-lg-only-1"></span>

                                    <div class="row">

                                        <div class="col-md-12">
                                            <g:textField type="email" class="form-control form-control-lg" name="username" id="username" value="${user?.username}" placeholder="Email"></g:textField>
                                            <ul class="error" id="username_errors" style="visibility: visible; display: inline-block">
                                                <li> </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <span class="space-lg-only-1"></span>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <g:passwordField type="password" class="form-control form-control-lg" name="password" id="password" value="${user?.password}" placeholder="Password"></g:passwordField>
                                            <ul class="error" id="password_errors" style="visibility: visible; display: inline-block">
                                                <li> </li>
                                            </ul>
                                        </div>

                                        <div class="col-md-6">
                                            <g:passwordField type="password" class="form-control form-control-lg" name="confirm" id="confirm" placeholder="Confirm password" value="${user?.confirm}"></g:passwordField>
                                            <ul class="error" id="confirm_errors" style="visibility: visible; display: inline-block">
                                                <li> </li>
                                            </ul>
                                        </div>
                                    </div>

                                    <span class="space-lg-only-1"></span>

                                    <input type="submit" class="btn btn-styled btn-base-1 mt-1" style="width: 90%; margin-left: 5%" value="Create account"/>

                                    <span class="space-md-md"></span>
                                </g:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

<asset:javascript src="register/async_validate.js"></asset:javascript>
</body>
</html>