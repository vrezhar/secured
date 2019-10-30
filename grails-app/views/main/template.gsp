<div class="row justify-content-center" id="create_wrapper" style="display: none; visibility: hidden">
    <div class="col-lg-12">
        <div class="card form-card form-card--style-2">
            <div class="form-header text-center">
                <div class="form-header-icon">
                    <asset:image src="icons/custom/signature_2.png"></asset:image>
                </div>
            </div>

            <span class="space-md-md"></span>
            <div class="card-body">
                <div class="card-title">
                    <div class="text-center px-2">
                        <h1 class="heading heading-4 strong-400 mb-4">Copy your signature here</h1>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <g:textArea type="text" class="form-control form-control-lg" name="signature" value="${signature?.body}" placeholder="Your signature..." id="signature"></g:textArea>
                    </div>
                </div>

                <div class="row" style="visibility: hidden; display: none;" id="loader">
                    <div class="spinner">

                    </div>
                    <label>Please  wait...</label>
                </div>

                <span class="space-md-md"></span>

                <div class="row">
                    <div class="company" id="company" style="visibility: hidden; display: none;">
                        <div class="card" style="margin-left: 5%; width: 200%">
                            <p class="c-gray-light" style="font-size: large">
                                <label for="companyId" style="font-size: large">Company Id: </label>
                                <b type="text" class="text_" id = "companyId"></b>
                            </p>
                            <p class="c-gray-light" style="font-size: large">
                                <label for="address" style="font-size: large">Address: </label>
                                <b type="text" class="text_" id="address" ></b>
                            </p>
                        </div>
                    </div>
                </div>

                <span class="space-lg-only-1"></span>

                <div class="row">
                    <div class="col-md-2" id="confirm" style="visibility: hidden; display: none;">
                        <input class="btn btn-styled btn-lg btn-circle btn-success mt-1" type="submit"  onclick="confirm()" value="Confirm">
                    </div>
                    <div class="col-md-2" id="cancel" style="visibility: hidden; display: none;">
                        <input class="btn btn-styled btn-lg btn-circle btn-danger mt-1" type="submit"  onclick="cancel()" value="Cancel">
                    </div>
                </div>

                <span class="space-lg-only-1"></span>

            </div>
        </div>
    </div>
</div>