<%--
  Created by IntelliJ IDEA.
  User: vrezh
  Date: 03.10.19
  Time: 20:01
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="layout" content="${gspLayout ?: 'main'}"/>
    <asset:javascript src="jquery-3.3.1.min.js"></asset:javascript>
    <asset:stylesheet src="web_page/signature/signature.css"/>
    <title> Register company </title>
</head>

<body>
<div class="wrapper" id="create" >
    <div class="inner">
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <h1>Copy your signature here</h1>
        <div id="signform" class="cssform">
            <div class="visible">
                <input type="text" class="text_" name="" value="${signature?.body}" id="signature">
                <g:eachError bean="${signature}" var="error">
                    <g:if test="${error?.field == "body"}">
                        <ul class = "errors" role="alert">
                            <li>
                                <g:message error="${error}"/>
                            </li>
                        </ul>
                    </g:if>
                </g:eachError>
            </div>

            <div class="loader" id="loader" style="visibility: hidden">
                <div class="spinner" >

                </div>
                <label>Please  wait...</label>
            </div>


            <div class="company" id="company">
                <p>
                    <label for="companyId">Company Id: </label>
                    <b type="text" class="text_" id = "companyId"></b>
                </p>
                <p>
                    <label for="address">Address: </label>
                    <b type="text" class="text_" id="address" ></b>
                </p>
            </div>
            <div class = "submits">
                    <input type="submit" id="confirm" onclick="confirm()" value="Confirm">
                    <input type="submit" id="cancel" onclick="cancel()" value="Cancel">
            </div>
        </div>

    </div>
</div>
<script>
    (function() {
        document.getElementById("signature").focus();
    })();
</script>
<asset:javascript src="signature/verify.js"></asset:javascript>
<asset:javascript src="signature/onInput.js"></asset:javascript>
<asset:javascript src="signature/loader.js"></asset:javascript>
</body>
</html>