package com.say.shiro;


import com.say.bean.User;
import com.say.jwt.JWTToken;
import com.say.jwt.JWTUtil;
import com.say.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: MyShiroRealm
 * @Description: 认证授权配置
 * @Author: lel
 * @Date: 2020/10/6 10:00
 * @Version: v1.0
 */

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    /**
     *
     * @param principalCollection:PrincipalCollection是一个身份集合,因为我们可以在Shiro中同时配置多个Realm
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String number = JWTUtil.getNumber(principalCollection.toString());
        User list =  userService.getUserByNumber(number);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        return info;

    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * 重写认证方法
     * @param authenticationToken：用于收集（来自控制器端的）用户提交的身份（如用户名）及凭据（如密码）。
     * @return
     * @throws AuthenticationException：该异常会直接通过控制器返回
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————say————");
        User userInfo = null;
        //获取用户的输入的账号.
        String token = (String) authenticationToken.getCredentials();
        String number = JWTUtil.getNumber(token);

        if (number == null || !JWTUtil.verify(token, number)) {
            throw new AuthenticationException("token认证失败！");
        }
        //通过username从数据库中查找 User对象，如果找到，没找到.由于mybati逆向工程自动提供给我们的方法是返回一个list，所以要从这个list中取数据
        User list = userService.getUserByNumber(number);
        if(list == null){
            return null;
        }else {
            userInfo = list;
        }
        if (userInfo == null) {
            return null;
        }
        /**
         *  userInfo:用户名（来自用户输入）
         *  userInfo.getPassword(): 密码（来自数据库）
         *  ByteSource.Util.bytes(userInfo.getCredentialsSalt()): salt=username+salt
         *  getName() :realm name
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                token,
                token,
                // ByteSource.Util.bytes(userInfo.getCredentialsSalt()),
                getName()
        );
        return authenticationInfo;

    }
}
