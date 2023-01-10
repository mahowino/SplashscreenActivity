package com.example.splashscreenactivity.constants;

public class MpesaConstants {

        public static final int CONNECT_TIMEOUT = 60 * 1000;

        public static final int READ_TIMEOUT = 60 * 1000;

        public static final int WRITE_TIMEOUT = 60 * 1000;

        public static final String BASE_URL = "https://api.safaricom.co.ke/";


        public static final String BUSINESS_SHORT_CODE = "7483499";
        public static final String PASSKEY = "eba2c28ef6c07b569925fc87ae64258c3dba28b76e02caa8a11811cc5e27cc85";
        public static final String TRANSACTION_TYPE = "CustomerBuyGoodsOnline";
        public static final String PARTYB = "5485689"; //same as business shortcode above
        public static final String CALLBACKURL = "https://us-central1-bigfamily-b7c30.cloudfunctions.net/callbackUrl?userid="+FirebaseInit.mAuth.getUid();
        public static final String DARAJA_CONSUMER_KEY="C91yWYLAoL7ii9FFLzLyclNCcFLWSKIl";
        public static final String DARAJA_CONSUMER_SECRET="CwE4E2JFQzugnMdQ";

        //You should get these values from the earlier post Part 1

}

