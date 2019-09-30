package com.secured.api.resources

import grails.validation.Validateable

class DocumentCommand
{
    List<ProductCommand> products
    String document_date
    String transfer_date
    String turnover_type
    String request_type
    String document_number
}
