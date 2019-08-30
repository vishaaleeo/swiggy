package swiggy.controller;

//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import swiggy.domain.User;
import swiggy.services.UserDAOImplementaion;



/*
 *To control and regulate the paths
 */
@Controller
public class UserController {

    private final static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserDAOImplementaion userDAOImplementaion;



    @GetMapping("/signup")
    public ModelAndView getSignup() {
        return new ModelAndView("signup");
    }

    @PostMapping(value="/signup",produces = MediaType.APPLICATION_JSON_VALUE)
   // @SendTo("/topic/user")
    public ResponseEntity createUser(@RequestBody User user) {

        String result=userDAOImplementaion.createUser(user);
        if(result.equals("success")) {

            logger.info("new user created");
            return new ResponseEntity("{\"message\":\""+"User created successfully\"}",HttpStatus.valueOf(201));
        }

        else {
            logger.info("error in creating user");
            return new ResponseEntity("{\"message\":\""+result+"\"}",HttpStatus.valueOf(400));
        }
    }


    @PostMapping(value="/login" , produces = MediaType.APPLICATION_JSON_VALUE)
   // @SendTo("/topic/user")
    public ResponseEntity login(@RequestBody User user) {

        //System.out.println(user.toString());
        User loginUser = userDAOImplementaion.loginUser(user);
        if(loginUser!=null) {

            Claims claims = Jwts.claims()
                    .setSubject(loginUser.getUserIdentifier().toString());

            String jwtToken=Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256,"secret")
                    .compact();
            //String jwtToken = Jwts.builder().setSubject(loginUser.getUserIdentifier().toString()).claim("roles", "user").setIssuedAt(new Date())
               //     .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            String jsonString="{ \"token\" : \""+jwtToken+"\"}";
            //JSONObject jsonObject = new JSONObject();
            //jsonObject.put("token",jwtToken);
            //System.out.println(jsonObject.toString());

            logger.info("user login success");
            return new ResponseEntity(jsonString, HttpStatus.valueOf(200));

            //System.out.println(loginUser.toString());
            //return new Response(loginUser.toString());
        }
        else {
            String jsonString="{\"message\" : \"invalid user login\"}";
            //return new Response("invalid user login");
            logger.error("invalid user login");
            return new ResponseEntity(jsonString,HttpStatus.valueOf(200));
        }
    }

   /* @PostMapping("/user/login")
    public ResponseEntity postlogin(@RequestBody User user){

        System.out.println("login");
        AuthUser principal=(AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity("login "+principal.getUserIdentifier(),HttpStatus.OK);
    }
*/
    @PostMapping (value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody User user) {

    String result=userDAOImplementaion.updateUser(user);
    logger.info("updation"+result);
    return new ResponseEntity(result,HttpStatus.valueOf(200));
    }




    @PostMapping(value="/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(@RequestBody User user) {

       String result= userDAOImplementaion.deleteUser(user);

       logger.info("deleteion"+result);
       return new ResponseEntity(result,HttpStatus.valueOf(200));
    }

    @GetMapping(value="/display",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayUser(){
        return new ResponseEntity(userDAOImplementaion.getUserDetails(),HttpStatus.OK);
    }



}
