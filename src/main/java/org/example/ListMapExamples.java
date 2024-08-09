package org.example;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.policy.WritePolicy;

import java.util.Arrays;
import java.util.List;

public class ListMapExamples {
    private static final String LIST_BIN = "list";
    private static final IAerospikeClient client
            = new AerospikeClient("localhost", 3000);
    private static final Key key = new Key("test", "sample", 1);

    private static void show(Operation operation, String description) {
        Record record = client.operate(null, key, operation);
        System.out.println(description + ": " + record.getValue(LIST_BIN));
    }


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 4, 7, 3, 9, 26, 11);
        client.put(null, key, new Bin(LIST_BIN, list));
        show(ListOperation.getByIndex(LIST_BIN, 1, ListReturnType.VALUE),
                "Index of 1");
        show(ListOperation.getByRank(LIST_BIN, 1, ListReturnType.VALUE),
                "Rank of 1");
        show(ListOperation.getByValue(LIST_BIN, Value.get(1),
                ListReturnType.VALUE), "Value of 1");
        client.close();
    }
}