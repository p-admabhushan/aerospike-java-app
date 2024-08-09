package org.example;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.CTX;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.cdt.MapOperation;
import com.aerospike.client.cdt.MapPolicy;
import com.aerospike.client.policy.WritePolicy;

import java.util.HashMap;
import java.util.Map;

public class MyMapOperationsCart {
    private static final IAerospikeClient client
            = new AerospikeClient("localhost", 3000);
    private static final Key key = new Key("test", "cart", 1);

    public static void main(String[] args) {
        System.out.println("Hello World !");

        client.delete(null, key);
        addItem(client, key, createItem("shoes", 59.25, "/items/item1234"));
        addItem(client, key, createItem("jeans", 29.95, "/items/item2378"));
        addItem(client, key, createItem("shirt", 19.95, "/items/item88293"));


        // MapOperation to update cost of zeroth index item (i.e. shoes) to 49 from existing value..
        client.operate(null, key,
                MapOperation.put(MapPolicy.Default, "items", Value.get("cost"),
                        Value.get(49), CTX.listIndex(0)));


        client.close();
    }


    public static Map<String, Object> createItem(String itemDescr,
                                                 double cost, String originalItem) {
        Map<String, Object> result = new HashMap<>();
        result.put("cost", cost);
        result.put("descr", itemDescr);
        result.put("orig", originalItem);
        return result;
    }

    public static void addItem(IAerospikeClient client, Key key,
                               Map<String, Object> item) {
        client.operate(null, key, ListOperation.append("items", Value.get(item)));
    }
}
