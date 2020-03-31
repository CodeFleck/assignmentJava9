package com.daniel.assignment.dao;

import com.daniel.assignment.model.ApplicationUser;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class ApplicationUserDao {

    private static final Map<Long, ApplicationUser> userMap = new HashMap<>();

    static {
        initUsers();
    }

    private static void initUsers() {
        ApplicationUser user1 = new ApplicationUser(1, "Smith", "abc123");
        ApplicationUser user2 = new ApplicationUser(2, "Allen", "abc123");
        ApplicationUser user3 = new ApplicationUser(3, "Jones", "abc123");

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);
    }

    public static ApplicationUser getApplicationUser(String userNo) {
        return userMap.get(userNo);
    }

    public static ApplicationUser addApplicationUser(ApplicationUser user) {
        userMap.put(user.getId(), user);
        return user;
    }

    public static ApplicationUser updateApplicationUser(ApplicationUser emp) {
        userMap.put(emp.getId(), emp);
        return emp;
    }

    public static void deleteApplicationUser(String userId) {
        userMap.remove(userId);
    }

    public static List<ApplicationUser> getAllApplicationUsers() {
        Collection<ApplicationUser> c = userMap.values();
        List<ApplicationUser> list = new ArrayList<ApplicationUser>();
        list.addAll(c);
        return list;
    }

    public ApplicationUser findByUsername(String username) {
        for (Map.Entry<Long, ApplicationUser> entry : userMap.entrySet()) {
            if (entry.getValue().getUsername().equals(username)){
                return entry.getValue();
            }
        }
        return null;
    }

    public static ApplicationUser save(ApplicationUser user) {
        userMap.put(user.getId(), user);
        return user;
    }
}
