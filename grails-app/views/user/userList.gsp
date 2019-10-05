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
    <style>
        #users .table{
            align-content: center;
        }
        #users .table a
        {
            display:block;
            text-decoration:none;
        }
        #users .table td a{
            text-align: center;
            font-size: larger;
        }
        #users .table th{
            font-size: inherit;
            text-align: center;
        }
        body h1
        {
            color: inherit;
            margin-left: 23%;
        }
        #users {
            width: 800px;
            margin-left: 5%;
        }
    </style>
</head>

<body>

    <h1>User List</h1>
    <div class = "list" id="users">
        <div class="table">
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                </tr>
                <g:each var="user" in="${users}">
                    <tr>
                        <td><a href = "${createLink(uri: "/user/show?id=${user.id}")}">${user.firstName}</a></td>
                        <td><a href = "${createLink(uri: "/user/show?id=${user.id}")}">${user.lastName}</a></td>
                    </tr>
                </g:each>
            </table>
        </div>
    </div>
</body>
</html>