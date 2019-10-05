package general

import spock.lang.Specification

class Test extends Specification
{
    def setup(){

    }
    def cleanup()
    {

    }
    void "general_test"()
    {
        String str = ""
        expect:
        !str  == (str != null && str != "")
    }
}
