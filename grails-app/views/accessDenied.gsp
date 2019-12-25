<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 06.12.19
  Time: 16:07
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>Access Denied</title>
</head>

<body>
<section class="home home-form-left" id="home">
    <!-- <div class="bg-overlay"></div> -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="home-wrapper">
                    <h1>
                        <span class="text-colored"><g:message code="website.error.403"></g:message></span>
                    </h1>
                    <h1>
                        <g:message code="website.error.403.message"></g:message>
                    </h1>
                    <div class="clearfix"></div>
                </div><!-- home wrapper -->
            </div> <!-- end col -->
        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
</body>
</html>