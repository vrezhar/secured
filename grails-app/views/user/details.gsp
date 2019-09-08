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
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
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

        <div class = "companies">
            <table>
                <tr>
                    <th>Company Address</th>
                    <th>Date Created</th>
                </tr>
                <g:each var="company" in="${user.companies}">
                    <tr>
                        <th>${company.address}</th>
                        <th>${company.dateCreated}</th>
                    </tr>
                </g:each>
            </table>
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