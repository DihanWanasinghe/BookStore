package org.example.models;

public class Author {

   //properties of the Author
   private String authorId;
   private String name;
   private String biography;
   private String email;

   public Author( String name, String biography, String email) {
      this.name = name;
      this.biography = biography;
      this.email = email;
   }
   
   public Author(){}

   public String getAuthorId() {
      return authorId;
   }

   public void setAuthorId(String authorId) {
      this.authorId = authorId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBiography() {
      return biography;
   }

   public void setBiography(String biography) {
      this.biography = biography;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public String toString() {
      return "Author{" +
              "authorId=" + authorId +
              ", name='" + name + '\'' +
              ", biography='" + biography + '\'' +
              ", email='" + email + '\'' +
              '}';
   }
}
