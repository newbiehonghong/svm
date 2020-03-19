mock('/sample/user/save', options => {
    var data = JSON.parse(options.body);
    data.id = new Date().getTime();
    return data;
});

mock('/sample/user/update', options => {
    var data = JSON.parse(options.body);
    return data;
});

mock("/sample/user/delete/.*", options => {
    return {}
});

mock("/sample/user/get/.*", options => {
    var regexp = new RegExp('/sample/user/get/(.*)');
    var ret = regexp.exec(options.url);
    var userId = parseInt(ret[1]);
    return {
        id: userId,
        name: Mock.Random.cname(),//随机生成一个常见的中文姓名
        birthday: Mock.Random.date(), //指示生成的日期字符串的格式,默认为yyyy-MM-dd
        province: '' + Mock.Random.integer(1, 5),
        city: '' + Mock.Random.integer(1, 5),
        salary: Mock.Random.float(1000, 10000, 0, 2),
        gender: '' + Mock.Random.integer(0, 1),
        memo: Mock.Random.csentence(0, 20)//随机生成一段中文文本
    }
});

mock('/sample/user/queryAll.*', options => {
    var result = [];
    for (let i = 0; i < 10; i++) {
        result.push({
            id: Mock.Random.integer(1000, 10000),
            name: Mock.Random.cname(),//随机生成一个常见的中文姓名
            birthday: Mock.Random.date(), //指示生成的日期字符串的格式,默认为yyyy-MM-dd
            province: '' + Mock.Random.integer(1, 5),
            city: '' + Mock.Random.integer(1, 5),
            salary: Mock.Random.float(1000, 10000, 0, 2),
            gender: '' + Mock.Random.integer(0, 1),
            memo: Mock.Random.csentence(0, 20)//随机生成一段中文文本
        });
    }
    return {
        list: result,
        pages: 10,
        total: 1000
    };
});