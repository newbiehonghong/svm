package svm.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import svm.common.exception.BaseRuntimeException;
import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;
import svm.sample.service.UserService;
import svm.sample.service.dto.UserQueryDTO;
import svm.security.annotation.RequiredPermission;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/sample/user")
@RequiredPermission("sample/user/query")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/save")
    @RequiredPermission("sample/user/add")
    public void saveUser(@RequestBody UserDO user) {
        userService.saveUser(user);
    }

    @PostMapping("/update")
    @RequiredPermission("sample/user/add")
    public void updateUser(@RequestBody UserDO user) {
        userService.updateUser(user);
    }

    @PostMapping("/delete/{id}")
    @RequiredPermission("sample/user/delete")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/get/{id}")
    public Object getUser(@PathVariable Long id) {
        logger.debug("get id: {0}", id);
        return userService.getUser(id);
    }

    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAllUsers(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.queryAllUsers(pageNum, pageSize);
    }

    @PostMapping("/query")
    @ResponseBody
    public Object queryUsers(
            @RequestBody UserQueryDTO userQuery,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.queryUsers(userQuery, pageNum, pageSize);
    }

    @PostMapping("/queryDownload")
    @ResponseBody
    public Object queryDownloadUsers(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize) {
        return userService.queryDownloadUsers(pageNum, pageSize);
    }

    @PostMapping("/upload")
    @ResponseBody
    @RequiredPermission("user/add")
    public Long upload(@RequestParam(value="picture",required = false)MultipartFile image) {
        if(image == null) {
            throw new BaseRuntimeException("上传文件不能为空");
        }

        UserAttachment attachment = new UserAttachment();
        attachment.setFileName(image.getOriginalFilename());
        attachment.setFileType(image.getContentType());
        try {
            attachment.setContent(image.getBytes());
        } catch(Exception ex) {
            throw new BaseRuntimeException(ex);
        }

        return userService.saveAttachment(attachment);
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable Long id) {
        UserAttachment attachment = userService.queryAttachment(id);
        if(attachment == null) {
            throw new BaseRuntimeException("文件不存在");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            //解决文件名中文乱码问题
            String downloadFileName = new String(attachment.getFileName().getBytes("UTF-8"),"iso-8859-1");
            //告诉浏览器以"attachment"方式打开文件
            headers.setContentDispositionFormData("attachment", downloadFileName);
            //设置请求头的媒体格式类型为 application/octet-stream(二进制流数据)
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(attachment.getContent(), headers, HttpStatus.CREATED);
        } catch(Exception e){
            throw new BaseRuntimeException(e);
        }
    }
}