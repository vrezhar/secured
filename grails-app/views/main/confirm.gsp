<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 29.08.19
  Time: 17:51
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <title>
        Success!
    </title>
</head>

<body>
<div class="body-wrap">
    <section class="slice sct-color-2">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="card form-card form-card--style-2">
                        <div class="form-header text-center">
                            <div class="form-header-icon">
                                <asset:image src="icons/custom/success.png"></asset:image>
                                <h1 style="font-weight: bolder; color: #000000;">You have successfully registered!</h1>
                            </div>
                        </div>
                        <div class="form-body">
                            <div class="row">
                                <h2>Please verify your email account before proceeding.</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div><!-- END: body-wrap -->
<a href="#" class="back-to-top btn-back-to-top"><</a>

</body>
</html>