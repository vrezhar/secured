<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:38
--%>

<%@ page import="org.springframework.validation.FieldError" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body id="body">
<h1>Registration</h1>
<p>Complete the form below to create an account!</p>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:hasErrors bean="${user}">
    <ul class="errors" role="alert">
        <g:eachError bean="${user}" var="error">
            <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
<g:form controller="user" action="register" name="registerForm">
    <div class="formField" id="usernameForm">
        <label for="username">Username:</label>
        <g:textField name="username" value="${user?.username}"></g:textField>
    </div>
    <div class="formField">
        <label for="password">Password:</label>
        <g:passwordField name="password" value="${user?.password}"></g:passwordField>
    </div>
    <div class="formField">
        <label for="confirm">Confirm Password:</label>
        <g:passwordField name="confirm" value="${params?.confirm}"></g:passwordField>
    </div>
    <div class="formField">
        <label for="firstName">First Name:</label>
        <g:textField name="firstName" value="${user?.firstName}"></g:textField>
    </div>
    <div class="formField">
        <label for="lastName">Last Name:</label>
        <g:textField name="lastName" value="${user?.lastName}"></g:textField>
    </div>
    <g:submitButton class="formButton" name="register" value="Register"></g:submitButton>
</g:form>
<script>
    (function() {
        document.forms['usernameForm'].elements['username'].focus();
    })();
</script>
</body>
</html>