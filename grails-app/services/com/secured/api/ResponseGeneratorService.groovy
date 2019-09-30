package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.ShipmentDocumentCommand
import com.secured.api.response.Responsive
import grails.gorm.transactions.Transactional

@Transactional
class ResponseGeneratorService
{

    static scope = 'prototype'

}
