package lunvik8;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * Basic GUI class that implements interface for bank. Part of D0018D,
 * assignment 4. This version of the program also implements file handling,
 * import and export of bank customers.
 * 
 * @author Viktor Lundberg, lunvik-8
 * @date 2021-05-03
 */

public class GUI extends JFrame
{
	/**
	 * Main method to start the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Driver code
		JFrame frame = new GUI();
	}

	// To reach an instance of BankFileHandling, initialized in constructor.
	public static BankFileHandling bank;

	// Window settings
	private static final int HEIGHT = 400;
	private static final int WEST_WIDTH = 240;
	private static final int CENTER_WIDTH = 240;
	private static final int EAST_WIDTH = 240;

	// Panels
	JPanel northPanel, westPanel, centerPanel, eastPanel, southPanel;

	// Components in north panel
	JLabel currentPage;

	// Components in west panel
	JButton home, viewCustomers, createCustomer, deleteCustomer, changeCustomerName, createSavingsAccount,
			createCreditAccount, viewCustomerAccounts, findAccount, deposit, withdrawal, closeAccount, viewTransactions;

	// Components in center panel
	JTextField field1, field2, field3;
	TitledBorder field1border, field2border, field3border;
	JButton okCreateCustomer, okDeleteCustomer, okChangeCustomerName, okCreateSavingsAccount, okCreateCreditAccount,
			okViewCustomerAccounts, okFindAccount, okDeposit, okWithdrawal, okCloseAccount, okViewTransactions;

	// Components in east panel
	JList displayList;

	// IO components
	JMenuBar menuBar;
	JMenu fileMenu, importMenu, exportMenu;
	JMenuItem clear, customersImport, customersExport, transactionsExport;

	/**
	 * Constructor
	 */
	public GUI()
	{
		bank = new BankFileHandling(); // Initialize a new instance of BankFileHandling
		this.setLayout(new BorderLayout()); // Use BorderLayout

		// Build panels
		buildMenu();
		buildNorth();
		buildWest();
		buildCenter();
		buildEast();
		buildSouth();

		// Frame settings
		this.setTitle("Bank");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Menu for file handling.
	 */
	private void buildMenu()
	{
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// ActionListener for IO operations
		IOListener ioListener = new IOListener();

		// File menu
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Clear button
		clear = new JMenuItem("Clear");
		fileMenu.add(clear);
		clear.addActionListener(ioListener);

		// Import export
		importMenu = new JMenu("Import ...");
		fileMenu.add(importMenu);
		exportMenu = new JMenu("Export ...");
		fileMenu.add(exportMenu);

		// Import sub menu
		customersImport = new JMenuItem("All customers with accounts");
		importMenu.add(customersImport);
		customersImport.addActionListener(ioListener);

		// Export sub menu
		customersExport = new JMenuItem("All customers with accounts");
		exportMenu.add(customersExport);
		customersExport.addActionListener(ioListener);

		transactionsExport = new JMenuItem("Transactions for account");
		exportMenu.add(transactionsExport);
		transactionsExport.addActionListener(ioListener);
	}

	/**
	 * North center panel, display current page
	 */
	private void buildNorth()
	{
		northPanel = new JPanel();
		this.add(northPanel, BorderLayout.NORTH);

		// Displays current page
		currentPage = new JLabel("Home"); // Start at page "Home".
		northPanel.add(currentPage);
		northPanel.setVisible(true);
	}

	/**
	 * West panel. Menu with buttons, all our functions.
	 */
	private void buildWest()
	{
		westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(14, 1)); // GridLayout inside this panel to keep buttons aligned.
		westPanel.setPreferredSize(new Dimension(WEST_WIDTH, HEIGHT));
		this.add(westPanel, BorderLayout.WEST);

		// ActionListener for menu
		MenuListener menuListener = new MenuListener();

		// Buttons
		home = new JButton("Home");
		westPanel.add(home);
		home.addActionListener(menuListener);

		viewCustomers = new JButton("View all existing customers");
		westPanel.add(viewCustomers);
		viewCustomers.addActionListener(menuListener);

		createCustomer = new JButton("Create new customer");
		westPanel.add(createCustomer);
		createCustomer.addActionListener(menuListener);

		deleteCustomer = new JButton("Delete an existing customer");
		westPanel.add(deleteCustomer);
		deleteCustomer.addActionListener(menuListener);

		JLabel handleCust = new JLabel("                Handle customers");
		westPanel.add(handleCust);

		changeCustomerName = new JButton("Change customer name");
		westPanel.add(changeCustomerName);
		changeCustomerName.addActionListener(menuListener);

		createSavingsAccount = new JButton("Create savings account");
		westPanel.add(createSavingsAccount);
		createSavingsAccount.addActionListener(menuListener);

		createCreditAccount = new JButton("Create credit account");
		westPanel.add(createCreditAccount);
		createCreditAccount.addActionListener(menuListener);

		viewCustomerAccounts = new JButton("View customer accounts");
		westPanel.add(viewCustomerAccounts);
		viewCustomerAccounts.addActionListener(menuListener);

		findAccount = new JButton("Find account");
		westPanel.add(findAccount);
		findAccount.addActionListener(menuListener);

		deposit = new JButton("Deposit");
		westPanel.add(deposit);
		deposit.addActionListener(menuListener);

		withdrawal = new JButton("Withdrawal");
		westPanel.add(withdrawal);
		withdrawal.addActionListener(menuListener);

		closeAccount = new JButton("Close account");
		westPanel.add(closeAccount);
		closeAccount.addActionListener(menuListener);

		viewTransactions = new JButton("View transactions");
		westPanel.add(viewTransactions);
		viewTransactions.addActionListener(menuListener);

		westPanel.setVisible(true);
	}

