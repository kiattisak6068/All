package controllers;


import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import java.util.*;
import models.*;

import views.html.*;
import com.fasterxml.jackson.databind.*;

public class HomeController extends Controller {

	 public Result register() {
        Users users=new Users("","","","");
        return ok(signup.render("",users));
    }
	public Result upadmin() {
        return ok(signupadmin.render(""));
    }
	
	 public Result index() {

        String user = session("user");
        String admin=session("admin");
        if(user != null) {
            return ok(home.render(""));
        }else if(admin!=null){
            return ok(home.render(""));
        } else {
            return ok(home.render(""));
        }
    }

	 public Result save_register(){
        DynamicForm formchk = Form.form().bindFromRequest();

        Form<Users> form = Form.form(Users.class).bindFromRequest();
        int i=0;
        try {
            Users us = form.get();
            if (us.pwd.length()>=8) {
                if(formchk.get("pwd2").equals(formchk.get("pwd"))){
                    for (Users p:Users.find.all()){
                        if (p.contains(us.email)){
                            i++;
                        }
                    }
                 if (i>0){
                        String fail="อีเมลล์นี้มีผู้ใช้งานแล้ว";
                        return ok(signup.render(fail,us));
                    }else {
                        us.save();
                        return ok(home.render(""));
                    }
                }else {
                    String fail="รหัสผ่านไม่ตรงกัน";
                    return ok(signup.render(fail,us));
                }
            }else {
                String fail="รหัสผ่านน้อยกว่า 8 ตัว";
                return ok(signup.render(fail,us));
            }


        } catch (Exception ex) {
			//
        }
 return ok();
    }
	

 public Result saveadmin(){
        DynamicForm formchk = Form.form().bindFromRequest();

        Form<Employees> form = Form.form(Employees.class).bindFromRequest();
        int i=0;
        try {
            Employees us = form.get();
            if (us.pwd.length()>=8) {
                if(formchk.get("pwd2").equals(formchk.get("pwd"))){
                    for (Employees p:Employees.find.all()){
                        if (p.contains(us.email)){
                            i++;
                        }
                    }
                 if (i>0){
                        String fail="อีเมลล์นี้มีผู้ใช้งานแล้ว";
                        return ok(signupadmin.render(fail));
                    }else {
                        us.save();
                        return ok(home.render(""));
                    }
                }else {
                    String fail="รหัสผ่านไม่ตรงกัน";
                    return ok(signupadmin.render(fail));
                }
            }else {
                String fail="รหัสผ่านน้อยกว่า 8 ตัว";
                return ok(signupadmin.render(fail));
            }


        } catch (Exception ex) {
			//
        }
 return ok();
    }
	
	public Result loginsubmit() {
        int chk=0;
         Form<Users> form = Form.form(Users.class).bindFromRequest();
         Users user=form.get();

        for (Users p:Users.find.all()){
            if (p.chklogin(user.email)&&p.chklogin(user.pwd)){
            chk=1;
            }
        }
        if(chk==1){
            session().clear();
            session("user", user.email);
            return ok(home.render(""));
        }

        //for admin
        DynamicForm ad=Form.form().bindFromRequest();
        int chkadmin=0;


        for (Employees p:Employees.find.all()){
            if (p.chkadmin(ad.get("email")) && p.chkadmin(ad.get("pwd"))){
                chkadmin=1;
            }
        }
        if(chkadmin==1){
            session().clear();
            session("admin", ad.get("email"));
            return ok(home.render(""));
        }

        return ok(home.render(""));

    }
	

    public Result getname(String id) {
		  String[] x=id.split(",");
          String email=x[0];
          String pwd=x[1];
		for (Employees p:Employees.find.all()){
            if (p.chkadmin(email) && p.chkadmin(pwd)){
               return ok("admin");
            }
        }
		
		 try{
       
          Users userget = Users.find.where().eq("email", email).findUnique();
          Users userdb=Users.find.byId(userget.ID);	
      
          if (userdb.email.equals(email)&&userdb.pwd.equals(pwd)){
              return ok("user");
          }
      }catch (Exception ex){
          return badRequest("error");
      }
	  
		
	  
        return ok("No user");
		
	}
	
	public Result setname(String id){
			String[] a = id.split(",");
				String fname = a[0];
				String lname = a[1];
				String email = a[2];
				String pwd = a[3];
				String repwd = a[4];
			int i=0;
			try {
				
            if (pwd.length()>=8) {
                if(pwd.equals(repwd)){
                    for (Users p:Users.find.all()){
                        if (p.contains(email)){
                            i++;
                        }
                    }
                 if (i>0){
                        String fail="This email address is already in use.";
                        return ok(fail);
                    }else {
						Users us = new Users(fname,lname,email,pwd);
                        us.save();
                        return ok("Register success.");
                    }
                }else {
                    String fail="Passwords do not match.";
                    return ok(fail);
                }
            }else {
                String fail="Password at least 8 characters";
                return ok(fail);
            }


        } catch (Exception ex) {
			//
        }
		return ok();	
	}
			
    
    public Result getuser() {
			List<Users> us = Users.find.all();
			JsonNode json = Json.toJson(us);
			return ok(Json.prettyPrint(json));
		}
	
    public Result logout() {
        session().remove("admin");
        session().remove("user");
        session().remove("userId");
        return ok(home.render(""));
    }

}
