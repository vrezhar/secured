--%>
<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:38
--%>

<%@ page import="org.springframework.validation.FieldError" contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:stylesheet src="web_page/authorization/register/register.css"></asset:stylesheet>
    <asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
    <title> Register </title>
</head>

<body>


<div class="wrapper" id="register" >
    <div class="inner">
        <h1>Registration Form</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <g:form id ="register_form" url="${postUrl ?: '/register/confirm'}" class="cssform">

            <div class="form-wrapper">
                <label for="firstName">First Name:</label>
                <g:textField id="firstName" name="firstName" value="${user?.firstName}"></g:textField>
                <ul class = "errors" role="alert" id="firstName_errors" style="visibility: hidden; display: none">

                </ul>
            </div>

            <div class="form-wrapper">
                <label for="lastName">Last Name:</label>
                <g:textField id="lastName" name="lastName" value="${user?.lastName}"></g:textField>
                <ul class = "errors" role="alert" id="lastName_errors" style="visibility: hidden; display: none">

                </ul>
            </div>

            <div class="form-wrapper">
                <label for="username">Email: </label>
                <g:textField id="username" name="username" value="${user?.username}"></g:textField>
                <ul class = "errors" role="alert" id="username_errors" style="visibility: hidden; display: none">

                </ul>
            </div>

            <div class="form-wrapper">
                <label for="password">Password:  </label>
                <g:passwordField id="password" name="password" value="${user?.password}"></g:passwordField>
                <ul class = "errors" role="alert" id="password_errors" style="visibility: hidden; display: none">

                </ul>
            </div>

            <div class="form-wrapper">
                <label for="confirm">Confirm:</label>
                <g:passwordField id="confirm" name="confirm" value="${user?.confirm}"></g:passwordField>
                <ul class = "errors" role="alert" id="confirm_errors" style="visibility: hidden; display: none">

                </ul>
            </div>

            <div class="form-button">
                <input type="submit" id="submit" value="Register"/>
            </div>
        </g:form>
    </div>
</div>

<asset:javascript src="register/async_validate.js"></asset:javascript>
</body>
</html>