<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 05.09.19
  Time: 19:00
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>
    <div class = "inner" id = "details">

        <div class = "info">
            <label for = "firstName">First Name:</label>
            <p id = "firstName">
                ${user.firstName}
            </p>
        </div>

        <div class = "info">
            <label for = "lastName">Last Name:</label>
            <p id = "lastName">
                ${user.lastName}
            </p>
        </div>

        <div class = "info">
            <label for = "username">Username:</label>
            <p id = "username">
                ${user.username}
            </p>
        </div>

        <div class = "info">
            <label for = "email">Email:</label>
            <p id = "email">
                ${user.email}
            </p>
        </div>

        <div class = "info">
            <label for = "dateCreated">Joined at:</label>
            <p id = "dateCreated">
                ${user.dateCreated}
            </p>
        </div>

        <sec:ifAllGranted roles="ROLE_USER">
            <div class = "info">
                <label for = "mainToken">Main Token:</label>
                <p id = "mainToken">
                    ${user?.mainToken ?: "This mustn't be shown"}
                </p>
            </div>
        </sec:ifAllGranted>

    </div>

</body>
</html>