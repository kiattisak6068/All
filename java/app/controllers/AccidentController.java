package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import java.util.*;
import models.*;
import java.text.*;
import java.util.stream.Collectors;
import views.html.*;

import com.fasterxml.jackson.databind.*;

public class AccidentController extends Controller {


	
	public Result getform(){
		String user = session("user");
		String admin = session("admin");
        if(user != null) {
            return ok(form.render());
        }else if(admin !=null){
			 return ok(form.render());
		}else{
            return ok(home.render(""));
        }
	}
	
	 public Result saveForm() {

        Form<Accident> form = Form.form(Accident.class).bindFromRequest();
        try {
            Accident acc = form.get();
                Date date = Calendar.getInstance().getTime();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
                String today = formatter.format(date);
                acc.time=today;
                acc.save();
            
        } catch (Exception ex) {
			//
        }
       
        return ok(listaccident.render());
    }
	
	
		
    public Result get() {
			List<Accident> acc = Accident.find.all();
			JsonNode json = Json.toJson(acc);
			return ok(Json.prettyPrint(json));
		}
		
	public Result getlist(){
		String user = session("user");
		String admin = session("admin");
        if(user != null) {
            return ok(listaccident.render());
        }else if(admin !=null){
			 return ok(listaccident.render());
		}else{
            return ok(home.render(""));
        }
		
	}

	public Result search(Integer id) {
		Accident acc = Accident.find.byId(id); 
		if (acc == null) {
			return badRequest("no accident id: "+id);
		}
		return ok(Json.toJson(acc));
	}
	
	
	public Result mdata(){
		String admin = session("admin");
		if(admin !=null){
			 return ok(managementdata.render());
		}else{
            return ok(home.render(""));
        }		
		
	}
	public Result muser(){
		String admin = session("admin");
		if(admin !=null){
			return ok(managementuser.render());
		}else{
            return ok(home.render(""));
        }		
	}
	
	public Result deletedata(Integer id) {
		Accident acc = Accident.find.byId(id);
		acc.delete();
		return ok(managementdata.render());
	
	}
	public Result deleteuser(Integer id) {
		Users user = Users.find.byId(id);
		user.delete();
		return ok(managementuser.render());
		
	}
	
	public Result getshow(Integer id){
		Accident acc = Accident.find.byId(id);
		String user = session("user");
		String admin = session("admin");
        if(user != null) {
            return ok(map.render(acc));
        }else if(admin !=null){
			 return ok(map.render(acc));
		}else{
            return ok(home.render(""));
        }
		
	}
	
	public Result savePost(String str) {
		String[] a = str.split(",");
			Accident acc = new Accident();
			acc.email = a[0];
			acc.description = a[1];
			acc.lat = a[2];
			acc.lng = a[3];
			
			Date date = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss a");
            String today = formatter.format(date);
            acc.time=today;

			acc.save();
			return ok("Accident report success ");
		
	}

}
