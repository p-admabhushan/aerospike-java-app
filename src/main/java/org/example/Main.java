package org.example;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.IAerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.policy.WritePolicy;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");




        IAerospikeClient client = new AerospikeClient(null, "127.0.0.1", 3000);
        Key key = new Key("test", "item", 1);

        WritePolicy writePolicy = new WritePolicy(client.getWritePolicyDefault());

//        writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
//        client.put(writePolicy, key, new Bin("name", "Stylish Couch"),
//                new Bin("cost", 50000), new Bin("discount", 0.21));


        client.put(null, key, new Bin("name", "Stylish Couch"),
                new Bin("cost", 50000), new Bin("discount", 0.21));
        Record item = client.get(null, key);
        System.out.printf("%s costs $%.02f with a %d%% discount\n",
                item.getString("name"), item.getInt("cost")/100.0,
                (int)(100*item.getFloat("discount")));
        client.close();
    }
}