	/**
	 * Center panel. Handles input and "OK" buttons.
	 */
	private void buildCenter()
	{
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(CENTER_WIDTH, HEIGHT));
		this.add(centerPanel, BorderLayout.CENTER);

		// Fields, will be used to handle input for all functions and will be
		// manipulated each time.
		field1 = new JTextField(14);
		centerPanel.add(field1);
		field1.setVisible(false);

		field2 = new JTextField(14);
		centerPanel.add(field2);
		field2.setVisible(false);

		field3 = new JTextField(14);
		centerPanel.add(field3);
		field3.setVisible(false);

		// ActionListener for "OK" buttons
		OkListener okListener = new OkListener();

		// "OK" buttons need to be different depending on which function we are using
		// because it will call different functions within BankLogic class.
		okCreateCustomer = new JButton("OK");
		okCreateCustomer.addActionListener(okListener);
		centerPanel.add(okCreateCustomer);
		okCreateCustomer.setVisible(false);

		okDeleteCustomer = new JButton("OK");
		okDeleteCustomer.addActionListener(okListener);
		centerPanel.add(okDeleteCustomer);
		okDeleteCustomer.setVisible(false);

		okChangeCustomerName = new JButton("OK");
		okChangeCustomerName.addActionListener(okListener);
		centerPanel.add(okChangeCustomerName);
		okChangeCustomerName.setVisible(false);

		okCreateSavingsAccount = new JButton("OK");
		okCreateSavingsAccount.addActionListener(okListener);
		centerPanel.add(okCreateSavingsAccount);
		okCreateSavingsAccount.setVisible(false);

		okCreateCreditAccount = new JButton("OK");
		okCreateCreditAccount.addActionListener(okListener);
		centerPanel.add(okCreateCreditAccount);
		okCreateCreditAccount.setVisible(false);

		okViewCustomerAccounts = new JButton("OK");
		okViewCustomerAccounts.addActionListener(okListener);
		centerPanel.add(okViewCustomerAccounts);
		okViewCustomerAccounts.setVisible(false);

		okFindAccount = new JButton("OK");
		okFindAccount.addActionListener(okListener);
		centerPanel.add(okFindAccount);
		okFindAccount.setVisible(false);

		okDeposit = new JButton("OK");
		okDeposit.addActionListener(okListener);
		centerPanel.add(okDeposit);
		okDeposit.setVisible(false);

		okWithdrawal = new JButton("OK");
		okWithdrawal.addActionListener(okListener);
		centerPanel.add(okWithdrawal);
		okWithdrawal.setVisible(false);

		okCloseAccount = new JButton("OK");
		okCloseAccount.addActionListener(okListener);
		centerPanel.add(okCloseAccount);
		okCloseAccount.setVisible(false);

		okViewTransactions = new JButton("OK");
		okViewTransactions.addActionListener(okListener);
		centerPanel.add(okViewTransactions);
		okViewTransactions.setVisible(false);

