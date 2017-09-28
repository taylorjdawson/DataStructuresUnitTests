import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
public class BankAccountTest
{

   public static final String OWNERNAME = "Cool Guy Dude";
   public BankAccount myBankAccount = new BankAccount(OWNERNAME);
   
   @Rule
   public final ExpectedException exceptionGrabber = ExpectedException.none();
   @Test
   public void testBankAccountCreatesSomething()
   {
      assertThat(myBankAccount, is(notNullValue()));
   }
   
   @Test
   public void testBankAccountThrowsIllegalArgument()
   {
      // The exceptionGrabber I instantiate earlier matches an expected
      // exception with the thrown exception.
      exceptionGrabber.expect(IllegalArgumentException.class);

      BankAccount throwIllegalArg = new BankAccount(null);
   }


   @Test
   public void testGetOwner()
   {
      assertThat(myBankAccount.getOwner(), is(equalTo(OWNERNAME)));
   }


   @Test
   public void testGetAccountNumber()
   {
      BankAccount acct1 = new BankAccount("foo");
      BankAccount acct2 = new BankAccount("bar");
      assertThat(acct1.getAccountNumber(), not(equalTo(acct2.getAccountNumber())));
   }


   @Test
   public void testGetCurrentBalance()
   {
      assertThat(myBankAccount.getCurrentBalance(), is(closeTo(0.0, 1.0E-7)));
   }


   @Test
   public void testMakeDeposit()
   {
      BankAccount foo = new BankAccount("Foo");
      double depositAmt = 20.00;
      foo.makeDeposit("-", depositAmt);
      assertThat(foo.getCurrentBalance(), is(closeTo(depositAmt, 1.0E-7)));
   }


   @Test
   public void testMakeWithdrawal()
   {
      BankAccount foo = new BankAccount("Foo");
      double depositAmt = 20.00;
      double withdrawalAmt = 10.00;
      foo.makeDeposit("-", depositAmt);
      foo.makeWithdrawal("+", withdrawalAmt);
      assertThat(foo.getCurrentBalance(), is(closeTo(depositAmt-withdrawalAmt, 1.0E-7)));
   }
   
   @Test
   public void testMakeWithdrawalThrowsIllegalArg()
   {
      exceptionGrabber.expect(IllegalArgumentException.class);
      myBankAccount.makeWithdrawal("should throw", -2.2);
   }

   @Test
   public void testMakeWithdrawalThrowsIllegalState()
   {
      BankAccount zeroBal = new BankAccount("no Balance");
      exceptionGrabber.expect(IllegalStateException.class);
      zeroBal.makeWithdrawal("should throw", 222222222222.0);
   }
   @Test
   public void testGetCurrentStatement()
   {
      BankAccount statementExample = new BankAccount("David Hansen");
      statementExample.makeDeposit("Direct Deposit", 1634.44);
      statementExample.makeWithdrawal("Coffee Cottage", 6.22);
      statementExample.makeDeposit("Deposit", 125.00);
      
      assertThat(statementExample.getCurrentStatement(), containsString("David Hansen"));
      
   }

}
