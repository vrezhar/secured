<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 29.08.19
  Time: 17:51
--%>

<%@ page import="grails.plugin.springsecurity.SpringSecurityService" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>
        Success!
    </title>
</head>

<body>
    <h1>You have successfully registered!</h1>
    <h2>Please verify your email account before proceeding.</h2>
    <h3>Click here to <g:link controller="login">log in </g:link></h3>
</body>
</html>