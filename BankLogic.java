package lunvik8;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank utility methods. Part of D0018D, assignment 4.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-01
 */

public class BankLogic
{
	/**
	 * ArrayList of type Customer to keep track of customers. Customers are created
	 * through createCustomer method and stored here.
	 */
	private List<Customer> customerList = new ArrayList<>();

	/**
	 * A method to find a customer in myCustomerArrayList.
	 * 
	 * @param pNo (Personal number)
	 * @return Customer object if found, null if no customer was found.
	 */
	private Customer findCustomer(String pNo)
	{
		// Loop through all elements in myCustomerArrayList (i.e, check all customers).
		for (int i = 0; i < customerList.size(); i++)
		{
			// Store every customer in temporary variable of Customer type.
			Customer tmpCustomer = customerList.get(i);
			// Check whether pNo from method input equals pNo in Customer object.
			if (tmpCustomer.getpNo().equals(pNo))
			{
				// We found the customer, return the object.
				return tmpCustomer;
			}
		}
		// If we didn't find anything, return null.
		return null;
	}

	/**
	 * Creates a Customer object and stores in myCustomerArrayList. Checks whether
	 * the customer is already active.
	 * 
	 * @param name    (First name)
	 * @param surname (Last name)
	 * @param pNo     (Personal number, yymmddxxxx)
	 * @return true if customer was created. false if customer was already active
	 */
	public boolean createCustomer(String name, String surname, String pNo)
	{
		// Try to find already existing customer through findCustomer method
		Customer tmpCustomer = findCustomer(pNo);
		// If we do find the customer, customer already exists.
		if (tmpCustomer != null)
		{
			// Return false end exit.
			return false;
		}
		else
		{
			// If we reach this point, customer does not exist in our list yet.
			// Create a new Customer of type Customer with information from method input.
			Customer newCustomer = new Customer(name, surname, pNo);
			// Add to the list of Customers at next position.
			customerList.add(newCustomer);
			// Customer successfully added. Return true and exit.
			return true;
		}
	}

	/**
	 * It gets information about all our current customers in our bank.
	 * 
	 * @return ArrayList of type String with one customer per element.
	 */
	public ArrayList<String> getAllCustomers()
	{
		// Create temporary list that we later return.
		ArrayList<String> tmpCustomerList = new ArrayList<String>();
		// Loops through all elements in myCustomerArrayList (i.e, all customers).
		for (Customer i : customerList)
		{
			// For every customer, use toString method and store in our temporary list.
			tmpCustomerList.add(i.toStringCustomer());
		}
		// Return the list.
		return tmpCustomerList;
	}

