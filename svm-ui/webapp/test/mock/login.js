
mock('/login', options => {
    var data = JSON.parse(options.body);
    if (data && data.name == 'admin') {
        return {
            token: 'aaa',
            nickname: '测试人员',
            permissions: ['p_security_permission']
        };
    } else {
        return {
            status: 401,
            message: '用户名或密码错误'
        }
    }
});

mock('/changePwd', options => {
    return {};
});