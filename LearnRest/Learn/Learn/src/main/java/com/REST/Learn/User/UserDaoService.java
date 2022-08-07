package com.REST.Learn.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	
	private  int counter =3 ;
	
	static {
		
		users.add(new User(1,"Harshit",new Date()));
		users.add(new User(2,"OK ",new Date()));
		users.add(new User(3,"Hello",new Date()));

		
		
	}
	
	public List<User> findAll() {
		return users;
		
	}
	
	public User save(User user) {
		if(user.getId()==null)
		{
			user.setId(counter++);
		}
		users.add(user);
		return user;
		
	}
	
	public User findOne(int id) {
		
		for(User user:users) {
			if(user.getId()==id) {
				return user;
			}
		}
		
		return null;
	}
	
public User deleteById(int id) {
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User user = it.next();
			if(user.getId()==id) {
				it.remove();
				return user;
			}
		}
		
		return null;
	}
	
	

}
