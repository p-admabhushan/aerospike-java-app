package org.example;

import com.aerospike.client.*;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.Record;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.ExpOperation;
import com.aerospike.client.exp.ExpReadFlags;
import com.aerospike.client.exp.ListExp;

import java.util.HashMap;
import java.util.Map;

public class MyReadExpression {

    private static final IAerospikeClient client
            = new AerospikeClient("localhost", 3000);
    private static final Key key = new Key("test", "cart", 1);

    public static void main(String[] args) {
        System.out.println("Hello world !");

        client.delete(null, key);

        addItem(client, key, createItem("shoes", 59.25, "/items/item1234"));
        addItem(client, key, createItem("jeans", 29.95, "/items/item2378"));
        addItem(client, key, createItem("shirt", 19.95, "/items/item88293"));

    }

    public static void addItem(IAerospikeClient client, Key key,
                               Map<String, Object> item) {

        client.operate(null, key, ListOperation.append("items", Value.get(item)),
                Operation.add(new Bin("total", (double)item.get("cost"))));
    }

    public static Map<String, Object> createItem(String itemDescr,
                                                 double cost, String originalItem) {
        Map<String, Object> result = new HashMap<>();
        result.put("cost", cost);
        result.put("descr", itemDescr);
        result.put("orig", originalItem);
        return result;
    }

    public static double getAverageCost(IAerospikeClient client, Key key) {
        Record record = client.operate(null, key,
                ExpOperation.read("avg", Exp.build(
                        Exp.div(
                                Exp.floatBin("total"),
                                Exp.toFloat(ListExp.size(Exp.listBin("items")))
                        )
                ), ExpReadFlags.DEFAULT));
        return record.getDouble("avg");
    }
}


