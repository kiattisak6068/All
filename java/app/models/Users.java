package models;



import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Users extends Model {

    @Id
    public Integer ID;
	public String fname;
	public String lname;
	public String email;
    public String pwd;


    public boolean contains(String key){
        if(key.equals(email)){
            return true;
        }
        return false;
    }
    public boolean chklogin(String key){
        if(key.equals(email) || key.equals(pwd)){
            return true;
        }
        return false;
    }

    public Users(String fname,String lname,String email, String pwd) {
		this.fname = fname;
		this.lname = lname;
        this.email = email;
        this.pwd = pwd;
    }
 
    public static Model.Finder<Integer, Users> find = new Model.Finder<>(Users.class);

}