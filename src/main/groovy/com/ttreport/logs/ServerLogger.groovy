package com.ttreport.logs

import com.ttreport.auth.User
import com.ttreport.data.products.BarCode
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import grails.artefact.DomainClass
import grails.validation.Validateable

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import static java.nio.file.StandardOpenOption.*

class ServerLogger
{
    static List<String> action_logs = Collections.synchronizedList(new ArrayList<String>())
    final static String log_location = "/var/log/custom/server_logs.txt"

    static void list_all_companies()
    {
        println("printing companies:")
        Company.list().each{
            println("address: ${it.address}, id: ${it.companyId}")
        }
    }
    static void list_companies_of(User user)
    {
        println("printing companies of ${user.username}:")
        user.companies.each{
            println("address: ${it.address}, id: ${it.companyId}")
        }
    }
    static void list_all_products()
    {
        println("printing products:")
        Products.list().each{
            println("product code: ${it.id}, description: ${it.description}")
        }
    }
    static void list_products_of(Company company)
    {
        println("printing products of ${company.user}'s company with id ${company.companyId}")
        company.products.each{
            println("product code: ${it.id}, description: ${it.description}")
        }
    }
    static void list_all_barcodes()
    {
        BarCode.list().each{
            println("uitCode: ${it.uitCode}, uitu_code: ${it.uituCode}, belongs to: ${it.products.id} ")
        }
    }
    static void list_barcodes_of(Company company)
    {
        println("printing barcodes of ${company.user}'s company with id ${company.companyId}")
        company.products.each {
            it.barCodes.each {
                println("uitCode: ${it.uitCode}, uitu_code: ${it.uituCode}")
            }
        }
    }
    static void list_barcodes_of(Products products)
    {
        println("printing barcodes assigned to ${products.id}")
        products.barCodes.each{
            println("uitCode: ${it.uitCode}, uitu_code: ${it.uituCode}")
        }
    }
    static void log(String ...actions)
    {
        Path path = Paths.get(log_location)
        PrintWriter writer = null
        try {
            writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
        }
        catch (IOException ioException){
            action_logs.add("Failed to create printwriter, input-output exception")
            ioException.stackTrace.each {
                action_logs.add(it.toString())
            }
            try{
                writer.close()
            }
            catch (Exception ignored){}
        }
        finally{
            if(writer){
                writer.println("New log entry: [logged at ${new Date()}]")
            }
        }
        for(action in actions){
            action_logs.add(action)
            try {
                writer.println("\t" + action)
            }
            catch (IOException ioException){
                action_logs.add("Failed to write some logs to file, input-output exception")
                ioException.stackTrace.each {
                    action_logs.add(it.toString())
                }
            }
            finally{
                if(writer){
                    try{
                        writer.println("End of the new log entry")
                        writer.println("\n")
                        writer.flush()
                        writer.close()
                    }
                    catch (Exception ignored){}
                }
            }
        }
    }
    static void log_validation_errors(Validateable cmd, String additional_message = null, boolean writeToFile = true)
    {
        if(writeToFile){
            Path path = Paths.get(log_location)
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Showing validation errors of a command object of class ${cmd.class.simpleName}:[logged at ${new Date()}]")
                cmd.errors.fieldErrors.each{
                    action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                    writer.println("\t" + "value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                }
                if(additional_message){
                    action_logs.add(additional_message)
                    writer.println("\t" + additional_message)
                }
                return
            }
            catch (IOException ioException){
                action_logs.add("Failed to write some logs to file, input-output exception")
                ioException.stackTrace.each {
                    action_logs.add(it.toString())
                }
                return
            }
            finally{
                if(writer){
                    try{
                        writer.println("End of validation errors' logs")
                        writer.println("\n")
                        writer.flush()
                        writer.close()
                    }
                    catch (Exception ignored){return}
                }
            }
        }
        cmd.errors.fieldErrors.each {
            action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error message: ${it.code}")
        }
        if(additional_message) {
            action_logs.add(additional_message)
        }
    }
    static void log_validation_errors(DomainClass domain, String additional_message = null, boolean writeToFile = true)
    {
        if(writeToFile){
            Path path = Paths.get(log_location)
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Showing validation errors of a ${domain.class.simpleName} domain class object:[logged at ${new Date()}]")
                domain.errors.fieldErrors.each{
                    action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                    writer.println("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                }
                if(additional_message){
                    action_logs.add(additional_message)
                    writer.println(additional_message)
                }
                return
            }
            catch (IOException ioException){
                action_logs.add("Failed to write some logs to file, input-output exception")
                ioException.stackTrace.each {
                    action_logs.add(it.toString())
                }
                return
            }
            finally{
                if(writer){
                    try{
                        writer.println("End of validation errors' logs")
                        writer.println("\n")
                        writer.flush()
                        writer.close()
                    }
                    catch (Exception ignored){return}
                }
            }
        }
        domain.errors.fieldErrors.each {
            action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
        }
        if(additional_message) {
            action_logs.add(additional_message)
        }
    }

    static void log_exception(Throwable e, boolean writeToFile = true)
    {
        if(writeToFile){
            Path path = Paths.get(log_location)
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Exception was thrown, type ${e.class.simpleName}:[logged at ${new Date()}]")
                writer.println("Message:")
                writer.println("\t" + e.message)
                action_logs.add(e.message)
                writer.println("Stacktrace:")
                e?.stackTrace?.each{
                    action_logs.add(it.toString())
                    writer.println("\t" + it.toString())
                }
                return
            }
            catch (IOException ioException){
                action_logs.add("Failed to write some logs to file, input-output exception")
                ioException.stackTrace.each {
                    action_logs.add(it.toString())
                }
                return
            }
            finally{
                if(writer){
                    try{
                        writer.println("End of exception's logs")
                        writer.println("\n")
                        writer.flush()
                        writer.close()
                    }
                    catch (Exception ignored){return}
                }
            }
        }
        e.stackTrace.each {
            action_logs.add(it.toString())
        }
    }

    static void print_logs()
    {
        println("showing all registered logs:")
        synchronized (action_logs){
            for(Iterator<String> iterator = action_logs.iterator(); iterator.hasNext(); ) {
                println(iterator.next())
            }
        }
    }
    static void cleanup()
    {
        println("dumped previous ${action_logs.size()} records")
        action_logs.clear()
    }
}
