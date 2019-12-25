package com.ttreport

import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional
import grails.validation.Validateable
import org.springframework.context.MessageSource

@Transactional
class MessageBundleService
{

    private static final Map<String, Locale> langLocaleMappings = [
            'en': Locale.ENGLISH,
            'ru': new Locale('ru', 'RU'),
    ].asImmutable()

    MessageSource messageSource

    protected String getMessage(String message) {
        try{
            messageSource.getMessage(message, [].toArray(), langLocaleMappings.en)
        }
        catch (Exception ignored){
            return null
        }
    }

    protected int getCode(String message)
    {
        if(message == 'nullable'){
            return 413
        }
        String error
        try{
            error = getMessage(message)
        }
        catch (Exception ignored){
            return -1
        }
        int result = 0
        for(int i = 0; i < error.length(); ++i){
            result = result*10 + error[i].toInteger()
        }
        return result
    }

    protected int computeHighestPriorityError(Validateable cmd)
    {
        List<Integer> list = []
        cmd.errors.fieldErrors.each {
            list.add(getCode(it.code))
        }
        ServerLogger.log_validation_errors(cmd)
        int min = list[0]?: -1
        for(item in list){
            if(item < min){
                min = item
            }
        }
        return min
    }
}
