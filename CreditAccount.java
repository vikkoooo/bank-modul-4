package lunvik8;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CreditAccount class that defines a CreditAccount as an extension of Account.
 * Part of D0018D, assignment 4.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-01
 */

public class CreditAccount extends Account implements Serializable
{
	/**
	 * Must be declared otherwise it will not be possible to send bank files between
	 * computers. It will generate InvalidClassException because loaded serial
	 * number does not match exported serial number. The two different machines has
	 * generated different numbers.
	 */
	private static final long serialVersionUID = 100L;
	
	/**
	 * Instance variables specific for CreditAccounts
	 */
	private int creditLimit;
	private double rate;
	private double debtRate;

	/**
	 * Constructor
	 */
	public CreditAccount()
	{
		super("Kreditkonto");
		creditLimit = 5000;
		rate = 0.5;
		debtRate = 7;
	}

	/**
	 * Withdrawal from account. Withdrawal amount has to be a positive number.
	 * Cannot exceed credit limit.
	 * 
	 * @param amount to withdrawal
	 * @return true if successful, false if failed
	 */
	public boolean withdrawal(double amount)
	{
		// Checks that the withdrawal amount does not exceed the current balance +
		// credit limit.
		double currentBalance = getBalance();
		if (currentBalance + creditLimit >= amount && amount >= 0)
		{
			// Set new balance
			setBalance(currentBalance -= amount);

			// Create timestamp for the transaction
			LocalDateTime currentTime = LocalDateTime.now(); // Get the current time
			DateTimeFormatter prefFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define our format
			String formatedTime = currentTime.format(prefFormat); // Format it
			getTransactionsList().add(formatedTime + " -" + amount + " kr " + "Saldo: " + getBalance() + " kr");

			// Successful transaction, return true
			return true;
		} else
		{
			// Unsuccessful transaction, return false
			return false;
		}
	}

	/**
	 * Calculate interest (SEK). If balance is positive, give 0,5% interest.
	 * Otherwise if balance is negative, collect 7% debt interest.
	 * 
	 * @return the interest
	 */
	public double calculateInterest()
	{
		double currentRate;
		// Current balance is positive: 0,5% interest
		if (getBalance() >= 0)
		{
			currentRate = rate;
		}
		// Current balance is negative: 7% debt interest
		else
		{
			currentRate = debtRate;
		}
		// Calculate interest and return it
		double interest = getBalance() * currentRate / 100;
		return interest;
	}

	/**
	 * A toString representation of the account including rate as %.
	 * 
	 * @return accountId + balance + type + rate
	 */
	public String toStringWithRate()
	{
		double currentRate;
		// Current balance is positive: 0,5% interest
		if (getBalance() >= 0)
		{
			currentRate = rate;
		}
		// Current balance is negative: 7% debt interest
		else
		{
			currentRate = debtRate;
		}
		return (getAccountId() + " " + getBalance() + " kr " + getType() + " " + currentRate + " %");
	}

}
