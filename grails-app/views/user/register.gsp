<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 29.08.19
  Time: 22:49
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
    <style type="text/css" media="screen">
    #register {
        margin: 15px 0px;
        padding: 0px;
        text-align: center;
    }

    #register .inner {
        width: 500px;
        padding-bottom: 6px;
        margin: 60px auto;
        text-align: left;
        border: 1px solid #aab;
        background-color: #f0f0fa;
        -moz-box-shadow: 2px 2px 2px #eee;
        -webkit-box-shadow: 2px 2px 2px #eee;
        -khtml-box-shadow: 2px 2px 2px #eee;
        box-shadow: 2px 2px 2px #eee;
    }

    #register .inner .fheader {
        padding: 18px 26px 14px 26px;
        background-color: #f7f7ff;
        margin: 0px 0 14px 0;
        color: #2e3741;
        font-size: 18px;
        font-weight: bold;
    }


    #register .inner .cssform  .form-wrapper{
        clear: left;
        margin: 0;
        padding: 4px 0 3px 0;
        padding-left: 105px;
        margin-bottom: 20px;
        height: 1%;
    }

    #register .inner .cssform  .confirm-wrapper{
        clear: left;
        margin: 0;
        padding: 4px 0 3px 0;
        padding-left: 115px;
        margin-bottom: 20px;
        height: 1%;
    }



    #login .inner .cssform .form-wrapper p{
        width: 130px;
    }


    #login .inner .cssform label {
        font-weight: bold;
        float: left;
        text-align: right;
        margin-left: -90px;
        width: 110px;
        padding-top: 3px;
        padding-right: 10px;
    }

    #register .inner .cssform .formButton {
        margin-left: 190px;
    }

    #register .inner .formTitle {
        margin-left: 150px;
    }

    </style>
    <title> Register </title>
</head>

<body>


<div class="wrapper" id="register" >
    <div class="inner">

        <div class="formTitle">
            <h2>Registration Form</h2>
        </div>

        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div class="fheader">
            <g:hasErrors bean="${user}">
                <ul class="errors" role="alert">
                    <g:eachError bean="${user}" var="error">
                        <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
        </g:hasErrors>
        </div>

        <g:form controller="user" action="register" id="loginForm" class="cssform">

            <div class="form-wrapper">
                <p>
                    <label for="firstName">First Name:</label>
                    <g:textField id="text_" name="firstName" value="${user?.firstName}"></g:textField>
                </p>
            </div>

            <div class="form-wrapper">
                <p>
                    <label for="lastName">Last Name:</label>
                    <g:textField id="text_" name="lastName" value="${user?.lastName}"></g:textField>
                </p>
            </div>

            <div class="form-wrapper">
                <p>
                    <label for="username">Username: </label>
                    <g:textField id="text_" name="username" value="${user?.username}"></g:textField>
                </p>
            </div>

            <div class="form-wrapper">
                <p>
                    <label for="password">Password:  </label>
                    <g:passwordField id="text_" name="password" value="${user?.password}"></g:passwordField>
                </p>
            </div>

            <div class="confirm-wrapper">
                <p>
                    <label for="confirm">Confirm:     </label>
                    <g:passwordField id="text_" name="confirm" value="${params?.confirm}"></g:passwordField>
                </p>
            </div>

            <div class="formButton">
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