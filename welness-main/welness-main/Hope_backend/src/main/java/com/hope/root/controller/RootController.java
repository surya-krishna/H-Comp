package com.hope.root.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hope.root.model.DeleteUserModel;
import com.hope.root.model.LabModel;
import com.hope.root.model.TestDataOp;
import com.hope.root.model.UserModel;
import com.hope.root.model.UserOp;
import com.hope.root.model.VerifyIp;
import com.hope.root.model.VerifyOp;
import com.hope.root.service.RootService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/register")
public class RootController {

    @Autowired
    private RootService userService;

    @RequestMapping(value="/usersList", method = RequestMethod.POST)
    public List<UserModel> listUser(){
    	
        return userService.findAllUsers();
    }

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    /*@PreAuthorize("hasRole('ADMIN')")

    //@Secured("ROLE_USER")
    @PreAuthorize("hasRole('SELLER')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }*/


    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public UserOp saveUser(@RequestBody UserModel user){
    	user.setRole("user");
        return userService.save(user);
    }
    
    @RequestMapping(value="/signupDoctor", method = RequestMethod.POST)
    public UserOp saveDoctor(@RequestBody UserModel user){
    	user.setRole("doctor");
        return userService.saveDoctor(user);
    }
    
    @RequestMapping(value="/signupLab", method = RequestMethod.POST)
    public UserOp saveLab(@RequestBody LabModel user){
        return userService.saveLab(user);
    }

    @RequestMapping(value="/editUser", method = RequestMethod.POST)
    public UserOp editUser(@RequestBody UserModel user){
        return userService.editUser(user);
    }

    
    @RequestMapping(value="/deleteUser", method = RequestMethod.POST)
    public UserOp deleteUser(@RequestBody DeleteUserModel user){
        return userService.deleteUser(user);
    }

    
    
    /*@RequestMapping(value="/vendorsignup", method = RequestMethod.POST)
    public UserOp saveVendor(@RequestBody VendorModel user){
        return userService.save_vendor(user);
    }*/
    
    @RequestMapping(value="/verifyOtp", method = RequestMethod.POST)
    public VerifyOp saveVendor(@RequestBody VerifyIp verifyIp){
        return userService.verifyOTP(verifyIp);
   }
    
    @RequestMapping(value="/getTestData", method = RequestMethod.GET)
    public TestDataOp getTestData(){
        return userService.getTestData();
    }




}
