package org.example;

import com.aerospike.client.*;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.policy.WritePolicy;

import java.util.concurrent.TimeUnit;


public class MyFilterExpressions {

    private static final IAerospikeClient client
            = new AerospikeClient("localhost", 3000);
    private static final Key key = new Key("test", "cart", 1);


    public static void main(String[] args) {

// May not work but for illustration purpose..

        WritePolicy wp = new WritePolicy(client.getWritePolicyDefault());
        wp.filterExp =
                Exp.build(Exp.eq(Exp.stringBin("state"), Exp.val("PROCESSING")));
        client.put(wp, key, new Bin("state", "COMPLETED"));



        //The expressions have a handy sinceUpdate() expression you can use to get the time since the record was updated
        // in milliseconds. You can use Java’s TimeUnits to get the number of milliseconds in a day,
        // so you would end up with:
        Exp.and(
                Exp.eq(Exp.stringBin("state"), Exp.val("PROCESSING")),
                Exp.le(Exp.sinceUpdate(), Exp.val(TimeUnit.DAYS.toMillis(1)))
        );




    }




}
