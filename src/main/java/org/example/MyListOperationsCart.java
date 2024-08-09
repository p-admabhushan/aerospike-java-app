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

public class MyListOperationsCart {
    private static final IAerospikeClient client
            = new AerospikeClient("localhost", 3000);
    private static final Key key = new Key("test", "cart", 1);

    public static void main(String[] args) {
        System.out.println("Hello World !");

        client.delete(null, key);
        addItem(client, key, createItem("shoes", 59.25, "/items/item1234"));
        addItem(client, key, createItem("jeans", 29.95, "/items/item2378"));
        addItem(client, key, createItem("shirt", 19.95, "/items/item88293"));

        int index = 1;
        Record record = client.operate(null, key,
                ListOperation.removeByIndex("items", index, ListReturnType.NONE),
                ListOperation.size("items"));

//        Record record = client.operate(null, key,
//        ListOperation.removeByIndexRange("items", index, 2, ListReturnType.NONE),
//                ListOperation.size("items"));

        Integer remaining = record.getInt("items");
        System.out.println("remaining items : "+remaining);

//        int index = 0;
//        Record record = client.operate(null, key,
//                ListOperation.removeByIndexRange("items", index, 2,
//                         ListReturnType.INVERTED));
//        int removed = record.getInt("items");
//
//        System.out.println("removed item : "+removed);


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
