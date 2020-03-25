mock('/security/user/save', options => {
    var data = JSON.parse(options.body);
    data.id = new Date().getTime();
    return data;
});

mock('/security/user/update', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock('/security/user/updatePwd', options => {
    return {};
});

mock("/security/user/delete/.*", options => {
    return {}
});

mock('/security/user/queryAll', options => {
    var count = Mock.Random.integer(30, 200);
    var result = [];
    for (let i = 1; i <= count; i++) {
        result.push({
            id: i,
            name: Mock.Random.name(),
            nickname: Mock.Random.cname(),
            password: Mock.Random.string(24)
        });
    }
    return result;
});

mock('/security/user/queryByRoleId/.*', options => {
    var count = Mock.Random.integer(1, 10);
    var result = [];
    for (let i = 0; i < count; i++) {
        result.push(Mock.Random.integer(1, 100));
    }
    return result;
});

mock('/security/user/saveUserRoles', options => {
    var data = JSON.parse(options.body);
    return data;
});