<!doctype html>
<html>
    <head>
        <title>Page Not Found</title>
        <meta name="layout" content="main">
        <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
    </head>
    <body>
    <section class="home home-form-left" id="home">
        <!-- <div class="bg-overlay"></div> -->
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="home-wrapper">
                        <h1>
                            <span class="text-colored"><g:message code="website.error.404"></g:message></span>
                        </h1>
                        <h1>
                            <g:message code="website.error.404.message"></g:message>
                        </h1>
                        <div class="clearfix"></div>
                    </div><!-- home wrapper -->
                </div> <!-- end col -->
            </div> <!-- end row -->
        </div> <!-- end container -->
    </section>
    </body>
</html>
