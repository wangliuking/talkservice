package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.config.ServerConfig;
import com.unionman.springbootsecurityauth2.controller.TreeUtil;
import com.unionman.springbootsecurityauth2.domain.Token;
import com.unionman.springbootsecurityauth2.dto.UserDTO;
import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.entity.User;
import com.unionman.springbootsecurityauth2.entity.WebUser;
import com.unionman.springbootsecurityauth2.enums.ResponseEnum;
import com.unionman.springbootsecurityauth2.enums.UrlEnum;
import com.unionman.springbootsecurityauth2.mapper.UserMapper;
import com.unionman.springbootsecurityauth2.service.StructureService;
import com.unionman.springbootsecurityauth2.service.UserService;
import com.unionman.springbootsecurityauth2.utils.BeanUtils;
import com.unionman.springbootsecurityauth2.utils.RedisUtil;
import com.unionman.springbootsecurityauth2.vo.UserVO;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.unionman.springbootsecurityauth2.config.OAuth2Config.CLIENT_ID;
import static com.unionman.springbootsecurityauth2.config.OAuth2Config.CLIENT_SECRET;
import static com.unionman.springbootsecurityauth2.config.OAuth2Config.GRANT_TYPE;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StructureService structureService;

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public void addUser(UserDTO userDTO) {
        User userPO = new User();
        User userByAccount = userRepository.findUserByAccount(userDTO.getAccount());
        if (userByAccount != null) {
            //此处应该用自定义异常去返回，在这里我就不去具体实现了
            try {
                throw new Exception("This user already exists!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userPO.setCreatedTime(System.currentTimeMillis());
        //添加用户角色信息
        Role rolePO = roleService.findById(userDTO.getRoleId());
        userPO.setRole(rolePO);
        BeanUtils.copyPropertiesIgnoreNull(userDTO, userPO);
        userRepository.save(userPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer id) {
        User userPO = userRepository.findById(id).get();
        if (userPO == null) {
            //此处应该用自定义异常去返回，在这里我就不去具体实现了
            try {
                throw new Exception("This user not exists!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userRepository.delete(userPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserDTO userDTO) {
        User userPO = userRepository.findById(userDTO.getId()).get();
        if (userPO == null) {
            //此处应该用自定义异常去返回，在这里我就不去具体实现了
            try {
                throw new Exception("This user not exists!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            BeanUtils.copyPropertiesIgnoreNull(userDTO, userPO);
            //修改用户角色信息
            Role rolePO = roleService.findById(userDTO.getRoleId());
            userPO.setRole(rolePO);
            userRepository.saveAndFlush(userPO);
        }
    }*/

    @Override
    public void deleteUser(String userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteWebUser(String userId) {
        userMapper.deleteWebUser(userId);
    }

    @Override
    public void addWebUser(WebUser webUser) {
        /*BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        webUser.setPassword(bCryptPasswordEncoder.encode(webUser.getPassword()));*/
        userMapper.insertWebUser(webUser);
    }

    @Override
    public void updateWebUser(WebUser webUser) {
        /*if(webUser.getPassword() != null && !"".equals(webUser.getPassword())){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            webUser.setPassword(bCryptPasswordEncoder.encode(webUser.getPassword()));
        }*/
        userMapper.updateWebUser(webUser);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectUserList(Map<String, Object> param,String structure) {
        List<User> list = userMapper.selectUserList(param);
        int total = userMapper.selectUserListCount(param);

        List<Node> nodeList = structureService.selectAll(param);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getInfiniteLevelTree(nodeList, Integer.parseInt(structure));

        for(User user : list){
            for(Node node : nodeList){
                if(node.getId() == user.getPower()){
                    user.setPowerName(node.getFullName());
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectWebUserList(Map<String, Object> param,String structure) {
        List<WebUser> list = userMapper.selectWebUserList(param);
        int total = userMapper.selectWebUserListCount(param);

        List<Node> nodeList = structureService.selectAll(param);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getInfiniteLevelTree(nodeList, Integer.parseInt(structure));

        for(WebUser webUser : list){
            for(Node node : nodeList){
                if(webUser.getPower() == node.getId()){
                    webUser.setPowerName(node.getFullName());
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public Map<String,Object> selectManageUserId(Map<String,Object> param){
        Map<String,Object> map = new HashMap<>();
        List<String> list = userMapper.selectManageUserId(param);
        for(String key : list){
            map.put(key,"");
        }
        return map;
    }

    @Override
    public ResponseVO<User> findUserById(String userId) {
        User userPO = userMapper.findUserById(userId);
        //UserVO userVO = new UserVO();
        //BeanUtils.copyPropertiesIgnoreNull(userPO, userVO);
        return ResponseVO.success(userPO);
    }

    @Override
    public ResponseVO<WebUser> findWebUserById(String userId) {
        WebUser webUser = userMapper.findWebUserById(userId);
        return ResponseVO.success(webUser);
    }

    @Override
    public ResponseVO login(UserDTO userDTO) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", CLIENT_ID);
        paramMap.add("client_secret", CLIENT_SECRET);
        paramMap.add("username", userDTO.getUsername());
        paramMap.add("password", userDTO.getPassword());
        paramMap.add("grant_type", GRANT_TYPE[0]);
        Token token = null;
        try {
            //因为oauth2本身自带的登录接口是"/oauth/token"，并且返回的数据类型不能按我们想要的去返回
            //但是我的业务需求是，登录接口是"user/login"，由于我没研究过要怎么去修改oauth2内部的endpoint配置
            //所以这里我用restTemplate(HTTP客户端)进行一次转发到oauth2内部的登录接口，比较简单粗暴
            token = restTemplate.postForObject(serverConfig.getUrl() + UrlEnum.LOGIN_URL.getUrl(), paramMap, Token.class);
            UserVO loginUserVO = redisUtil.get(token.getValue(), UserVO.class);
            if (loginUserVO != null) {
                //登录的时候，判断该用户是否已经登录过了
                //如果redis里面已经存在该用户已经登录过了的信息
                //我这边要刷新一遍token信息，不然，它会返回上一次还未过时的token信息给你
                //不便于做单点维护
                token = oauthRefreshToken(loginUserVO.getRefreshToken());
                redisUtil.deleteCache(loginUserVO.getAccessToken());
            }
        } catch (RestClientException e) {
            try {
                //e.printStackTrace();
                //此处应该用自定义异常去返回，在这里我就不去具体实现了
                //throw new Exception("username or password error");
                return ResponseVO.error(ResponseEnum.PASSWORDISWRONG);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (token != null) {
            //这里我拿到了登录成功后返回的token信息之后，我再进行一层封装，最后返回给前端的其实是LoginUserVO
            UserVO loginUserVO = new UserVO();
            WebUser userPO = userMapper.findWebUserById(userDTO.getUsername());
            BeanUtils.copyPropertiesIgnoreNull(userPO, loginUserVO);
            loginUserVO.setPassword(userPO.getPassword());
            loginUserVO.setPower(userPO.getPower());
            loginUserVO.setAccessToken(token.getValue());
            loginUserVO.setAccessTokenExpiresIn(token.getExpiresIn());
            loginUserVO.setAccessTokenExpiration(token.getExpiration());
            loginUserVO.setExpired(token.isExpired());
            loginUserVO.setScope(token.getScope());
            loginUserVO.setTokenType(token.getTokenType());
            loginUserVO.setRefreshToken(token.getRefreshToken().getValue());
            loginUserVO.setRefreshTokenExpiration(token.getRefreshToken().getExpiration());
            //存储登录的用户
            System.out.println("====================");
            System.out.println(loginUserVO);
            redisUtil.set(loginUserVO.getAccessToken(), loginUserVO, TimeUnit.HOURS.toSeconds(1));
            //存储登录用户与token映射关系
            return ResponseVO.success(loginUserVO);
        } else {
            return ResponseVO.error(ResponseEnum.PASSWORDISWRONG);
        }

    }

    /**
     * @param refreshToken
     * @return
     * @description oauth2客户端刷新token
     * @date 2019/03/05 14:27:22
     * @author Zhifeng.Zeng
     */
    private Token oauthRefreshToken(String refreshToken) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("client_id", CLIENT_ID);
        paramMap.add("client_secret", CLIENT_SECRET);
        paramMap.add("refresh_token", refreshToken);
        paramMap.add("grant_type", GRANT_TYPE[1]);
        Token token = null;
        try {
            token = restTemplate.postForObject(serverConfig.getUrl() + UrlEnum.LOGIN_URL.getUrl(), paramMap, Token.class);
        } catch (RestClientException e) {
            try {
                //此处应该用自定义异常去返回，在这里我就不去具体实现了
                throw new Exception(ResponseEnum.REFRESH_TOKEN_INVALID.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return token;
    }

    @Override
    public void addUserBatch(Map<String,Object> param) {
        userMapper.insertUserBatch(param);
    }

    @Override
    public void updateUserBatch(Map<String,Object> param) {
        userMapper.updateUserBatch(param);
    }

    @Override
    public void deleteUserBatch(Map<String,Object> param) {
        userMapper.deleteUserBatch(param);
    }
}
