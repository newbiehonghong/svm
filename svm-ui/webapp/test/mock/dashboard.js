mock('/dashboard/config/query', options => {
    return [
        {
            id: 101,
            type: 11,
            width: 560,
            height: 280,
            count: 6,
            mode: ''
        },
        {
            id: 102,
            type: 12,
            width: 560,
            height: 280,
            count: 6,
            mode: ''
        },
        {
            id: 103,
            type: 21,
            width: 470,
            height: 280,
            count: 7,
            mode: 'service'
        },
        {
            id: 104,
            type: 22,
            width: 650,
            height: 280,
            count: 7,
            mode: 'api',
        },
        {
            id: 105,
            type: 31,
            width: 1120,
            height: 320,
            count: 0,
            mode: 'service',
            services: 'sample-service|domain-service',
            apis: ''
        },
        {
            id: 106,
            type: 32,
            width: 600,
            height: 320,
            count: 0,
            mode: 'api',
            services: '',
            apis: 'domain-service/user/query|domain-service/user/get/{id}|test/user/save'
        },
        {
            id: 107,
            type: 1,
            width: 520,
            height: 280
        }
    ]
});

mock('/dashboard/config/save', options => {
    return Mock.Random.integer(1000, 4000)
});

mock('/dashboard/config/update', options => {
    return {}
});

mock('/dashboard/config/delete/*', options => {
    return {}
});

mock('/dashboard/layout/save', options => {
    return {}
});

mock('/dashboard/api/query', options => {
    return [
        {
            value: 'sample-service', children: [
                { value: '/user/query/list1' },
                { value: '/user/saveUser' },
                { value: '/user/updateUser' }
            ]
        },
        {
            value: 'domain-service', children: [
                { value: '/user/query' },
                { value: '/user/get/{id}' },
                { value: '/user/save' },
                { value: '/user/update' }
            ]
        },
        {
            value: 'asp-test-th000', children: [
                { value: '/test/get/{id}' },
                { value: '/test/save' },
                { value: '/test/update/qs-001' }
            ]
        },
        {
            value: 'test', children: [
                { value: '/employee/query' },
                { value: '/dept/get/{id}' },
                { value: '/user/save' }
            ]
        },
        {
            value: 'test-example-domain', children: [
                { value: '/test/query' },
                { value: '/test/get/{id}' },
                { value: '/test/save' },
                { value: '/test/update/qs-001' },
                { value: '/test/update/qs-002' },
                { value: '/test/update/qs-003' },
                { value: '/test/update/qs-004' }
            ]
        }
    ]
});

mock('/dashboard/health', options => {
    return [
        { service: 'sample-service', value: 1 },
        { service: '/user/query/list1', value: 2 },
        { service: '/user/saveUser', value: 3 },
        { service: '/user/updateUser', value: 0 }
    ]
});

mock('/dashboard/top/rt/max', options => {
    var count = getQueryParameter(options.url, 'count');
    count = count ? parseInt(count) : 6;
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'path': Mock.Random.domain() + '/' + Mock.Random.word(),
            'service': Mock.Random.word(),
            'value': Mock.Random.float(10, 10000, 0, 3)
        });
    }
    return data
});

mock('/dashboard/top/rt/avg', options => {
    var count = getQueryParameter(options.url, 'count');
    count = count ? parseInt(count) : 6;
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'path': Mock.Random.domain() + '/' + Mock.Random.word(),
            'service': Mock.Random.word(),
            'value': Mock.Random.float(10, 10000, 0, 3)
        });
    }
    return data
});

mock('/dashboard/top/qps', options => {
    var count = getQueryParameter(options.url, 'count');
    count = count ? parseInt(count) : 6;
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'path': Mock.Random.domain() + '/' + Mock.Random.word(),
            'service': Mock.Random.word(),
            'value': Mock.Random.float(10, 10000, 0, 1)
        });
    }
    return data
});

mock('/dashboard/top/block', options => {
    var count = getQueryParameter(options.url, 'count');
    count = count ? parseInt(count) : 6;
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'path': Mock.Random.domain() + '/' + Mock.Random.word(),
            'service': Mock.Random.word(),
            'status': Mock.Random.integer(1, 4) + '',
            'value': Mock.Random.float(10, 10000, 0, 1)
        });
    }
    return data
});

mock('/dashboard/chart/rt', options => {
    var makeData = function () {
        var values = [];
        var start = 1566893074.548;
        for (let i = 0; i < 30; i++) {
            values.push([start + i * 30, '' + Mock.Random.float(10, 1000, 0, 3)])
        }
        return values;
    };

    var count = Mock.Random.integer(1, 5);
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'metric': {
                'fullpath': Mock.Random.domain() + '/' + Mock.Random.word(),
                'service': Mock.Random.word(),
            },
            'values': makeData()
        });
    }
    return data
});

mock('/dashboard/chart/qps', options => {
    var makeData = function () {
        var values = [];
        var start = 1566893074.548;
        for (let i = 0; i < 60; i++) {
            values.push([start + i * 30, '' + Mock.Random.float(10, 1000, 0, 1)])
        }
        return values;
    };

    var count = Mock.Random.integer(1, 5);
    var data = [];
    for (let i = 0; i < count; i++) {
        data.push({
            'metric': {
                'fullpath': Mock.Random.domain() + '/' + Mock.Random.word(),
                'service': Mock.Random.word(),
            },
            'values': makeData()
        });
    }
    return data
});