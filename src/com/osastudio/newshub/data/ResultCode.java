package com.osastudio.newshub.data;

public interface ResultCode {

   public static final int RESULT_NONE = 0;
   public static final int RESULT_OK = 1;
   
   public static final int RESULT_PARAMETER_ERROR = 100;
   public static final int RESULT_PARAMETER_TYPE_ERROR = 101;
   public static final int RESULT_INVALID_VALIDATE_CODE = 102;
   public static final int RESULT_VALIDATE_FAILURE = 103;
   public static final int RESULT_ILLEGAL_VALIDATE_CODE_STATUS = 104;
   public static final int RESULT_VALIDATE_CODE_ALREADY_EXISTS = 105;
   public static final int RESULT_REGISTER_FAILURE = 106;
   public static final int RESULT_NO_DEVICE_IDENTIFIER = 107;
   public static final int RESULT_PAYMENT_NEEDED = 108;
   public static final int RESULT_ADD_SUBACCOUNT_FAILURE = 109;
   
   public static final int RESULT_UNKNOWN_ERROR = -1;
   
}
