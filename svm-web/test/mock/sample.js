const Random = Mock.Random;

/**
 * 保存用户信息
 * @param {Object} user 用户信息
 */
function mock_saveUser(user) {
    mock('/sample/user/save', options => {
        return new Date().getTime();
    });
}

/**
 * 更新用户信息
 * @param {Object} user 用户信息 
 */
function mock_updateUser(user) {
    mock('/sample/user/update', options => {
    });
}

/**
 * 删除用户信息
 * @param {String} id 用户ID 
 */
function mock_deleteUser(id) {
    mock("/sample/user/delete/.*", options => {
    }, true);
}

/**
 * 查询单条用户记录
 * @param {Number} id 用户ID
 */
function mock_getUser(id) {
    mock("/sample/user/get/.*", options => {
        var regexp = new RegExp('/sample/user/get/(.*)');
        var ret = regexp.exec(options.url);
        var userId = parseInt(ret[1]);
        return {
            id: userId,
            name: Random.cname(),//随机生成一个常见的中文姓名
            birthday: Random.date(), //指示生成的日期字符串的格式,默认为yyyy-MM-dd
            province: '' + Random.integer(1, 5),
            city: '' + Random.integer(1, 5),
            salary: Random.float(1000, 10000, 0, 2),
            gender: '' + Random.integer(0, 1),
            memo: Random.csentence(0, 20)//随机生成一段中文文本
        }
    }, true);
}

/**
 * 查询所有记录
 * @param {Number} pageNum 页码 
 */
function mock_queryAllUsers(pageNum) {
    mock('/sample/user/queryAll.*', options => {
        var result = [];
        for(let i = 0; i < 10; i++) {
            result.push({
                id: Random.integer(1000, 10000),
                name: Random.cname(),//随机生成一个常见的中文姓名
                birthday: Random.date(), //指示生成的日期字符串的格式,默认为yyyy-MM-dd
                province: '' + Random.integer(1, 5),
                city: '' + Random.integer(1, 5),
                salary: Random.float(1000, 10000, 0, 2),
                gender: '' + Random.integer(0, 1),
                memo: Random.csentence(0, 20)//随机生成一段中文文本
            });
        }
        return {
            list: result,
            pages: 10,
            total: 1000
        };
    }, true);
}

function mock_all() {
    //没找到好的方式来支持只mock个别请求和mock整个模块这两种方式之间自由切换
    //暂时可以在此方法里调用本模块所有mock方法
}