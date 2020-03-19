export default {
    decode: function (name, value) {
        var item = (this[name] || []).find(element => element.value == value);
        return item && item.label || value;
    },
    //省
    sample_province: [
        { value: '1', label: '黑龙江' },
        { value: '2', label: '吉林' },
        { value: '3', label: '辽宁' },
        { value: '4', label: '河北' },
        { value: '5', label: '山东' }
    ],
    //市
    sample_city: [
        { value: '1', label: '哈尔滨' },
        { value: '2', label: '长春' },
        { value: '3', label: '沈阳' },
        { value: '4', label: '大连' },
        { value: '5', label: '石家庄' },
        { value: '6', label: '济南' }
    ]
}