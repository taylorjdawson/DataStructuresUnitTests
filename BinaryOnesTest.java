import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class BinaryOnesTest
{

   public static long SMALL_INPUT = 15;
   public static long BIGGER_INPUT = 4206969;
   public static long ZERO = 0;
   @Test
   public void testNumberOfOnesForSmallNumber()
   {
      assertThat(BinaryOnes.numberOfOnes(SMALL_INPUT), is(equalTo(4)));
   }
   
   @Test
   public void testNumberOfOnesForBiggerNumber()
   {
      assertThat(BinaryOnes.numberOfOnes(BIGGER_INPUT), is(equalTo(9)));
   }
   
   @Test
   public void testNumberOfOnesForZero()
   {
      assertThat(BinaryOnes.numberOfOnes(ZERO), is(equalTo(0)));
   }

}
