package com.monty.community.controller;

import com.monty.community.dto.AccessTokenDTO;
import com.monty.community.dto.AccessTokenDTO;
import com.monty.community.dto.GithubUser;
import com.monty.community.mapper.UserMapper;
import com.monty.community.model.User;
import com.monty.community.provider.GithubProvider;
import com.monty.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                                   HttpServletRequest httpservletrequest,
                            HttpServletResponse httpServletResponse){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setStatus(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getGithubUser(accessToken);
        if(githubUser!=null){
//            登陆成功操作
            User user=new User();
            user.getName();
            user.setName(githubUser.getLogin());
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token= UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.login(user);
            httpServletResponse.addCookie(new Cookie("token",token));
//            httpservletrequest.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
//            登陆失败操作
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logOut(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ){
//        delete session
        httpServletRequest.getSession().removeAttribute("user");
//        delete token cookie
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
    }
}
