package demo.variables;
public class Person implements java.io.Serializable{  
      
    private static final long serialVersionUID = 8379071759772449529L;  
      
    private Integer id;  
    private String name;  
    private String education;  
      
    public Integer getId() {  
        return id;  
    }  
    public void setId(Integer id) {  
        this.id = id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
      
}  