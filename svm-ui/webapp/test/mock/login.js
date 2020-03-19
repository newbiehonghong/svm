
mock('/login', options => {
    var data = JSON.parse(options.body);
    if (data && data.name == 'admin') {
        return {
            token: 'aaa',
            nickname: '测试人员',
            permissions: ['security_p_admin']
        };
    } else {
        return {
            status: 401,
            message: '用户名或密码错误'
        }
    }
});