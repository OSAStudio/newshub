package com.osastudio.newshub.data;

public abstract class SingleStringFieldObject extends NewsBaseObject {

   protected String field;

   public String getValue() {
      return this.field;
   }

   public void setValue(String value) {
      this.field = value;
   }

}
