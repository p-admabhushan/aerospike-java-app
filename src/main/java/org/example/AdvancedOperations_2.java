package org.example;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.IAerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.Operation;


public class AdvancedOperations_2 {

    public static void main(String[] args) {
        System.out.println("Hello World !!!");
        IAerospikeClient client = new AerospikeClient(null, "127.0.0.1", 3000);
        Key key = new Key("test", "cart", 1);
        client.delete(null, key);
        addItem(key, "shoes", 59.25);
        addItem(key, "jeans", 29.95);
        addItem(key, "shirt", 19.95);

        client.close();

    }


    public static void addItem(Key key, String itemDescr, double cost) {
        IAerospikeClient client = new AerospikeClient(null, "127.0.0.1", 3000);

        //public Record operate(WritePolicy policy, Key key, Operation ... operations);

        Record record = client.operate(null, key,
                Operation.get("cost"),
                Operation.append(new Bin("items", itemDescr+",")),
                Operation.add(new Bin("totalItems", 1)),
                Operation.add(new Bin("cost", cost)),
                Operation.get("cost")
        );
        System.out.println(record);

        client.close();
    }
}
