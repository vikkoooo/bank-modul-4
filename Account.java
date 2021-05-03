package lunvik8;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Account class that defines the properties of an Account. Part of D0018D,
 * assignment 4.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-01
 */

public abstract class Account implements Serializable
{
	/**
	 * Must be declared otherwise it will not be possible to send bank files between
	 * computers. It will generate InvalidClassException because loaded serial
	 * number does not match exported serial number. The two different machines has
	 * generated different numbers.
	 */
	private static final long serialVersionUID = 100L;

	/**
	 * Class variable. We want an unique accountId for the whole bank, not just for
	 * the customer.
	 */
	private static int currentAccountId = 1000;

	/**
	 * List to store all transactions for the current account.
	 */
	private List<String> transactionsList = new ArrayList<>();

	/**
	 * Instance variables for all Accounts.
	 */
	private int accountId;
	private double balance;
	private String type;

	/**
	 * Constructor
	 * 
	 * @param type of account to be defined by the underclass.
	 */
	public Account(String type)
	{
		currentAccountId++; // Increment class variable currentAccountId
		accountId = currentAccountId; // Then set our new account to that Id
		balance = 0;
		this.type = type;
	}

	/**
	 * Calculates the interest of the account
	 * 
	 * @return the interest
	 */
	abstract double calculateInterest();

	/**
	 * Abstract method withdrawal to be defined in CreditAccount and SavingsAccount
	 * 
	 * @param amount to withdrawal
	 * @return true if successful, false if failed
	 */
	abstract boolean withdrawal(double amount);

	/**
	 * Abstract toString representation of the account including rate as %.
	 * 
	 * @return accountId + balance + type + rate
	 */
	abstract String toStringWithRate();

	/**
	 * A toString representation of the account without rate.
	 * 
	 * @return accountId + balance + type
	 */
	public String toStringWithoutRate()
	{
		return (accountId + " " + balance + " kr " + getType() + " ");
	}

	/**
	 * Deposit into account
	 * 
	 * @param amount to deposit as double
	 */
	public void deposit(double amount)
	{
		// Set the new balance
		balance += amount;

		// Create timestamp for the transaction
		LocalDateTime currentTime = LocalDateTime.now(); // Get the current time
		DateTimeFormatter prefFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define our format
		String formatedTime = currentTime.format(prefFormat); // Format it
		getTransactionsList().add(formatedTime + " " + amount + " kr " + "Saldo: " + getBalance() + " kr");
	}

	/**
	 * getAccountId
	 * 
	 * @return the accountId
	 */
	public int getAccountId()
	{
		return accountId;
	}

	/**
	 * Check balance
	 * 
	 * @return the balance
	 */
	public double getBalance()
	{
		return balance;
	}

	/**
	 * Method to get the transaction list
	 * 
	 * @return the list of transactions
	 */
	public List<String> getTransactionsList()
	{
		return transactionsList;
	}

	/**
	 * Get type of account
	 * 
	 * @return the account type as String. "Sparkonto" or "Kreditkonto"
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * currentAccountId needs to be reachable to implement IO operations.
	 * 
	 * @return the currentAccountId
	 */
	public static int getCurrentAccountId()
	{
		return currentAccountId;
	}

	/**
	 * To be able to change the balance we need a setter that the withdrawal methods
	 * can use.
	 * 
	 * @param the new balance
	 */
	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	/**
	 * When we load a new bank from file we need to be able to set the
	 * currentAccountId to what it was when the bank was exported.
	 * 
	 * @param the new currentAccountId loaded from file
	 */
	public static void setCurrentAccountId(int currentAccountId)
	{
		Account.currentAccountId = currentAccountId;
	}

}
