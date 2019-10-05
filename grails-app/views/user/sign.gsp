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
    <style type="text/css" media="screen">
    #create {
        margin: 15px 0px;
        padding: 0px;
        text-align: center;
    }

    body{
        background-image: linear-gradient(rgb(240,255,255),#f0f0fa);
    }

    #create .inner {
        width: 500px;
        height: 400px;
        padding-bottom: 6px;
        margin: 60px auto;
        text-align: left;
        border: 1px solid rgb(233, 248, 250);
        background-color: #f1effa;
        -moz-box-shadow: 2px 2px 2px #eee;
        -webkit-box-shadow: 2px 2px 2px #eee;
        -khtml-box-shadow: 2px 2px 2px #eee;
        box-shadow: 2px 2px 2px #eee;
    }
    #create .inner .cssform .visible{
        clear: left;
        margin: 0;
        padding: 4px 0 3px 0;
        padding-left: 80px;
        margin-bottom: 20px;
        height: 1%;
    }
    #create .inner .cssform .visible input[type="text"] {
        width:  350px;
        height: 50px;
        align-content: start;

    }
    #create .inner .cssform .visible .submit input[type="submit"]{
        margin-left: 0px%;
        margin-top: 25px;
    }

    #create .inner .cssform .visible .cancel input[type="submit"]{
        margin-left: 15%;
        margin-top: -32px;
        display: none;
        visibility: hidden;
    }


    #create .inner .cssform .visible ul{
        margin: 0;
        padding: 0;
        background-color: #f0f0fa;
        -moz-box-shadow: 2px 2px 2px #f0f0fa;
        -webkit-box-shadow: 2px 2px 2px #f0f0fa;
        -khtml-box-shadow: 2px 2px 2px #f0f0fa;
        border: #f0f0fa;
    }

    #create .inner .company p {
        width: 120px;
        clear: left;
        margin: 0;
        padding: 4px 0 3px 0;
        padding-left: 105px;
        margin-bottom: 10px;
        height: 1%;
    }


    #create .inner .company label {
        font-weight: initial;
        display: inline-block;
        width: 100px;
        text-align: right;
        float: left;
        margin-left: -105px;
        padding-top: 0px;
        padding-right: 10px;
    }

    #create .inner .company
    {
        display: none;
        visibility: hidden;
        margin-top: -25px;
    }
    #create .inner h1{
        margin-left: 30%;
        align-content: center;
        align-self: center;
        font-weight: bolder;
    }
    </style>
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
                <form id = "confirm" action="/user/company/confirm">
                    <input type="text" class="text_" name="" value="${signature?.body}" id="signature">
                    <div class = "submit">
                        <input type="submit" value="Confirm">
                    </div>
                </form>
                <g:eachError bean="${signature}" var="error">
                    <g:if test="${error?.field == "body"}">
                        <ul class = "errors" role="alert">
                            <li>
                                <g:message error="${error}"/>
                            </li>
                        </ul>
                    </g:if>
                </g:eachError>
                <div class="submit" id="initial" onclick="submit()">
                    <input type="submit" id="submit" value="Submit">
                </div>

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
            <div class="cancel" onclick="cancel()">
                <input type="submit" id="cancel" value="Cancel">
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
</body>
</html>