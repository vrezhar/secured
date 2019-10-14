<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 14.10.19
  Time: 21:22
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
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
                                    <asset:image src="icons/custom/signature_2.png"></asset:image>
                                </div>
                            </div>
                            <div class="card-title">
                                <div class="text-center px-2">
                                    <h4 class="heading heading-4 strong-400 mb-4">Copy your signature here</h4>
                                </div>
                            </div>
                            <span class="space-md-md"></span>
                            <div class="card-body">

%{--                                <span class="space-lg-only-1"></span>--}%

                                <div class="row">
                                    <div class="col-md-12">
                                        <g:textField type="text" class="form-control form-control-lg" name="signature" value="${signature?.body}" placeholder="Your signature..." id="signature"></g:textField>
                                    </div>
                                </div>

                                <div class="row" style="visibility: hidden; display: none;" id="loader">
                                    <div class="spinner">

                                    </div>
                                    <label>Please  wait...</label>
                                </div>

                                <div class="row">
                                    <div class="company" id="company" style="visibility: hidden; display: none;">
                                        <p style="font-size: large">
                                            <label for="companyId" style="font-size: large">Company Id: </label>
                                            <b type="text" class="text_" id = "companyId"></b>
                                        </p>
                                        <p style="font-size: large">
                                            <label for="address" style="font-size: large">Address: </label>
                                            <b type="text" class="text_" id="address" ></b>
                                        </p>
                                    </div>
                                </div>

                                <span class="space-lg-only-1"></span>

                                <div class="row">
                                    <div class="col-sm-2" id="confirm" style="visibility: hidden; display: none;margin-right: 2%; ">
                                        <input class="btn btn-styled btn-lg btn-block btn-success mt-1" type="submit"  onclick="confirm()" value="Confirm">
                                    </div>
                                    <div class="col-sm-2" id="cancel" style="visibility: hidden; display: none;">
                                        <input class="btn btn-styled btn-lg btn-block btn-danger mt-1" type="submit"  onclick="cancel()" value="Cancel">
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
<asset:javascript src="signature/verify.js"></asset:javascript>
<asset:javascript src="signature/onInput.js"></asset:javascript>
<asset:javascript src="signature/loader.js"></asset:javascript>
</body>
</html>