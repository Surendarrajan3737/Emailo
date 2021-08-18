package webOperations;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import com.twilio.*;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class OtpVerification {
		private static final String ACCOUNT_SID = "ACc7ff53cae48cc0c41de54fc3e7fe3dfb";
		private static final String AUTH_TOKEN = "912102278f59a90c8c3f44897801ded7";
		
		public static String getOTP(String mobileNumber) {
			if(mobileNumber.charAt(0)=='+' && mobileNumber.charAt(1)=='9' && mobileNumber.charAt(2)=='1')
				mobileNumber=mobileNumber.substring(3);
			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
			int OTP=((ThreadLocalRandom.current().nextInt())*1000000)%1000000;
			OTP=Math.abs(OTP);
			@SuppressWarnings("deprecation")
			String mString=Integer.toString(OTP)+" is your emailo verification code\n Happy Emailo-ing (✷‿✷) \n"+new Date().toLocaleString()+"\nThis OTP is valid for 10 minutes";
			Message message=Message.creator(new PhoneNumber("+91"+mobileNumber),new PhoneNumber("+12406164170"),mString).create();
			System.out.println(message.getSid());
			return Integer.toString(OTP);
		}
		
		public static boolean verifyOTP(String generated,String received)
		{
			return generated.equals(received);
		}
}
