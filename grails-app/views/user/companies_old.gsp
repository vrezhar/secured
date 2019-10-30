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
    <asset:stylesheet src="web_page/profile/details.css"></asset:stylesheet>
</head>

<body>
<div class = "inner" id = "details">
    <div class="info-wrapper">
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
            <label for = "username">Email:</label>
            <p id = "username">
                ${user.username}
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
                    ${user.mainToken}
                </p>
            </div>
        </sec:ifAllGranted>

        <sec:ifAllGranted roles="ROLE_ADMIN">
            <div class = "info">
                <label for = "status">Account status:</label>
                <p id = "status">
                    <g:if test="${user.enabled}">
                        Enabled
                    </g:if>
                    <g:elseif test="${!user.enabled}">
                        Disabled
                    </g:elseif>
                </p>
            </div>
        </sec:ifAllGranted>
    </div>

    <div class = "companies" >
        <table>
            <tr>
                <th>Company Address</th>
                <th>Date Created</th>
                <th>Company Id</th>
                <th>Token </th>
            </tr>
            <g:each var="company" in="${user.companies}">
                <tr>
                    <td>${company.address}</td>
                    <td>${company.dateCreated}</td>
                    <td>${company.companyId}</td>
                    <td>${company.token}</td>
                </tr>
            </g:each>
        </table>
    </div>
</div>


</body>
</html>