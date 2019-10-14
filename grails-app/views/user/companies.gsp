<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 14.10.19
  Time: 22:44
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <title></title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
</head>

<body>
<div class="body-wrap" style="background: transparent;">
    <div id="st-container" class="st-container">

        <section class="slice sct-color-2">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-12">
                        <div class="card form-card form-card--style-2">
                            <div class="form-header text-center">
                                <div class="form-header-icon">
                                    <asset:image src="icons/custom/companies.png"></asset:image>
                                </div>
                                <div class="card-title">
                                    <div class="text-center px-2">
                                        <h1 class="heading heading-4 strong-400 mb-4">Your Companies</h1>
                                    </div>
                                </div>
                            </div>

                            <span class="space-md-md"></span>

                            <div class="card-body">

                                <span class="space-md-md"></span>

                                <div class="row">
                                    <div class = "companies" >
                                            <g:each var="company" in="${user.companies}">
                                                <div class="px-4 py-4">
                                                    <table class="c-gray-light">
                                                        <tr>
                                                            <td>
                                                                ${company.companyId}
                                                            </td>
                                                            <td>
                                                                ${company.token}
                                                            </td>
                                                            <td>
                                                                <a href="#">Show more</a>
                                                            </td>
                                                        </tr>
                                                        <tr>

                                                        </tr>
                                                    </table>

                                                </div>
                                            </g:each>
                                    </div>
                                </div>

                                <span class="space-lg-only-1"></span>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>