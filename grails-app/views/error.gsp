<!doctype html>
<html>
    <head>
        <title><g:if env="development">Grails Runtime Exception</g:if><g:else>Error 505</g:else></title>
        <meta name="layout" content="main">
        <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
    </head>
    <body>
        <g:if env="development">
            <g:if test="${Throwable.isInstance(exception)}">
                <g:renderException exception="${exception}" />
            </g:if>
            <g:elseif test="${request.getAttribute('javax.servlet.error.exception')}">
                <g:renderException exception="${request.getAttribute('javax.servlet.error.exception')}" />
            </g:elseif>
            <g:else>
                <ul class="errors">
                    <li>An error has occurred</li>
                    <li>Exception: ${exception}</li>
                    <li>Message: ${message}</li>
                    <li>Path: ${path}</li>
                </ul>
            </g:else>
        </g:if>
        <g:else>
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
        </g:else>
    </body>
</html>
