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

    body{
        background-image: linear-gradient(rgb(240,255,255),#f0f0fa);
    }

    #register .inner {
        width: 500px;
        padding-bottom: 6px;
        margin: 60px auto;
        text-align: left;
        border: 1px solid rgb(233, 248, 250);
        background-color: #f1effa;
        -moz-box-shadow: 2px 2px 2px #eee;
        -webkit-box-shadow: 2px 2px 2px #eee;
        -khtml-box-shadow: 2px 2px 2px #eee;
        box-shadow: 2px 2px 2px #eee;
    }
    #register .inner .cssform  .form-wrapper{
        clear: left;
        margin: 0;
        padding: 4px 0 3px 0;
        padding-left: 105px;
        margin-bottom: 20px;
        height: 1%;
    }

    #register .inner .cssform  .form-wrapper ul{
        margin: 0;
        padding: 0;
        background-color: #f0f0fa;
        -moz-box-shadow: 2px 2px 2px #f0f0fa;
        -webkit-box-shadow: 2px 2px 2px #f0f0fa;
        -khtml-box-shadow: 2px 2px 2px #f0f0fa;
        border: #f0f0fa;
    }

    #register .inner .cssform label {
        font-weight: bold;
        float: left;
        text-align: left;
        margin-left: -90px;
        width: 95px;
        padding-top: 3px;
        padding-right: 10px;
    }

    #register .inner .cssform .form-button {
        margin-left: 190px;
    }

    #register .inner .cssform .formButton input {
        width: 100px;
        height: 25px;
        align-content: center;
        font-weight: bold;
    }

    #register .inner h1{
        margin-left:  180px;
        padding-bottom:  25px;
        font-weight: bolder;
    }
    </style>
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