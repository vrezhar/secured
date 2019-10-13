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

<asset:stylesheet src="web_page/authorization/login/inputs.css"></asset:stylesheet>
<asset:stylesheet src="web_page/authorization/login/errors.css"></asset:stylesheet>

    </head>

    <div class="body-wrap">
        <section class="slice sct-color-2">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-6">
                        <div class="card form-card form-card--style-2">
                            <div class="form-header text-center">
                                <div class="form-header-icon">
                                    <i class="icon ion-log-in"></i>
                                </div>
                            </div>
                            <div class="form-body">
                                <div class="text-center px-2">
                                    <h4 class="heading heading-4 strong-400 mb-4">Sign in to your account</h4>
                                </div>
                                <g:if test='${flash.message}'>
                                    <div class="login_message" id="login_message">${flash.message}</div>
                                </g:if>
                                <form class="form-default" role="form" action="/login/authenticate" method="POST">
                                    <div class="row ">
                                        <div class="col-12">
                                            <div class="form-group">
%{--                                                <label>Email</label>--}%
                                                <input type="email" class="text_" name="${usernameParameter ?: 'username'}" id="username" placeholder="Username"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="form-group has-feedback">
%{--                                                <label>Password</label>--}%
                                                <input type="password" class="text_" name="${passwordParameter ?: 'password'}" id="password" placeholder="Password"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="checkbox">
                                                <input type="checkbox" id="chkRemember" <g:if test='${hasCookie}'>checked="checked"</g:if>>
                                                <label for="chkRemember">Remember me</label>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-styled btn-lg btn-block btn-base-1 mt-1">Sign in</button>
                                </form>
                            </div>
                        </div>

                        <!-- Form auxiliary links -->
                        <div class="form-user-footer-links pt-2">
                            <div class="row">
%{--                                <div class="col-6">--}%
%{--                                    <g:link controller="register" action="register">Recover password</g:link>--}%
%{--                                </div>--}%
                              <div class="col-12 text-right"> %{-- change this to six if password recovery will be an option --}%
                                    <a href="/register" class="">Create a new account</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div><!-- END: body-wrap -->
<a href="#" class="back-to-top btn-back-to-top"></a>


    <script>
    (function() {
        document.forms['form-default'].elements['${usernameParameter ?: 'username'}'].focus();
    })();
    </script>


    </body>
</html>