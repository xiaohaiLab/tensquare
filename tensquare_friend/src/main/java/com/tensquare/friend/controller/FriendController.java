package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author "zhouhai"
 * @Date2019/3/719:16
 **/
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type) {

        //验证是否登录，并且拿到当前登录的用户id
        Claims token = (Claims) request.getAttribute("claims_user");

        if (token == null) {
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //得到当前登录的用户id
        String userid = token.getId();
        //判断是添加好友还是非好友
        if (type!=null ) {
            if (type.equals("1")) {
                //添加好友
                int flag = friendService.addFriend(userid,friendid);
                if (flag==0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1) {
                    userClient.updatefanscountandfollowcount(userid,friendid,1);
                    return new Result(false, StatusCode.OK, "添加成功");
                }
            }else if(type.equals("2")){
                //添加非好友




            }
            return new Result(false, StatusCode.ERROR, "参数异常");
        }else{
            return new Result(false, StatusCode.ERROR, "参数异常");
        }


    }

}
