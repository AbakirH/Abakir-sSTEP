
package com.google.sps.data;

// This is the structure of a contact class that would be used to easily access the info in the database
public final class Contact {

    private final String name;
    private final String email;
    private final long number;
    private final long id;
  
    public Contact(String name, String email, long number, long id) {
      this.name = name;
      this.email = email;
      this.number = number;
      this.id = id;
    }
  }