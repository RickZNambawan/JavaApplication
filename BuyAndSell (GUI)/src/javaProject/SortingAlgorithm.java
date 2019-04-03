package javaProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;

public class SortingAlgorithm {

    private MyDatabase db = new MyDatabase();
    private Connection conn = db.connectDatabase();

    private void getDataFromDatabase(LinkedList<Accounts> unsortedItems) throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM itemsToSell");
        while(rs.next()) {
            String itemCategory = rs.getString("itemCategory");
            String itemName = rs.getString("itemName");
            int itemNumber = rs.getInt("itemNumber");
            int itemPrice = rs.getInt("itemPrice");
            int quantity = rs.getInt("quantity");
            String firstName = rs.getString("firstName");
            unsortedItems.add(new Accounts(itemCategory, itemName, itemNumber, itemPrice, quantity, firstName));
        }
    }

    protected LinkedList<Accounts> bucketSort() throws SQLException {
        LinkedList<Accounts> unsortedItems = new LinkedList<>();
        LinkedList<Accounts> sortedItems = new LinkedList<>();
        LinkedList<Integer> prices = new LinkedList<>();
        getDataFromDatabase(unsortedItems);

        unsortedItems.forEach((accounts) -> {prices.add(accounts.getItemPrice());});
        int largestElement = Collections.max(prices);
        int[] bucket = new int[largestElement + 1];

        for (int i = 0; i < bucket.length; i++)
            bucket[i] = 0;

        for (int j = 0; j < unsortedItems.size(); j++)
            bucket[unsortedItems.get(j).getItemPrice()]++;

        for (int i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i]; j++) {
                int currentPosition = 0;
                while(true) {
                    if(sortedItems.contains(unsortedItems.get(currentPosition))) {
                        currentPosition++;
                    } else if(unsortedItems.get(currentPosition).getItemPrice() != i) {
                        currentPosition++;
                    }  else {
                        sortedItems.add(unsortedItems.get(currentPosition));
                        break;
                    }
                }
            }
        }

        displaySortedData(unsortedItems, sortedItems, "BUCKET SORT");
        unsortedItems.clear();
        return sortedItems;
    }

    protected LinkedList<Accounts> heapSort() throws SQLException {
        LinkedList<Accounts> unsortedItems = new LinkedList<>();
        LinkedList<Accounts> sortedItems = new LinkedList<>();
        getDataFromDatabase(unsortedItems);

        int length = unsortedItems.size();
        int[] array = new int[length];

        // put unsorted items into array
        for(int i = 0; i < length; i++) {
            array[i] = unsortedItems.get(i).getQuantity();
        }

        // Build max heap
        for (int i = length / 2 - 1; i >= 0; i--) {
            // i = 2
            heapify(array, length, i);
        }


        // Heap sort
        for (int i= length - 1; i>=0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Heapify root element
            heapify(array, i, 0);
        }

        int currentPositionOfArray = 0;
        int counter = 0;
        while(currentPositionOfArray < length) {
            if (sortedItems.contains(unsortedItems.get(counter))) {
                counter++;
            }
            else
            if (unsortedItems.get(counter).getQuantity() == array[currentPositionOfArray]) {
                sortedItems.add(unsortedItems.get(counter));
                counter = 0;
                currentPositionOfArray++;
            } else {
                counter++;
            }
        }

        displaySortedData(unsortedItems, sortedItems, "HEAP SORT TOP-DOWN");
        array = null;
        unsortedItems.clear();
        return sortedItems;
    }

    protected void loadBucketSortData() throws SQLException {
        LinkedList<Accounts> sortedList = bucketSort();

        for(int position = 0; position < sortedList.size(); position++) {
            conn.createStatement().executeUpdate("INSERT INTO sortedItems (itemCategory, itemName, itemNumber, itemPrice, quantity, firstName) VALUES" +
                    "('" + sortedList.get(position).getItemCategory() + "'," + "'" + sortedList.get(position).getItemName() + "', " +
                    sortedList.get(position).getItemNumber() + ", '" + sortedList.get(position).getItemPrice()
                    + "'," + "'" + sortedList.get(position).getQuantity() + "'," + "'" + sortedList.get(position).getFirstName() + "'" + ")");
        }
    }

    protected void loadHeapSortData() throws SQLException {
        LinkedList<Accounts> sortedList = heapSort();

        for(int position = 0; position < sortedList.size(); position++) {
            conn.createStatement().executeUpdate("INSERT INTO sortedItems (itemCategory, itemName, itemNumber, itemPrice, quantity, firstName) VALUES" +
                    "('" + sortedList.get(position).getItemCategory() + "'," + "'" + sortedList.get(position).getItemName() + "', " +
                    sortedList.get(position).getItemNumber() + ", '" + sortedList.get(position).getItemPrice()
                    + "'," + "'" + sortedList.get(position).getQuantity() + "'," + "'" + sortedList.get(position).getFirstName() + "'" + ")");
        }
    }

    protected void heapify(int[] array, int n, int i) {
        // Find largest among root, left child and right child
        int largest = i; // 2
        int l = 2*i + 1; // 5
        int r = 2*i + 2; // 6

        // 5 < 6 && 10 > 9
        if (l < n && array[l] > array[largest])
            largest = l; // 5

        // 6 < 6 &&
        if (r < n && array[r] > array[largest])
            largest = r;

        // Swap and continue heapifying if root is not largest
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            heapify(array, n, largest);
        }
    }

    private void displaySortedData(LinkedList<Accounts> unsortedAccount, LinkedList<Accounts> sortedAccount, String message) {

        System.out.println("\n BEFORE SORTING USING " + message + " ......... \n");
        unsortedAccount.forEach((items) -> {
            System.out.print(items.getItemName() + "\t\t\t\t");
            System.out.print(items.getItemCategory() + "\t\t\t\t");
            System.out.print(items.getItemNumber() + "\t\t\t\t");
            System.out.print(items.getItemPrice() + "\t\t\t\t");
            System.out.print(items.getQuantity() + "\t\t\t\t");
            System.out.println(items.getFirstName());
        });

        System.out.println("\n AFTER SORTING USING " + message + " ......... \n");
        sortedAccount.forEach((items) -> {
            System.out.print(items.getItemName() + "\t\t\t\t");
            System.out.print(items.getItemCategory() + "\t\t\t\t");
            System.out.print(items.getItemNumber() + "\t\t\t\t");
            System.out.print(items.getItemPrice() + "\t\t\t\t");
            System.out.print(items.getQuantity() + "\t\t\t\t");
            System.out.println(items.getFirstName());
        });

        System.out.println("\n");
    }
}
