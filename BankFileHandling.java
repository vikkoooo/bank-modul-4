package lunvik8;

import java.util.*;
import java.io.*;

/**
 * Class that handles all IO operations for bank. Part of D0018D, assignment 4.
 * This class is an extension of BankLogic. This is because we have to reach the
 * customer list.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-03
 * 
 */

public class BankFileHandling extends BankLogic
{
	/**
	 * Clear all customers from the bank. This method is used if we manually want
	 * to, but also every time we load customers because otherwise we end up with
	 * duplicates.
	 * 
	 * To clear everything, the only thing we need to do is empty the customerList
	 * list in our current object we are running.
	 */
	public void clear()
	{
		GUI.bank.getCustomerList().clear();
	}

	/**
	 * Save all customers and their accounts and transactions to a .dat file. Class
	 * variable, currentAccountId, that keeps track of which is the latest account
	 * number created, is saved manually. It is always saved secondly in the list.
	 * This means it has to be read the same way.
	 * 
	 * 1. Save int (how many customers do we export?) 2. Save class variable
	 * currentAccountId 3. Save the customers
	 * 
	 * @param fileName, name of file to save
	 * @throws FileNotFoundException, IOException
	 */
	public void exportCustomers(String fileName) throws FileNotFoundException, IOException
	{
		String filePath = "lunvik8_files/";
		String fileType = ".dat";

		// Create stream
		FileOutputStream fileOutputStream_out = new FileOutputStream(filePath + fileName + fileType);
		ObjectOutputStream objectOutputStream_out = new ObjectOutputStream(fileOutputStream_out);

		// Get the list of customers for our bank object
		List<Customer> customerList = GUI.bank.getCustomerList();

		// How many customers to write
		objectOutputStream_out.writeInt(customerList.size());

		// Get current account id from Account class and write that
		objectOutputStream_out.writeInt(Account.getCurrentAccountId());

		// Write all customers
		for (Customer e : customerList)
		{
			objectOutputStream_out.writeObject(e);
		}
		objectOutputStream_out.close();
	}

	/**
	 * Load all customers and their accounts and transactions to the live bank.
	 * Class variable, currentAccountId, that keeps track of which is the latest
	 * account number created is loaded manually. It is always saved secondly, so we
	 * need to load it in the same order.
	 * 
	 * 1. Load int (how many customers do we load?) 2. Load class variable and set
	 * it in Account class. 3. Load the customers
	 * 
	 * @param fileName, name of file to load
	 * @throws FileNotFoundException, IOException, ClassNotFoundException
	 */
	public void importCustomers(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		String path = "lunvik8_files/";
		String type = ".dat";

		// Create stream
		FileInputStream fileInputStream_in = new FileInputStream(path + fileName + type);
		ObjectInputStream objectInputStream_in = new ObjectInputStream(fileInputStream_in);

		// Get the list of customers for our bank object
		List<Customer> customerList = GUI.bank.getCustomerList();

		// Clear bank before we start loading
		clear();

		// How many customers to load?
		int size = objectInputStream_in.readInt();

		// What was the state of currentAccountId when we exported?
		int currentAccountId = objectInputStream_in.readInt();
		Account.setCurrentAccountId(currentAccountId);

		// Load all customers and add to customerList
		for (int i = 0; i < size; i++)
		{
			Customer nextObject = (Customer) objectInputStream_in.readObject();
			customerList.add(nextObject);
		}
		objectInputStream_in.close();
	}

	/**
	 * Method that writes transactions to .txt file for the customer and account
	 * specified by the user.
	 * 
	 * @param pNo       to export
	 * @param accountId to export
	 * @param fileName, name of export file
	 * @throws IOException 
	 */
	public void exportTransactions(String pNo, int accountId, String fileName) throws IOException
	{
		String path = "lunvik8_files/";
		String type = ".txt";

		// Get the transactions to write from getTransactions method
		ArrayList<String> transactions = getTransactions(pNo, accountId);

		// Create writer
		FileWriter fw = new FileWriter(path + fileName + type);
		BufferedWriter bw = new BufferedWriter(fw);

		// Write transactions list
		for (String e : transactions)
		{
			bw.write(e);
			bw.newLine();
		}
		// Close writer
		bw.close();
	}

}