	/**
	 * Searches for a customer in list and returns information about the customer
	 * including it's accounts. This is reached through the list of accounts
	 * associated with the customer.
	 * 
	 * @param pNo of the person we want to retrieve information about.
	 * @return ArrayList of String type or null if no customer is found.
	 */
	public ArrayList<String> getCustomer(String pNo)
	{
		// Create a temporary list.
		ArrayList<String> tmpCustomerList = new ArrayList<String>();
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Use toString method on customer and add to our tmp list.
			// Add information about customer on first slot.
			tmpCustomerList.add(tmpCustomer.toStringCustomer());
			// Continue with information about the accounts on following slots.
			// We must loop through the account list to fetch information about each slot
			for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
			{
				// Add to the list.
				tmpCustomerList.add(tmpCustomer.getAccountsList().get(i).toStringWithRate());
			}
			// Return the list.
			return tmpCustomerList;
		}
		else
		{
			// If no customer is found, return null.
			return null;
		}
	}

	/**
	 * Searches for a customer personal number, pNo. Changes name of the customer if
	 * the customer is found. Input cannot be empty string.
	 * 
	 * @param name    (First name)
	 * @param surname (Last name)
	 * @param pNo     (Personal number, yymmddxxxx)
	 * @return true, if name was successfully changed. false if customer not found.
	 */
	public boolean changeCustomerName(String name, String surname, String pNo)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Changes name of the person, as long as the name input was not empty.
			if (!name.isEmpty())
			{
				tmpCustomer.setName(name);
			}
			// Changes the surname of the person, as long as the name input was not empty.
			if (!surname.isEmpty())
			{
				tmpCustomer.setSurname(surname);
			}
			// We found the person, so return true.
			return true;
		}
		else
		{
			// We didn't find the person, return false.
			return false;
		}
	}

	/**
	 * Creates a new instance of SavingsAccount (i.e, creates an account for the
	 * person). We are using pNo to find the correct person to create the account
	 * for. The account is added to the customers accounts list.
	 * 
	 * @param pNo (Personal number)
	 * @return AccountId of the new account that was created, or -1 if no account
	 *         was created.
	 */
	public int createSavingsAccount(String pNo)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Create new SavingsAccount
			Account tmpAcc = new SavingsAccount();
			// Add our freshly created account to customers account list.
			tmpCustomer.getAccountsList().add(tmpAcc);
			// Get account number for the newly created account and return it.
			return (tmpAcc.getAccountId());
		}
		else
		{
			// If we didn't find the customer and no account was created, return -1.
			return -1;
		}
	}

	/**
	 * Create a new CreditAccount for the customer. We are using pNo as input to
	 * find the correct person and then add the account to the customers account
	 * list.
	 * 
	 * @param pNo (Personal number)
	 * @return AccountId of the new account that was created, or -1 if no account
	 *         was created.
	 */
	public int createCreditAccount(String pNo)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Create new CreditAccount
			Account tmpAcc = new CreditAccount();
			// Add our freshly created account to customers account list.
			tmpCustomer.getAccountsList().add(tmpAcc);
			// Get account number for the newly created account and return it.
			return (tmpAcc.getAccountId());
		}
		else
		{
			// If we didn't find the customer and no account was created, return -1.
			return -1;
		}
	}

	/**
	 * Searches for a specific account for a specific customer and returns
	 * information about the account.
	 * 
	 * @param pNo       (Personal number)
	 * @param accountId (Account Id)
	 * @return String with information about account if found. Returns null if no
	 *         account was found
	 */
	public String getAccount(String pNo, int accountId)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer: do
		if (tmpCustomer != null)
		{
			// Loop through all accounts in the array associated with the current person.
			for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
			{
				// Check whether the current accountId equals the one we are searching for.
				int currentAccountId = tmpCustomer.getAccountsList().get(i).getAccountId();
				if (currentAccountId == accountId)
				{
					// If it does, return a toString version of the account.
					return tmpCustomer.getAccountsList().get(i).toStringWithRate();
				}
			}
			// If we didn't find the account, return null.
			return null;
		}
		else
		{
			// If we didn't find the customer, return null.
			return null;
		}
	}

	/**
	 * Deposits an amount into a customers account as long as amount is positive.
	 * 
	 * @param pNo       (Personal number)
	 * @param accountId (Account id we want to deposit into)
	 * @param amount    (Amount to deposit)
	 * @return True if successful, false if account not found or amount not > 0.
	 */
	public boolean deposit(String pNo, int accountId, double amount)
	{
		if (amount > 0)
		{
			// Find the customer using findCustomer method.
			Customer tmpCustomer = findCustomer(pNo);
			// If we did find the customer
			if (tmpCustomer != null)
			{
				// Loop through all accounts in the array associated with the current person.
				for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
				{
					// Check whether the current accountId equals the one we are searching for.
					int currentAccountId = tmpCustomer.getAccountsList().get(i).getAccountId();
					if (currentAccountId == accountId)
					{
						// If it does, we found the correct account.
						// Deposit into the account and return true.
						tmpCustomer.getAccountsList().get(i).deposit(amount);
						return true;
					}
				}
			}
		}
		// If we didn't find it or amount was not > 0, return false.
		return false;
	}

	/**
	 * Attempts to make a withdrawal from an account.
	 * 
	 * @param pNo       (Personal number)
	 * @param accountId (Account Id of the account to withdrawal from)
	 * @param amount    (Amount to withdrawal)
	 * @return True if successful, false if account not found or withdrawal
	 *         conditions not met.
	 */
	public boolean withdraw(String pNo, int accountId, double amount)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Loop through all accounts in the array associated with the current person.
			for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
			{
				// Check whether the current accountId equals the one we are searching for.
				int currentAccountId = tmpCustomer.getAccountsList().get(i).getAccountId();
				if (currentAccountId == accountId)
				{
					// If it does, we found the correct account.
					// Attempt to withdrawal from the account. Withdrawal method returns true if it
					// is successful. Therefore, our condition has to be to return true also if
					// successful.
					if (tmpCustomer.getAccountsList().get(i).withdrawal(amount))
					{
						return true;
					}
					else
					{
						// Attempt failed, return false.
						return false;
					}
				}
			}
		}
		// If we didn't find the customer or the account, return false.
		return false;
	}

	/**
	 * Closes a specific account owned by the customer. It calculates and shows rate
	 * when closing the account.
	 * 
	 * @param pNo       (Personal number)
	 * @param accountId (Account Id we want to close)
	 * @return Information about the account including calculated rate.
	 */
	public String closeAccount(String pNo, int accountId)
	{
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Loop through all accounts in the array associated with the current person.
			for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
			{
				// Check whether the current accountId equals the one we are searching for.
				int currentAccountId = tmpCustomer.getAccountsList().get(i).getAccountId();
				if (currentAccountId == accountId)
				{
					// If it does, we found the correct account.
					// Add information about the account + the calculated interest as String
					String s = tmpCustomer.getAccountsList().get(i).toStringWithoutRate()
							+ tmpCustomer.getAccountsList().get(i).calculateInterest() + " kr";
					// Permanently close the account.
					tmpCustomer.getAccountsList().remove(i);
					// Return the String.
					return (s);
				}
			}
		}
		// Returns null if no account was found.
		return null;
	}

	/**
	 * Closes all accounts of a customer and removes the customer from the bank.
	 * 
	 * @param pNo (Personal number)
	 * @return Information about the customer and the closed accounts, including
	 *         rate.
	 */
	public ArrayList<String> deleteCustomer(String pNo)
	{
		// Create temporary list that we later return.
		ArrayList<String> tmpCustomerList = new ArrayList<String>();
		// Loop through all elements in myCustomerArrayList (i.e, check all customers).
		// We have to do this manually because we need to find the correct index
		// in order to be able to delete it later on.
		for (int i = 0; i < customerList.size(); i++)
		{
			// Store every customer in temporary variable of Customer type.
			Customer tmpCustomer = customerList.get(i);
			// Check whether pNo from method input equals pNo in Customer object.
			if (tmpCustomer.getpNo().equals(pNo))
			{
				// Now we have found the correct person.
				// Add information about the customer to the list.
				tmpCustomerList.add(tmpCustomer.toStringCustomer());
				// Loop through all the accounts.
				// No incrementer on j since we delete the account each time in the loop,
				// so index shiftes every time.
				for (int j = 0; j < tmpCustomer.getAccountsList().size();)
				{
					// Add information about each account including interest to the list.
					tmpCustomerList.add(tmpCustomer.getAccountsList().get(j).toStringWithoutRate()
							+ tmpCustomer.getAccountsList().get(j).calculateInterest() + " kr");
					// Remove the account.
					tmpCustomer.getAccountsList().remove(j);
				}
				// Remove the customer.
				customerList.remove(i);
				// Return the information.
				return tmpCustomerList;
			}
		}
		// If nothing was done, return null.
		return null;
	}

	/**
	 * Gets a list of all transactions for a specific account
	 * 
	 * @param pNo       (Personal number)
	 * @param accountId (of the account we want to find transactions for)
	 * @return the list of transactions if successful, null if no transactions found
	 */
	public ArrayList<String> getTransactions(String pNo, int accountId)
	{
		// Create a list to copy over our current transactions into
		ArrayList<String> transactions = new ArrayList<>();
		// Find the customer using findCustomer method.
		Customer tmpCustomer = findCustomer(pNo);
		// If we did find the customer
		if (tmpCustomer != null)
		{
			// Loop through all accounts in the array associated with the current person.
			for (int i = 0; i < tmpCustomer.getAccountsList().size(); i++)
			{
				// Check whether the current accountId equals the one we are searching for.
				int currentAccountId = tmpCustomer.getAccountsList().get(i).getAccountId();
				if (currentAccountId == accountId)
				{
					// If it does, we found the correct account.
					for (int j = 0; j < tmpCustomer.getAccountsList().get(i).getTransactionsList().size(); j++)
					{
						// Get the transactions
						String currentTransaction = tmpCustomer.getAccountsList().get(i).getTransactionsList().get(j);
						transactions.add(currentTransaction);
					}
					// If there was any transaction history to get and we got it, return the list.
					if (!transactions.isEmpty())
					{
						return transactions;
					}
				}
			}
		}
		// If we didn't find any customer or the account didn't have any transaction
		// history, return null.
		return null;
	}

	/**
	 * This method returns the complete list of customers. This is needed for IO
	 * operations. We need to be able to export the customers, and also load the
	 * customers into this list when we load.
	 * 
	 * @return the customerList
	 */
	public List<Customer> getCustomerList()
	{
		return customerList;
	}

}