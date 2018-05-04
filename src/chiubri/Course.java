////////////////////////////////////////////////////////////////////////////////
// Course.java
// ============
//
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import java.util.Objects;

public class Course {
    
    private String id;
    private String title;
    private int credit;
     
    private String category;
    
    public Course() {
        this("","",0,"NONE");
    }
    public Course(String id, String title, int credit, String category){
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = category;
    }
    public enum Category{
    DATABASE, MATH, SYSTEM, INFORMATION, PROGRAMMING;
    }
    public void set(String id, String title, int credit, String category) {
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = category; 
    }
    
    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String toString() {
        
        return "Course: " + id + " " + title + " " + credit + " " + category;
    }
    public boolean equals(Object o) {
        
        //checks if the course id are the same 
        //return true if they are equal
        //checks if o is a Course Object
        if (o instanceof Course) {
            
            Course course = (Course)o; // downcast
            // return id.equals(car.id); <-- if id was a String
            if (id == course.getId()) 
                return true;
            else 
                return false;
                        
        } else
            return false;
        
    }
    //hashcode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
}
