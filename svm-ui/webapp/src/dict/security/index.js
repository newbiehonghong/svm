export default {
    decode: function (name, value) {
        var item = (this[name] || []).find(element => element.value == value);
        return item && item.label || value;
    },
    security_permission_type: [
        { value: 'page', label: '页面' },
        { value: 'service', label: '服务' }
    ]
}