		centerPanel.setVisible(true);
	}

	/**
	 * East panel. Shows relevant current information about customers, accounts and
	 * transactions. Information field changes depending on which state in the
	 * program user currently is.
	 */
	private void buildEast()
	{
		eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(EAST_WIDTH, HEIGHT));
		this.add(eastPanel, BorderLayout.EAST);

		// We display information in JList. When we start the program default is a list
		// of the bank's current customers.
		displayList = new JList();
		displayList.setListData(bank.getAllCustomers().toArray());
		displayList.setBorder(BorderFactory.createTitledBorder("Customers"));

		// Scroll is needed because list can grow large.
		JScrollPane custScroll = new JScrollPane(displayList);
		custScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		custScroll.setVisible(true);
		custScroll.setPreferredSize(new Dimension(EAST_WIDTH, HEIGHT - 100));
		eastPanel.add(custScroll);

		eastPanel.setVisible(true);
	}

	/**
	 * South panel. Shows program name, version and author.
	 */
	private void buildSouth()
	{
		southPanel = new JPanel();
		this.add(southPanel, BorderLayout.SOUTH);

		JLabel version = new JLabel("Bank 1.0 by Viktor Lundberg");
		southPanel.add(version);
		southPanel.setVisible(true);
	}

	/**
	 * Since we are reusing the center panel for user input interactions we have to
	 * make sure we only display the components we need for the current page. So by
	 * default, we want to reset so everything is hidden each time we start to call
	 * a button function.
	 */
	private void hideFields()
	{
		field1.setVisible(false);
		field2.setVisible(false);
		field3.setVisible(false);
		okCreateCustomer.setVisible(false);
		okDeleteCustomer.setVisible(false);
		okChangeCustomerName.setVisible(false);
		okCreateSavingsAccount.setVisible(false);
		okCreateCreditAccount.setVisible(false);
		okViewCustomerAccounts.setVisible(false);
		okFindAccount.setVisible(false);
		okDeposit.setVisible(false);
		okWithdrawal.setVisible(false);
		okCloseAccount.setVisible(false);
		okViewTransactions.setVisible(false);
	}

	/**
	 * Resets the textFields in center panel.
	 */
	private void resetTextFields()
	{
		field1.setText("");
		field2.setText("");
		field3.setText("");
	}

	/**
	 * Nested ActionListener class. This class listens to the buttons in our menu,
	 * west panel. Each action will perform changes to the center panel, getting the
	 * center panel ready to handle next user input properly.
	 */
	public class MenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Get text from button clicked. Use this information to find out which button
			// user clicked on.
			String buttonText = e.getActionCommand();

			// Clicked "Home" or "View all existing customers" button
			if (buttonText.equals("Home") || buttonText.equals("View all existing customers"))
			{
				// Change current page information
				currentPage.setText(buttonText);
				// By default we want to hide all fields and bring out only what we need
				hideFields();

				// By default we want to show current customers in the bank.
				displayList.setListData(bank.getAllCustomers().toArray());
				displayList.setBorder(BorderFactory.createTitledBorder("Customers"));
			}
			// Clicked "Create new customer" button
			else if (buttonText.equals("Create new customer"))
			{
				currentPage.setText(buttonText);
				hideFields();

				// Manipulate fields so the user knows what to fill in
				field1.setBorder(BorderFactory.createTitledBorder("Name"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Surname"));
				field2.setVisible(true);
				field3.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field3.setVisible(true);

				// Bring out "OK" button linked to the specific function we are performing, in
				// this case the click of "OK" from user means we should call the createCustomer
				// method in BankLogic.
				okCreateCustomer.setVisible(true);
			}
			// Clicked "Delete an existing customer" button
			else if (buttonText.equals("Delete an existing customer"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				okDeleteCustomer.setVisible(true);
			}
			// Clicked "Change customer name" button
			else if (buttonText.equals("Change customer name"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Name"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Surname"));
				field2.setVisible(true);
				field3.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field3.setVisible(true);
				okChangeCustomerName.setVisible(true);
			}
			// Clicked "Create savings account" button
			else if (buttonText.equals("Create savings account"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				okCreateSavingsAccount.setVisible(true);
			}
			// Clicked "Create credit account" button
			else if (buttonText.equals("Create credit account"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				okCreateCreditAccount.setVisible(true);
			}
			// Clicked "View customer accounts" button
			else if (buttonText.equals("View customer accounts"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				okViewCustomerAccounts.setVisible(true);
			}
			// Clicked "Find account" button
			else if (buttonText.equals("Find account"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Account number"));
				field2.setVisible(true);
				okFindAccount.setVisible(true);
			}
			// Clicked "Deposit" button
			else if (buttonText.equals("Deposit"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Account number"));
				field2.setVisible(true);
				field3.setBorder(BorderFactory.createTitledBorder("Amount"));
				field3.setVisible(true);
				okDeposit.setVisible(true);
			}
			// Clicked "Withdrawal" button
			else if (buttonText.equals("Withdrawal"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Account number"));
				field2.setVisible(true);
				field3.setBorder(BorderFactory.createTitledBorder("Amount"));
				field3.setVisible(true);
				okWithdrawal.setVisible(true);
			}
			// Clicked "Close account" button
			else if (buttonText.equals("Close account"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Account number"));
				field2.setVisible(true);
				okCloseAccount.setVisible(true);
			}
			// Clicked "View transactions" button
			else if (buttonText.equals("View transactions"))
			{
				currentPage.setText(buttonText);
				hideFields();
				field1.setBorder(BorderFactory.createTitledBorder("Personal number"));
				field1.setVisible(true);
				field2.setBorder(BorderFactory.createTitledBorder("Account number"));
				field2.setVisible(true);
				okViewTransactions.setVisible(true);
			}
		}
	}

	/**
	 * Nested ActionListener class. This class listens to the "OK" buttons and
	 * accepts information from field1, field2 and field3 and calls the appropriate
	 * function in BankLogic class.
	 */
	public class OkListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Create new customer
			if (e.getSource().equals(okCreateCustomer))
			{
				if (!field1.getText().isEmpty() && !field2.getText().isEmpty() && !field3.getText().isEmpty())
				{
					// createCustomer will return true if customer successfully was created. It will
					// return false if customer already existed in the system.
					boolean action = bank.createCustomer(field1.getText(), field2.getText(), field3.getText());

					// Display changes
					displayList.setListData(bank.getAllCustomers().toArray());

					// Sets the text in field1, field2 and field3 to ""
					resetTextFields();

					// Confirm success or not
					if (action == true)
					{
						success();
					}
					else if (action == false)
					{
						JOptionPane.showMessageDialog(null, "Customer already exists");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter information into all fields");
				}
			}
			// Delete customer
			else if (e.getSource().equals(okDeleteCustomer))
			{
				// deleteCustomer returns an ArrayList with information about the deleted
				// customer. Store this information.
				ArrayList<String> list = bank.deleteCustomer(field1.getText());

				// But if no customer was found action will contain null, if this is the case,
				// customer was not found
				if (list == null)
				{
					customerNotFound();
				}
				// If we got something back, continue as normal.
				else
				{
					displayList.setListData(bank.getAllCustomers().toArray());
					resetTextFields();

					// Show information about the deleted customer
					JList confirm = new JList();
					confirm.setListData(list.toArray());
					JOptionPane.showMessageDialog(null, confirm);
				}
			}
			// Change customer name
			else if (e.getSource().equals(okChangeCustomerName))
			{
				// Check that the user entered something into field1 or field2.
				if (!field1.getText().isEmpty() && field2.getText().isEmpty()
						|| field1.getText().isEmpty() && !field2.getText().isEmpty()
						|| !field1.getText().isEmpty() && !field2.getText().isEmpty())
				{
					boolean action = bank.changeCustomerName(field1.getText(), field2.getText(), field3.getText());
					displayList.setListData(bank.getAllCustomers().toArray());
					resetTextFields();
					// Confirm success or not
					if (action == true)
					{
						success();
					}
					else if (action == false)
					{
						customerNotFound();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter something into the fields to change name");
				}
			}
			// Create savings account
			else if (e.getSource().equals(okCreateSavingsAccount))
			{
				// Creating accounts is a little different because it will return an int value
				// instead of boolean.
				int action = bank.createSavingsAccount(field1.getText());

				if (action == -1)
				{
					customerNotFound();
				}
				else
				{
					displayList.setListData(bank.getCustomer(field1.getText()).toArray());
					displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
					resetTextFields();
					confirmAccount(action);
				}
			}
			// Create credit account
			else if (e.getSource().equals(okCreateCreditAccount))
			{
				int action = bank.createCreditAccount(field1.getText());

				if (action == -1)
				{
					customerNotFound();
				}
				else
				{
					displayList.setListData(bank.getCustomer(field1.getText()).toArray());
					displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
					resetTextFields();
					confirmAccount(action);
				}

			}
			// View customer accounts
			else if (e.getSource().equals(okViewCustomerAccounts))
			{
				ArrayList<String> list = bank.getCustomer(field1.getText());

				// If this is true, we didn't find the customer.
				if (list == null)
				{
					customerNotFound();
				}
				// We found the customer, continue.
				else
				{
					displayList.setListData(list.toArray());
					displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
					resetTextFields();
					success();
				}
			}
			// Find account
			else if (e.getSource().equals(okFindAccount))
			{
				// Check that the user entered something into field1 and field2.
				if (!field1.getText().isEmpty() && !field2.getText().isEmpty())
				{
					try
					{
						String pNo = field1.getText();
						int accountId = Integer.parseInt(field2.getText());

						String info = bank.getAccount(pNo, accountId);

						if (info == null)
						{
							JOptionPane.showMessageDialog(null, "No account was found");
						}
						else
						{
							String[] arr = { info };
							displayList.setListData(arr);
							displayList.setBorder(BorderFactory.createTitledBorder("Account information"));
							resetTextFields();
							success();
						}
					}
					catch (NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null,
								"Account number cannot contain any letters or special characters");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please enter something into both fields to search");
				}
			}
			// Deposit
			else if (e.getSource().equals(okDeposit))
			{
				try
				{
					String pNo = field1.getText();
					int accountId = Integer.parseInt(field2.getText());
					double amount = Double.parseDouble(field3.getText());

					boolean action = bank.deposit(pNo, accountId, amount);

					if (action == true)
					{
						displayList.setListData(bank.getCustomer(field1.getText()).toArray());
						displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
						resetTextFields();
						confirmDepositWithdrawal(action);
					}
					else
					{
						confirmDepositWithdrawal(action);
					}
				}
				catch (NumberFormatException ex)
				{
					invalidFormat();
				}
			}
			// Withdrawal
			else if (e.getSource().equals(okWithdrawal))
			{
				try
				{
					String pNo = field1.getText();
					int accountId = Integer.parseInt(field2.getText());
					double amount = Double.parseDouble(field3.getText());

					boolean action = bank.withdraw(pNo, accountId, amount);

					if (action == true)
					{
						displayList.setListData(bank.getCustomer(field1.getText()).toArray());
						displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
						resetTextFields();
						confirmDepositWithdrawal(action);
					}
					else
					{
						confirmDepositWithdrawal(action);
					}
				}
				catch (NumberFormatException ex)
				{
					invalidFormat();
				}
			}
			// Close account
			else if (e.getSource().equals(okCloseAccount))
			{
				try
				{
					String pNo = field1.getText();
					int accountId = Integer.parseInt(field2.getText());

					// In this case we get a String back from closeAccount method
					String action = bank.closeAccount(pNo, accountId);

					if (action == null)
					{
						JOptionPane.showMessageDialog(null, "Customer or account not found");
					}
					else
					{
						displayList.setListData(bank.getCustomer(field1.getText()).toArray());
						displayList.setBorder(BorderFactory.createTitledBorder("Customer info"));
						resetTextFields();

						// Show string to confirm success
						JOptionPane.showMessageDialog(null, action);
					}
				}
				catch (NumberFormatException ex)
				{
					invalidFormat();
				}
			}
			// View transactions
			else if (e.getSource().equals(okViewTransactions))
			{
				try
				{
					String pNo = field1.getText();
					int accountId = Integer.parseInt(field2.getText());

					ArrayList<String> list = bank.getTransactions(pNo, accountId);

					if (list == null)
					{
						customerNotFound();
					}
					else
					{
						displayList.setListData(list.toArray());
						displayList.setBorder(BorderFactory.createTitledBorder("Account transactions"));
						resetTextFields();
						success();
					}
				}
				catch (NumberFormatException ex)
				{
					invalidFormat();
				}
			}
		}
	}

	/**
	 * Nested ActionListener class, that listen to Input/Output operations in our
	 * File menu.
	 */
	public class IOListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Clear all customers
			if (e.getSource().equals(clear))
			{
				// Make sure user didn't missclick
				int option = JOptionPane.showConfirmDialog(null, "Are you sure?");

				if (option == 0)
				{
					bank.clear();

					// Update display list
					displayList.setListData(bank.getAllCustomers().toArray());
					displayList.setBorder(BorderFactory.createTitledBorder("Customers"));
				}
			}

			// Export all customers with accounts
			if (e.getSource().equals(customersExport))
			{
				String fileName = JOptionPane.showInputDialog("Please enter file name");

				// User clicks cancel
				if (fileName == null)
				{
					return;
				}

				// User entered a file name
				if (!fileName.isEmpty())
				{
					try
					{
						bank.exportCustomers(fileName);
					}
					catch (FileNotFoundException ex)
					{
						JOptionPane.showMessageDialog(null, "File not found"); // Exception message to user
					}
					catch (IOException ex)
					{
						JOptionPane.showMessageDialog(null, "Failed or interrupted input / output operation");
					}
				}

				// User didn't enter a file name and clicked OK
				else if (fileName.isEmpty())
				{
					JOptionPane.showInternalMessageDialog(null, "File name cannot be empty");
				}
			}

			// Import all customers with accounts
			if (e.getSource().equals(customersImport))
			{
				String fileName = JOptionPane.showInputDialog("Please enter file name");

				// User clicks cancel
				if (fileName == null)
				{
					return;
				}

				// User entered a file name
				if (!fileName.isEmpty())
				{
					try
					{
						bank.importCustomers(fileName);
						displayList.setListData(bank.getAllCustomers().toArray());
						displayList.setBorder(BorderFactory.createTitledBorder("Customers"));
					}
					catch (FileNotFoundException ex)
					{
						JOptionPane.showMessageDialog(null, "File not found");
					}
					catch (IOException ex)
					{
						JOptionPane.showMessageDialog(null, "Failed or interrupted input / output operation");
					}
					catch (ClassNotFoundException ex)
					{
						JOptionPane.showMessageDialog(null,
								"The information you are trying to import does not match what this bank can handle");
					}
				}

				// User didn't enter a file name and clicked OK
				else if (fileName.isEmpty())
				{
					JOptionPane.showInternalMessageDialog(null, "File name cannot be empty");
				}
			}

			// Transactions for account
			if (e.getSource().equals(transactionsExport))
			{
				// For the transactions we need 3 inputs from user. This is solved by creating a
				// panel that we add to our JOptionPane
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(3, 1));

				JTextField pNr_textField = new JTextField(14);
				pNr_textField.setBorder(BorderFactory.createTitledBorder("Personal number"));

				JTextField accountId_textField = new JTextField(14);
				accountId_textField.setBorder(BorderFactory.createTitledBorder("Account number"));

				JTextField fileName_textField = new JTextField(14);
				fileName_textField.setBorder(BorderFactory.createTitledBorder("File name"));

				// Add components
				panel.add(pNr_textField);
				panel.add(accountId_textField);
				panel.add(fileName_textField);

				// User input
				int option = JOptionPane.showConfirmDialog(null, panel, "Export transactions",
						JOptionPane.INFORMATION_MESSAGE);

				// User clicked OK
				if (option == 0)
				{
					try
					{
						String pNr = pNr_textField.getText();
						int accountId = Integer.parseInt(accountId_textField.getText());
						String fileName = fileName_textField.getText();
						bank.exportTransactions(pNr, accountId, fileName);
						JOptionPane.showMessageDialog(null, "Success");
					}
					catch (IOException ex)
					{
						JOptionPane.showMessageDialog(null, "Failed or interrupted input / output operation");
					}
				}
			}
		}
	}

	/**
	 * Shows an JOptionPane message with information if the action from the user was
	 * successful or not.
	 * 
	 * @param action, true = successful, false = unsuccessful.
	 */
	private void confirmDepositWithdrawal(boolean action)
	{
		if (action == true)
		{
			JOptionPane.showMessageDialog(null, "OK");
		}
		else if (action == false)
		{
			JOptionPane.showMessageDialog(null, "Account not found or withdrawal conditions not met");
		}
	}

	/**
	 * Shows an JOptionPane message with information about newly created account.
	 * 
	 * @param action, account number from user action if successful, otherwise -1 =
	 *                something was wrong.
	 */
	private void confirmAccount(int action)
	{
		if (action == -1)
		{
			JOptionPane.showMessageDialog(null, "No account was created");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "OK, account number: " + action);
		}
	}

	/**
	 * JOptionPane message shown when customer is not found
	 */
	private void customerNotFound()
	{
		JOptionPane.showMessageDialog(null, "Customer not found");
	}

	/**
	 * JOptionPane message shown when action was successfully executed
	 */
	private void success()
	{
		JOptionPane.showMessageDialog(null, "Success");
	}

	/**
	 * JOptionPane message shown when user entered wrong input. Usually this happens
	 * when we cannot parse an int or double from String. So input from user was
	 * probably containing characters or special characters, and not only integers
	 * or floating decimals
	 */
	private void invalidFormat()
	{
		JOptionPane.showMessageDialog(null, "Problem with input. Invalid format");
	}

}
