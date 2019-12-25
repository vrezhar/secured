package com.ttreport.auth

interface UserInitializer
{
    //def initialize(Map map)
    def assignRoleAndSave(User usr, Role role, boolean flush)
    def assignRoleAndSave(User usr, String r, boolean flush)
}