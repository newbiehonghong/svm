
export default {
    decode: function (name, value) {
        var item = (this[name] || []).find(element => element.value == value);
        return item && item.label || value;
    },
    //显示内容分类
    type: [
        { value: 1, label: '服务列表' },
        { value: 11, label: '最大响应时间排名' },
        { value: 12, label: '平均响应时间排名' },
        { value: 21, label: 'QPS排名' },
        { value: 22, label: '限流次数排名' },
        { value: 31, label: '平均响应时间图表' },
        { value: 32, label: 'QPS图表' }
    ],
    //限流规则
    status: [
        { value: '1', label: '限流' },
        { value: '2', label: '降级' },
        { value: '3', label: '授权' },
        { value: '4', label: '系统' }
    ]
}