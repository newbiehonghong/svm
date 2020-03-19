import request from '@/utils/request';

/**
 * 查询所有记录
 */
export function queryAllDept() {
    return request({
        url: '/sample/user/queryAllDept'
    });
};