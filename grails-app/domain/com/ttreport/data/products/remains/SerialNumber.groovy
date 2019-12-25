package com.ttreport.data.products.remains

import com.ttreport.data.products.BarCode
import org.apache.commons.lang.math.RandomUtils

class SerialNumber
{
    private static final transient String lowercases = "asdfghjklpoiuytrewqzxcvbnm"
    private static final transient String uppercases = lowercases.toUpperCase()

    String serialNumber
    boolean selfGenerated

    static SerialNumber generateRandomSerialNumber()
    {
        SerialNumber serial = new SerialNumber(selfGenerated: true)
        StringBuilder sb = new StringBuilder()
        String uuid = UUID.randomUUID().toString()

        for (int i = 0;; i++) {
            if(sb.length() == 13){
                break
            }
            if(i == 35){
                uuid = UUID.randomUUID().toString()
            }
            if(uuid[i] == '-') {
                continue
            }
            int random = RandomUtils.nextInt(100)
            if(random <= 10){
                continue
            }
            if(random > 10 && random <= 20){
                sb.append(lowercases[RandomUtils.nextInt(26)])
                continue
            }
            if(random > 70 && random <= 90){
                sb.append(uppercases[RandomUtils.nextInt(26)])
                continue
            }
            if(random > 90){
                sb.append(RandomUtils.nextInt(9))
                continue
            }
            sb.append(uuid[i])
        }
        serial.serialNumber = sb.toString()
        return serial
    }

    static belongsTo = [orderUnit: ProductOrderUnit]

    static constraints = {
        serialNumber nullable: true, blank: true
    }
}
