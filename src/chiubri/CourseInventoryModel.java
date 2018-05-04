////////////////////////////////////////////////////////////////////////////////
// CourseInventoryModel.java
// ============
//
//
// AUTHOR: Brian Chiu
// CREATED: 2018-04-05
// UPDATED: 2018-04-27
////////////////////////////////////////////////////////////////////////////////
package chiubri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CourseInventoryModel {
    
    //member var
    protected ArrayList<Course> courses;
    private Course selectedCourse = null;
    private ArrayList<String> categories;
    private String id;
    
    //ctor
    public CourseInventoryModel() {
        
        courses = new ArrayList<Course>(); //create array
        
        //Creates an arraylist of categories for combobox
        categories = new ArrayList<>();
        categories.add("DATABASE");
        categories.add("MATH");
        categories.add("SYSTEM");
        categories.add("INFORMATION");
        categories.add("PROGRAMMING");
    }
    
    //parse DAT file
    public void readCourseFile(File file) {
        
        //validate param
        if (file == null)
                return;

        ArrayList<String> lines = new ArrayList<>();
        String line;
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
          //copy all lines from file to memory  
          while((line = reader.readLine()) != null) {    
              lines.add(line);
          }
            
        } catch (IOException e) {
            System.out.println("ERROR Failed to read file");
        }
        
        courses.clear();
        for (int i = 0; i < lines.size(); i++) {
            
            line = lines.get(i);
            String[] tokens = line.split(";");
            
            //assign tokens to Courses Object
            if (tokens.length == 4) {
                
                //trim then set token values to Courses
                Course c = new Course(tokens[0].trim(), tokens[1].trim(),
                        Integer.parseInt(tokens[2].trim()), tokens[3].trim());
          
                courses.add(c);
            }
            
        }
    
    }
    
    //save DAT file
    public void saveCourseFile(File file){
        
        //writes to the file
        try(BufferedWriter wr = new BufferedWriter(new FileWriter (file))){
            Course c = new Course();
            for (int i = 0; i < courses.size(); i++) {
                c = courses.get(i);
                wr.write(c.getId() + " ; " + c.getTitle() + " ; " 
                        +  c.getCredit() + " ; " +  c.getCategory());
                wr.newLine();
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //returns size of array
    public int getCourseCount() {
        
        return courses.size();
    }
    
    public ArrayList<Course> getCourses() { 
        return courses;
    }
    
    
    public ArrayList<Course> findCoursesById(String id){
        ArrayList<Course> courses = new ArrayList<>();
        
        //validation
        if(id == null || id.isEmpty()) 
            return courses; //returns empty array
        
        //make all char are uppercase
        String keyword = id.toUpperCase();
        //find all courses containing the keyword
        for(int i = 0; i< this.courses.size(); i++) {
            
            Course c = this.courses.get(i);
            if (c.getId().contains(keyword)) 
                courses.add(c); // found it and then add it
        }
        return courses;
    }
    
    //validates ID
    public boolean validateId(String id) {
        
        //regexp
        final String PATTERN = "^[A-Za-z]{4}[0-9]{5}$";
        if (id != null && id.matches(PATTERN))
            return true;
        else
            return false;  
    }
    public Course getSelectedCourse(){
        return selectedCourse;
    }
    public int getSelectedCourseIndex(){
        return this.courses.indexOf(selectedCourse);
    }
    public void setSelectedCourse(Course c) { 
        this.selectedCourse = c;
    }
    public void removeCourse(Course c){ 
        courses.remove(c);
    } 
    public void updateCourse(String id, String title, int credit, String cat){
        
        Course course = getCourseById(id);
        if (course != null)
            course.set(id, title, credit, cat);
    }
    public Course getCourseById (String id) {
        
        for (int i = 0; i < courses.size(); i++){
            if(id.equals(courses.get(i).getId()))
                return courses.get(i);
        }
        return null; //not found
    }
    public void addCourse (String id, String title, int credit, String cat) {
        
        Course course = new Course  (id,  title,  credit,  cat);
        courses.add(course);
        setNewCourseId(id);  
    }
    public ArrayList<String> getCategories() {
        
        return categories;
    }
    public void setNewCourseId(String id) {
        this.id = id;
    }
    public String getNewCourseId() {
        return id;
    }
    
    public int getCourseIndex(String id) {
        
        for (int i = 0; i < courses.size(); i++)
            if (courses.get(i).getId() == id)
                return i;
        
        return -1;
    }
}
