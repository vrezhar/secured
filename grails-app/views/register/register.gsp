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
    <title> Register </title>
</head>

<body>
<!-- MAIN WRAPPER -->
<div class="body-wrap" style="background: transparent;">
    <div id="st-container" class="st-container">

        <section class="slice-lg has-bg-cover bg-size-cover" style="background-image: url(../../assets/images/backgrounds/slider/img-45.jpg); background-position: bottom center;">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-title">
                                <h5 class="heading heading-5 strong-500">
                                    Create a new account
                                </h5>
                            </div>
                            <div class="card-body">
                                <g:form class="form-default" method="POST" url="/register/confirm" id="register_form">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
%{--                                                <label>First name</label>--}%
                                                <g:textField name="firstName" class="form-control form-control-lg" id="firstName" placeholder="First name" value="${user?.firstName}"></g:textField>
                                                <ul class="col-md-6"  id="firstName_errors" style="visibility: hidden; display: none;">

                                                </ul>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
%{--                                                <label>Last name</label>--}%
                                                <g:textField type="text" class="form-control form-control-lg" name="lastName" id="lastName" value="${user?.lastName}" placeholder="Last name"></g:textField>
                                                <ul class="col-md-6" id="lastName_errors" style="visibility: hidden; display: none;">

                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <span class="space-md-md"></span>

                                    <div class="row">
%{--                                        <div class="col-md-6">--}%
%{--                                            <div class="form-group">--}%
%{--                                                <label>Username</label>--}%
%{--                                                <input type="text" class="form-control form-control-lg">--}%
%{--                                            </div>--}%
%{--                                        </div>--}%

                                        <div class="col-md-12">
                                            <div class="form-group">
%{--                                                <label>Email</label>--}%
                                                <g:textField type="email" class="form-control form-control-lg" name="username" id="username" value="${user?.username}" placeholder="Email"></g:textField>
                                                <ul id="username_errors" style="visibility: hidden; display: none">

                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <span class="space-md-md"></span>

                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-group">
%{--                                                <label>Password</label>--}%
                                                <g:textField type="password" class="form-control form-control-lg" name="password" id="password" value="${user?.password}" placeholder="Password"></g:textField>
                                                <ul id="password_errors" style="visibility: hidden; display: none">

                                                </ul>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="form-group">
%{--                                                <label>Confirm password</label>--}%
                                                <g:textField type="password" class="form-control form-control-lg" name="confirm" id="confirm" placeholder="Confirm password" value="${user?.confirm}"></g:textField>
                                                <ul id="confirm_errors" style="visibility: hidden; display: none">

                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                    <span class="space-md-md"></span>

%{--                                    <div class="row">--}%
%{--                                        <div class="col-md-6">--}%
%{--                                            <div class="form-group">--}%
%{--                                                <label>Country</label>--}%
%{--                                                <select class="form-control form-control-lg selectpicker">--}%
%{--                                                    <option>Argentina</option>--}%
%{--                                                    <option>Brazil</option>--}%
%{--                                                    <option>Canada</option>--}%
%{--                                                    <option>Dominican Republic</option>--}%
%{--                                                    <option>France</option>--}%
%{--                                                    <option>Greece</option>--}%
%{--                                                </select>--}%
%{--                                            </div>--}%
%{--                                        </div>--}%

%{--                                        <div class="col-md-6">--}%
%{--                                            <div class="form-group">--}%
%{--                                                <label>City</label>--}%
%{--                                                <input type="text" class="form-control form-control-lg">--}%
%{--                                            </div>--}%
%{--                                        </div>--}%
%{--                                    </div>--}%

%{--                                    <div class="row">--}%
%{--                                        <div class="col-sm-12">--}%
%{--                                            <div class="form-group">--}%
%{--                                                <label>Address</label>--}%
%{--                                                <input type="text" class="form-control form-control-lg">--}%
%{--                                            </div>--}%
%{--                                        </div>--}%
%{--                                    </div>--}%

                                    <input type="submit" class="btn btn-styled btn-base-1 mt-3" value="Create account"/>
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