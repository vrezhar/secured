package com.secured.auth

interface UserInitializer
{
    //def initialize(Map map)
    def assignRole(User usr,Role role,boolean flush)
    def assignRole(User usr,String r,boolean flush)
    def addToCoordinates(User usr,Map<String,String> coordinates,boolean save,boolean flush)
}