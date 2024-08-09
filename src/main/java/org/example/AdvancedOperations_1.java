package org.example;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.IAerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Bin;
import com.aerospike.client.Operation;


public class AdvancedOperations_1 {
    private static final IAerospikeClient client = new AerospikeClient(null, "127.0.0.1", 3000);
    private static final Key key = new Key("test", "cart", 1);
    private static final Key key2 = new Key("test", "cart", 1);

    public static void main(String[] args) {
        System.out.println("Hello World !!!");
        //IAerospikeClient client = new AerospikeClient(null, "127.0.0.1", 3000);


        client.put(null, key,
                new Bin("items", "shoes,"),
                new Bin("totalItems", 1),
                new Bin("cost", 59.25));



        addItem(key, "jeans", 29.95);
        addItem(key, "shirt", 19.95);

       // client = new AerospikeClient("172.17.0.2", 3000);

        client.delete(null, key2);
        addItem(key2, "shoes", 59.25);
        addItem(key2, "jeans", 29.95);
        addItem(key2, "shirt", 19.95);

        client.close();

    }

    public static void addItem(Key key, String itemDescr, double cost) {


        //public Record operate(WritePolicy policy, Key key, Operation ... operations);

        client.operate(null, key,
                Operation.append(new Bin("items", itemDescr + ",")),
                Operation.add(new Bin("totalItems", 1)),
                Operation.add(new Bin("cost", cost))
        );
       // client.close();
    }

}
