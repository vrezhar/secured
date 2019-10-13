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

<asset:stylesheet src="boomerang.css"></asset:stylesheet>
<link href="https://fonts.googleapis.com/css?family=Nunito:400,600,700,800|Roboto:400,500,700" rel="stylesheet">
<asset:stylesheet src="assets/vendor/swiper/css/swiper.min.css"></asset:stylesheet>
<asset:stylesheet src="assets/vendor/hamburgers/hamburgers.min.css" ></asset:stylesheet>
<asset:stylesheet src="assets/vendor/animate/animate.min.css" ></asset:stylesheet>
<asset:stylesheet src="assets/vendor/fancybox/css/jquery.fancybox.min.css"></asset:stylesheet>
<asset:stylesheet src="assets/fonts/font-awesome/css/font-awesome.min.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/ionicons/css/ionicons.min.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/line-icons/line-icons.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/line-icons-pro/line-icons-pro.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/linea/arrows/linea-icons.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/linea/basic/linea-icons.css" ></asset:stylesheet>
<asset:stylesheet src="/assets/fonts/linea/ecommerce/linea-icons.css" ></asset:stylesheet>
<asset:stylesheet src="assets/fonts/linea/software/linea-icons.css"></asset:stylesheet>

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
                                    <button type="submit" class="btn btn-styled btn-lg btn-block btn-base-1 mt-4">Sign in</button>
                                </form>
                            </div>
                        </div>

                        <!-- Form auxiliary links -->
                        <div class="form-user-footer-links pt-2">
                            <div class="row">
                                <div class="col-6">
                                    <g:link controller="register" action="register">Recover password</g:link>
                                </div>
                                <div class="col-6 text-right">
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

<asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
<asset:javascript src="popper.min.js"></asset:javascript>
<asset:javascript src="bootstrap.js"></asset:javascript>
<asset:javascript src="slidebar/slidebar.js"></asset:javascript>
<asset:javascript src="classie.js"></asset:javascript>
<asset:javascript src="assets/vendor/bootstrap-notify/bootstrap-growl.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/scrollpos-styler/scrollpos-styler.js"></asset:javascript>
<asset:javascript src="assets/vendor/adaptive-backgrounds/adaptive-backgrounds.js"></asset:javascript>
<asset:javascript src="assets/vendor/countdown/js/jquery.countdown.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/dropzone/dropzone.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/easy-pie-chart/jquery.easypiechart.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/fancybox/js/jquery.fancybox.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/flatpickr/flatpickr.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/flip/flip.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/footer-reveal/footer-reveal.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/gradientify/jquery.gradientify.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/headroom/headroom.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/headroom/jquery.headroom.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/input-mask/input-mask.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/instafeed/instafeed.js"></asset:javascript>
<asset:javascript src="assets/vendor/milestone-counter/jquery.countTo.js"></asset:javascript>
<asset:javascript src="assets/vendor/nouislider/js/nouislider.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/paraxify/paraxify.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/select2/js/select2.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/sticky-kit/sticky-kit.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/swiper/js/swiper.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/textarea-autosize/autosize.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/typeahead/typeahead.bundle.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/typed/typed.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/vide/vide.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/viewport-checker/viewportchecker.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/wow/wow.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/isotope/isotope.min.js"></asset:javascript>
<asset:javascript src="assets/vendor/imagesloaded/imagesloaded.pkgd.min.js"></asset:javascript>
<asset:javascript src="boomerang.js"></asset:javascript>
    <script>
    (function() {
        document.forms['form-default'].elements['${usernameParameter ?: 'username'}'].focus();
    })();
    </script>


    </body>
</html>