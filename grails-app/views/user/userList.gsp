<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>

    </title>
    <h1>User List</h1>
    <style>
    .table a
    {
        display:block;
        text-decoration:none;
    }
    </style>
</head>

<body>
<div class="table">
<table>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
    </tr>
    <g:each var="user" in="${users}">
        <tr>
            <th><a href = "${createLink(uri: "/user/show?id=${user.id}")}">${user.firstName}</a></th>
            <th><a href = "">${user.lastName}</a></th>
        </tr>
    </g:each>
</table>
</div>
</body>
</html>