package models;



import java.util.*;
import javax.persistence.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Accident extends Model {

    @Id
    public Integer ID;
	public String email;
	public String description;
	public String lat;
	public String lng;
    public String time;
 
    public static Model.Finder<Integer, Accident> find = new Model.Finder<>(Accident.class);

}