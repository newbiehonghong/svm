mock('/security/role/save', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock('/security/role/update', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock("/security/role/delete/.*", options => {
    return {}
});

mock('/security/role/queryAll', options => {
    var count = Mock.Random.integer(5, 30);
    var result = [];
    for (let i = 1; i <= count; i++) {
        result.push({
            id: i,
            name: Mock.Random.name()
        });
    }
    return result;
});

mock('/security/role/queryByUserId/.*', options => {
    var count = Mock.Random.integer(1, 10);
    var result = [];
    for (let i = 0; i < count; i++) {
        result.push(Mock.Random.integer(1, 10));
    }
    return result;
});

mock('/security/role/queryByPermissionId/.*', options => {
    var count = Mock.Random.integer(1, 10);
    var result = [];
    for (let i = 0; i < count; i++) {
        result.push(Mock.Random.integer(1, 10));
    }
    return result;
});

mock('/security/role/saveRoleUsers', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock('/security/role/saveRolePermissions', options => {
    var data = JSON.parse(options.body);
    return data;
});