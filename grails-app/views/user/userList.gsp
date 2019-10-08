<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 22.08.19
  Time: 01:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>List of all users</title>
    <asset:stylesheet src="web_page/profile/user_list.css"></asset:stylesheet>
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