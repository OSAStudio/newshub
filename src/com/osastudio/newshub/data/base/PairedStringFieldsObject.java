package com.osastudio.newshub.data.base;


public abstract class PairedStringFieldsObject extends NewsBaseObject {

   protected String id;
   protected String name;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

}
