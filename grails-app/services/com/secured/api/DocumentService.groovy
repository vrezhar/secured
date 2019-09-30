package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.ShipmentDocumentCommand
import grails.gorm.transactions.Transactional

@Transactional
class DocumentService
{
    Map accept(AcceptanceDocumentCommand cmd)
    {

    }

    Map ship(ShipmentDocumentCommand cmd)
    {

    }
}
