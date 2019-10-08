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
    <asset:stylesheet src="web_page/authorization/register.css"></asset:stylesheet>
    <title> Register </title>
</head>

<body>


<div class="wrapper" id="register" >
    <div class="inner">
        <h1>Registration Form</h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <g:form url="${postUrl ?: '/register/confirm'}" class="cssform">

            <div class="form-wrapper">
                <label for="firstName">First Name:</label>
                <g:textField id="text_" name="firstName" value="${user?.firstName}"></g:textField>
                <g:hasErrors bean="${user}" var="error">
                    <g:eachError bean="${user}" var="error">
                        <g:if test="${error?.field == "firstName"}">
                            <ul class = "errors" role="alert">
                                <li>
                                    <g:message error="${error}"/>
                                </li>
                            </ul>
                        </g:if>
                    </g:eachError>
                </g:hasErrors>
            </div>

            <div class="form-wrapper">
                <label for="lastName">Last Name:</label>
                <g:textField id="text_" name="lastName" value="${user?.lastName}"></g:textField>
                    <g:hasErrors bean="${user}" var="error">
                        <g:eachError bean="${user}" var="error">
                            <g:if test="${error?.field == "lastName"}">
                                <ul class = "errors" role="alert">
                                    <li>
                                        <g:message error="${error}"/>
                                    </li>
                                </ul>
                            </g:if>
                        </g:eachError>
                    </g:hasErrors>
            </div>

            <div class="form-wrapper">
                <label for="email">Email:</label>
                <g:textField id="text_" name="email" value="${user?.email}"></g:textField>
                    <g:hasErrors bean="${user}" var="error">
                        <g:eachError bean="${user}" var="error">
                            <g:if test="${error?.field == "email"}">
                                <ul class = "errors" role="alert">
                                    <li>
                                        <g:message error="${error}"/>
                                    </li>
                                </ul>
                            </g:if>
                        </g:eachError>
                    </g:hasErrors>
            </div>

            <div class="form-wrapper">
                <label for="username">Username: </label>
                <g:textField id="text_" name="username" value="${user?.username}"></g:textField>

                    <g:hasErrors bean="${user}" var="error">
                        <g:eachError bean="${user}" var="error">
                            <g:if test="${error?.field == "username"}">
                                <ul class = "errors" role="alert">
                                    <li>
                                        <g:message error="${error}"/>
                                    </li>
                                </ul>
                            </g:if>
                        </g:eachError>
                    </g:hasErrors>

            </div>

            <div class="form-wrapper">
                <label for="password">Password:  </label>
                <g:passwordField id="text_" name="password" value="${user?.password}"></g:passwordField>

                    <g:hasErrors bean="${user}" var="error">
                        <g:eachError bean="${user}" var="error">
                            <g:if test="${error?.field == "password"}">
                                <ul class = "errors" role="alert">
                                    <li>
                                        <g:message error="${error}"/>
                                    </li>
                                </ul>
                            </g:if>
                        </g:eachError>
                    </g:hasErrors>

            </div>

            <div class="form-wrapper">
                <label for="confirm">Confirm:     </label>
                <g:passwordField id="text_" name="confirm" value="${params?.confirm}"></g:passwordField>
            </div>

            <div class="form-button">
                <g:submitButton  name="register" value="Register" ></g:submitButton>
            </div>
        </g:form>
    </div>
</div>
<script>
    (function() {
        document.forms['usernameForm'].elements['username'].focus();
    })();
</script>
</body>
</html>