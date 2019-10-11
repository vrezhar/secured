<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 11.10.19
  Time: 16:54
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>

    <title><g:message code='springSecurity.login.title'/></title>

    <asset:stylesheet src="web_page/authorization/login/login.css"></asset:stylesheet>

</head>

<body>
<div id="login">
    <div class="inner">
        <div class="fheader"><g:message code='springSecurity.login.header'/></div>

        <g:if test='${flash.message}'>
            <div class="login_message">${flash.message}</div>
        </g:if>

        <form action="${postUrl ?: '/login/authenticate'}" method="POST" id="loginForm" class="cssform" autocomplete="off">
            <p id="username-wrapper">
                <% """ <label for="username">Your Email:</label>""" %>
                <input type="text" class="text_" name="${usernameParameter ?: 'username'}" value="username" id="username"/>
            </p>

            <p id="password-wrapper">
                <% """ <label for="password">Password:</label> """ %>
                <input type="text" class="text_" name="${passwordParameter ?: 'password'}" value="password" id="password"/>
            </p>

            <label for="remember_me" id="remember_me_holder">
                <g:message code='springSecurity.login.remember.me.label'/>
                <input type="checkbox" class="chk" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
                <span class = "checkmark"></span>
            </label>

            <div class="submit">
                <input type="submit" id="submit" value="Sign in"/>
            </div>
        </form>
        <div class="register">
            <form action = "/register" method="GET">
                <input type="submit" id="register" value="Sign up"/>
            </form>
        </div>
        <script>
            (function() {
                document.forms['loginForm'].elements['${usernameParameter ?: 'username'}'].focus();
            })();
        </script>
    </div>
</div>
</body>
</html>
