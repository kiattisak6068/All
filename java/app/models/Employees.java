package models;



import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Employees extends Model {

    @Id
    public Integer ID;
	public String email;
    public String pwd;


		
    public boolean contains(String key){
        if(key.equals(email)){
            return true;
        }
		 return false;
    }
     public boolean chkadmin(String key){
        if (key.equals(email) || key.equals(pwd)){
            return true;
        }
        return false;
    }

 
    public static Model.Finder<Integer, Employees> find = new Model.Finder<>(Employees.class);

}