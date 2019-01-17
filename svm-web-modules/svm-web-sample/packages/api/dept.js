/**
 * 查询所有记录
 */
export function queryAllDept(cmp) {
    return cmp.$api.request({
        url: '/sample/user/queryAllDept'
    });
};