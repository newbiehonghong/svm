package svm.app.controller.sample;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import svm.app.common.annotation.RequiredPermission;
import svm.app.dao.sample.SampleDeptMapper;
import svm.app.dao.sample.SampleUserMapper;
import svm.app.entity.sample.UserAttachment;
import svm.app.entity.sample.UserDO;
import svm.app.service.sample.UserService;
import svm.app.service.sample.dto.UserQueryDTO;
import svm.common.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/sample/user")
@RequiredPermission("sample/user/query")
public class SampleUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SampleUserMapper sampleUserMapper;

    @Autowired
    private SampleDeptMapper sampleDeptMapper;

    private Logger logger = LoggerFactory.getLogger(SampleUserController.class);

    @PostMapping("/save")
    @ResponseBody
    @RequiredPermission("sample/user/add")
    public Long saveUser(@RequestBody UserDO user) {
        return userService.saveUser(user);
    }

    @PostMapping("/update")
    @RequiredPermission("sample/user/add")
    public void updateUser(@RequestBody UserDO user) {
        userService.updateUser(user);
    }

    @PostMapping("/delete/{ids}")
    @RequiredPermission("sample/user/delete")
    public void deleteUser(@PathVariable String ids) {
        String[] strIds = ids.split(",");
        int count = strIds.length;
        Long[] idArray = new Long[count];
        for(int i = 0; i < count; i++) {
            idArray[i] = Long.parseLong(strIds[i]);
        }
        userService.deleteUsers(idArray);
    }

    @PostMapping("/get/{id}")
    public Object getUser(@PathVariable Long id) {
        logger.debug("get id: {0}", id);
        return userService.getUser(id);
    }

    @PostMapping("/queryAll")
    @ResponseBody
    public Object queryAllUsers(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize, pageNum == 1);
        List<UserDO> users = sampleUserMapper.queryAllUsers();
        return new PageInfo(users);
    }

    @PostMapping("/query")
    @ResponseBody
    public Object queryUsers(
            @RequestBody UserQueryDTO userQuery,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize, pageNum == 1);
        List<UserDO> users = sampleUserMapper.queryUsers(userQuery);
        return new PageInfo(users);
    }

    @PostMapping("/queryDownload")
    @ResponseBody
    public Object queryDownloadUsers(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
        PageHelper.startPage(pageNum, pageSize, pageNum == 1);
        List<UserDO> users = sampleUserMapper.queryDownloadUsers();
        return new PageInfo(users);
    }

    @PostMapping("/upload")
    @ResponseBody
    @RequiredPermission("user/add")
    public Long upload(@RequestParam(value="picture",required = false)MultipartFile image) {
        if(image == null) {
            throw new BusinessException("上传文件不能为空");
        }

        UserAttachment attachment = new UserAttachment();
        attachment.setFileName(image.getOriginalFilename());
        attachment.setFileType(image.getContentType());
        try {
            attachment.setContent(image.getBytes());
        } catch(Exception ex) {
            throw new BusinessException(ex);
        }

        return userService.saveAttachment(attachment);
    }

    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @PathVariable Long id) {
        UserAttachment attachment = userService.queryAttachment(id);
        if(attachment == null) {
            throw new BusinessException("文件不存在");
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
        } catch(Exception ex){
            throw new BusinessException(ex);
        }
    }

    @PostMapping("/queryAllDept")
    @ResponseBody
    public Object queryAllDept() {
        return sampleDeptMapper.queryAll();
    }
}