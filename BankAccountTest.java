import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
public class BankAccountTest
{

   public static final String OWNERNAME = "Cool Guy Dude";
   public BankAccount myBankAccount = new BankAccount(OWNERNAME);
   public ExpectedException exceptionGrabber = ExpectedException.none();
   @Test
   public void testBankAccountCreatesSomething()
   {
      assertThat(myBankAccount, is(notNullValue()));
   }
   
   public void testBankAccountThrowsIllegalArgument()
   {
      // The exceptionGrabber I instantiate earlier matches an expected
      // exception with the thrown exception.
      BankAccount throwIllegalArg = new BankAccount("");
      exceptionGrabber.expect(IllegalArgumentException.class);
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
      fail("Not yet implemented"); // TODO
   }


   @Test
   public void testMakeWithdrawl()
   {
      fail("Not yet implemented"); // TODO
   }


   @Test
   public void testGetCurrentStatement()
   {
      fail("Not yet implemented"); // TODO
   }

}
