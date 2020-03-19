<template>
    <cmp-error :errorCode="errorCode" :errorDesc="errorDesc"></cmp-error>
</template>

<script>
import cmpError from './error';
export default {
    data: function() {
        return {
            errorCode: '403',
            errorDesc: '你没有权限访问该页面'
        }
    },
    components: {
        cmpError
    }
}
</script>