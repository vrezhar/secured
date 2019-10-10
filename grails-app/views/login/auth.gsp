<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 23.08.19
  Time: 04:25
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title><g:message code='springSecurity.login.title'/></title>
    <asset:stylesheet src="web_page/authorization/login.css"></asset:stylesheet>
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
                <label for="username">Your Email:</label>
                <input type="text" class="text_" name="${usernameParameter ?: 'username'}" id="username"/>
            </p>

            <p id="password-wrapper">
                <label for="password">Password:</label>
                <input type="password" class="text_" name="${passwordParameter ?: 'password'}" id="password"/>
            </p>

            <p id="remember_me_holder">
                <input type="checkbox" class="chk" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/>
                <label for="remember_me"><g:message code='springSecurity.login.remember.me.label'/></label>
            </p>

            <div class="submit">
                <input type="submit" id="submit" value="${message(code: 'springSecurity.login.button')}"/>
            </div>

        </form>

        <div class="register">
            <sec:ifNotLoggedIn>
                Don't have an account?
                <g:link controller="register" action="register">
                    Register Now!
                </g:link>
            </sec:ifNotLoggedIn>
        </div>

    </div>
</div>

<script>
    (function() {
        document.forms['loginForm'].elements['${usernameParameter ?: 'username'}'].focus();
    })();
</script>

</body>
</html>