package lunvik8;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SavingsAccount class that defines a SavingsAccount as an extension of
 * Account. Part of D0018D, assignment 4.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-01
 */

public class SavingsAccount extends Account implements Serializable
{
	/**
	 * Must be declared otherwise it will not be possible to send bank files between
	 * computers. It will generate InvalidClassException because loaded serial
	 * number does not match exported serial number. The two different machines has
	 * generated different numbers.
	 */
	private static final long serialVersionUID = 100L;
	
	/**
	 * Instance variables specific for SavingsAccounts
	 */
	private int availableFreeWithdrawals;
	private double rate;
	private double debtRate;

	/**
	 * Constructor
	 */
	public SavingsAccount()
	{
		super("Sparkonto");
		availableFreeWithdrawals = 1;
		rate = 1;
		debtRate = 2;
	}

	/**
	 * Withdrawal from account. Depending on if the account has available free
	 * withdrawals the cost is calculated different.
	 * 
	 * If it has free withdrawals available check that the amount to withdraw does
	 * not exceed the balance and is positive.
	 * 
	 * If it does not have free withdrawals available, collect 2% interest from
	 * withdrawn amount and check that the balance and interest does not exceed
	 * current balance.
	 * 
	 * @param amount to withdrawal
	 * @return true if successful, false if failed
	 */
	public boolean withdrawal(double amount)
	{
		double withdrawalInterest = amount * debtRate / 100;
		double currentBalance = getBalance();

		// Free withdrawal available & amount does not exceed current balance.
		if (currentBalance >= amount && amount >= 0 && availableFreeWithdrawals > 0)
		{
			// Set new balance and update condition
			setBalance(currentBalance -= amount);
			availableFreeWithdrawals--;

			// Create timestamp for the transaction
			LocalDateTime currentTime = LocalDateTime.now(); // Get the current time
			DateTimeFormatter prefFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define our format
			String formatedTime = currentTime.format(prefFormat); // Format it
			getTransactionsList().add(formatedTime + " -" + amount + " kr " + "Saldo: " + getBalance() + " kr");

			// Successful transaction, return true
			return true;
		}
		// No free withdrawals available, amount does not exceed current balance +
		// interest for the transaction.
		else if (currentBalance - withdrawalInterest >= amount && amount >= 0 && availableFreeWithdrawals <= 0)
		{
			// Set new balance
			setBalance(currentBalance -= (amount + withdrawalInterest));

			// Create timestamp for the transaction
			LocalDateTime currentTime = LocalDateTime.now(); // Get the current time
			DateTimeFormatter prefFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Define our format
			String formatedTime = currentTime.format(prefFormat); // Format it
			getTransactionsList().add(
					formatedTime + " -" + (amount + withdrawalInterest) + " kr " + "Saldo: " + getBalance() + " kr");

			// Successful transaction, return true
			return true;
		} else
		{
			// Unsuccessful transaction, return false
			return false;
		}
	}

	/**
	 * Calculate interest (SEK)
	 * 
	 * @return the interest
	 */
	public double calculateInterest()
	{
		double interest = getBalance() * rate / 100;
		return interest;
	}

	/**
	 * A toString representation of the account including rate as %.
	 * 
	 * @return accountId + balance + type + rate
	 */
	public String toStringWithRate()
	{
		return (getAccountId() + " " + getBalance() + " kr " + getType() + " " + rate + " %");
	}

}
