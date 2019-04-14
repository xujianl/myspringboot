package com.example.demo;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private AyUserService ayUserService;
    @Test
    public void contextLoads() {
    }
    @Test
    public void mysqlTest(){
        String sql = "select id,name,password from ay_user";
        List<AyUser> userList = (List<AyUser>)jdbcTemplate.query(sql,new RowMapper<AyUser>() {
            @Override
            public AyUser mapRow(ResultSet rs, int i) throws SQLException {
                AyUser user = new AyUser();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
        System.out.println();
        for (AyUser user:userList){
            System.out.println("[id]" + user.getId() + "[name]" + user.getName());
        }
    }

    @Test
    public void testRepository(){
        List<AyUser> ayUserList = ayUserService.findAll();
        System.out.println("findAll()"+ayUserList.size());
        for (AyUser ayUser : ayUserList){
            System.out.println("findAll()" + ayUser.getName());
        }
        AyUser ayUser = ayUserService.findById("2");
        System.out.println("findById" + ayUser.getName());

        ayUserService.delete("3");
        AyUser ayUser1 = new AyUser();
        ayUser1.setId("3");
        ayUser1.setName("李丽红");
        ayUser1.setPassword("123456");
        ayUserService.save(ayUser1);

        List<AyUser> userList = ayUserService.findByName("李丽红");
        for (AyUser ayUser2 : userList){
            System.out.println("findByName " + ayUser2.getId() + ayUser2.getName());
        }
        Assert.isTrue(userList.get(0).getName().equals("李丽红"),"data error!");

        List<AyUser> userList1 = ayUserService.findByNameLike("阿%");
        for (AyUser ayUser2 : userList1){
            System.out.println("findByNameLike " + ayUser2.getId() + ayUser2.getName());
        }

        //根据id列表查询
        List<String> ids = new ArrayList<>();
        ids.add("1");
        ids.add("3");
        List<AyUser> userList2 = ayUserService.findByIdIn(ids);
        for (AyUser ayUser3 : userList2){
            System.out.println("findByIdIn " + ayUser3.getId() + ayUser3.getName());
        }
        //分页查询数据
        PageRequest pageRequest = PageRequest.of(0,10);
        Page<AyUser> userList5 = ayUserService.findAll(pageRequest);
        System.out.println("page findAll()"+userList5.getTotalPages() + "/" + userList5.getSize());
    }
}
