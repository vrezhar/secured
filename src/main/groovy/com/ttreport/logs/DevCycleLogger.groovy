package com.ttreport.logs

import com.ttreport.auth.User
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import grails.artefact.DomainClass
import grails.validation.Validateable

import java.nio.file.Files
import java.nio.file.OpenOption
import java.nio.file.Path
import java.nio.file.Paths
import static java.nio.file.StandardOpenOption.*

class DevCycleLogger
{
    static ArrayList<String> action_logs = new ArrayList<String>()

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
    static void log(String action, boolean writeToFile = true)
    {
        action_logs.add(action)
        if(writeToFile){
            Path path = Paths.get("logs.txt")
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println(action + ", logged at ${new Date()}")
                writer.flush()
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
            Path path = Paths.get("logs.txt")
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Showing validation errors of a command object of class ${cmd.class.simpleName}:")
                cmd.errors.fieldErrors.each{
                    action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                    writer.println("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}" + ", logged at ${new Date()}")
                }
                if(additional_message){
                    action_logs.add(additional_message)
                    writer.println(additional_message + ", logged at ${new Date()}")
                }
                writer.flush()
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
            Path path = Paths.get("logs.txt")
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Showing validation errors of a ${domain.class.simpleName} domain class object:")
                domain.errors.fieldErrors.each{
                    action_logs.add("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}")
                    writer.println("value ${it.rejectedValue} not validated for field ${it.field}, error code: ${it.code}" + ", logged at ${new Date()}")
                }
                if(additional_message){
                    action_logs.add(additional_message)
                    writer.println(additional_message + ", logged at ${new Date()}")
                }
                writer.flush()
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

    static void log_stack_trace(Throwable e, boolean writeToFile = true)
    {
        if(writeToFile){
            Path path = Paths.get("logs.txt")
            PrintWriter writer = null
            try {
                writer = new PrintWriter(new BufferedOutputStream(Files.newOutputStream(path,CREATE,APPEND)))
                writer.println("Stacktrace of the exception of type ${e.class.simpleName}:")
                e?.stackTrace?.each{
                    action_logs.add(it.toString())
                    writer.println(it.toString())
                }
                writer.flush()
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
        action_logs.each {
            println(it)
        }
    }
    static void cleanup()
    {
        println("dumped previous ${action_logs.size()} records")
        action_logs.clear()
    }
}
