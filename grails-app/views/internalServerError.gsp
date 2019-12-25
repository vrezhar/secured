<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 04.12.19
  Time: 22:30
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>Internal Server Error</title>
</head>

<body>
<section class="home home-form-left" id="home">
    <!-- <div class="bg-overlay"></div> -->
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="home-wrapper">
                    <h1>
                        <span class="text-colored"><g:message code="website.error.505"></g:message></span>
                    </h1>
                    <h1>
                        <g:message code="website.error.505.message"></g:message>
                    </h1>
                    <div class="clearfix"></div>
                </div><!-- home wrapper -->
            </div> <!-- end col -->
        </div> <!-- end row -->
    </div> <!-- end container -->
</section>
</body>
</html>