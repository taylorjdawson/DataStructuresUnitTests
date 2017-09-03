import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;


/**
 * Complete unit tests for the PhoneNumber class.
 * Send any questions to the groupchat, but all tests should be readable
 * from left-right. [is() means nothing to the compiler btw]
 * @author Daniel Rohwedder
 *
 */
public class PhoneNumberTest
{

   public static final int AREA_CODE = 510;
   public static final int PREFIX = 458;
   public static final int EXTENSION = 1138;
   public static final String PHONE_NUMBER_STR = "510-458-1138";
   
   @Test
   public void testPhoneNumberIntIntIntCreatesInstance()
   {
      PhoneNumber myNumber = new PhoneNumber(AREA_CODE, PREFIX, EXTENSION);
      assertThat(myNumber, is(notNullValue()));
   }


   @Test
   public void testPhoneNumberDefaultAreaCreatesInstance()
   {
      PhoneNumber myNumber = new PhoneNumber(PREFIX, EXTENSION);
      assertThat(myNumber, is(notNullValue()));
   }


   @Test
   public void testPhoneNumberStringCreatesInstance()
   {
      PhoneNumber myNumber = new PhoneNumber(PHONE_NUMBER_STR);
      assertThat(myNumber, is(notNullValue()));
   }


   @Test
   public void testIsTollFreeIntConstructor800()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(800, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeIntConstructor844()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(844, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeIntConstructor855()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(855, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeIntConstructor866()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(866, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeIntConstructor877()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(877, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeIntConstructor888()
   {
      PhoneNumber tollFreeInts = new PhoneNumber(888, 32, 1444);
      assertThat(tollFreeInts.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeStringConstructor()
   {
      PhoneNumber tollFreeString = new PhoneNumber("800-420-9000");
      assertThat(tollFreeString.isTollFree(), is(equalTo(true)));
   }
   
   @Test
   public void testIsTollFreeStringConstructorReturnsFalse()
   {
      PhoneNumber notTollFreeString = new PhoneNumber("523-420-9000");
      assertThat(notTollFreeString.isTollFree(), is(equalTo(false)));
   }
   
   @Test
   public void testIsTollFreeIntConstructorReturnsFalse()
   {
      PhoneNumber notTollFreeInts = new PhoneNumber(AREA_CODE, PREFIX, EXTENSION);
      assertThat(notTollFreeInts.isTollFree(), is(equalTo(false)));
   }
   
   @Test
   public void testIsTollFreeDefaultAreaCodeReturnsFalse()
   {
      PhoneNumber notTollFreeDefault = new PhoneNumber(PREFIX, EXTENSION);
      {
         assertThat(notTollFreeDefault.isTollFree(), is(equalTo(false)));
      }
   }

   @Test
   public void testToStringNormalNumber()
   {
      PhoneNumber intIntPhoneNum = new PhoneNumber(AREA_CODE, PREFIX, EXTENSION);
      assertThat(intIntPhoneNum.toString(), containsString("(510) 458-1138"));
   }
   
   @Test
   public void testToStringWithDefaultAreaCode()
   {
      PhoneNumber defaultedPhoneNum = new PhoneNumber(PREFIX, EXTENSION);
      assertThat(defaultedPhoneNum.toString(), containsString(") 458-1138"));
   }
   
   @Test
   public void testToStringWithStringConst()
   {
      PhoneNumber stringPhoneNum = new PhoneNumber("510-458-1138");
      assertThat(stringPhoneNum.toString(), containsString("(510) 458-1138"));
   }
   
   @Test
   public void testToStringWithLeadingZeros()
   {
      PhoneNumber zeroesPhoneNum = new PhoneNumber(AREA_CODE, 24, 1);
      assertThat(zeroesPhoneNum.toString(), containsString("(510) 024-0001"));
   }
   
   @Test
   public void testToStringWithLeadingZeroesAndStringConst()
   {
      PhoneNumber stringZeroesPhoneNum = new PhoneNumber("510-024-0001");
      assertThat(stringZeroesPhoneNum.toString(), containsString("(510) 024-0001"));
   }

}
