<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:38
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
    </title>

    <div style = "alignment: center" >
        <h1>
            <b>Welcome, ${user}!</b>
        </h1>
    </div>
</head>

<body>
    <div class = "navbar">
        <sec:ifAllGranted roles="ROLE_ADMIN" >
            <h1>
            <g:link controller="main" action="list">
                List of all users
            </g:link>
            </h1>
        </sec:ifAllGranted>
        <g:link controller="user" action="show">
            Details
        </g:link>
        <g:link controller="user" action="createCompany">
            Register company
        </g:link>
    </div>
</body>

</html>