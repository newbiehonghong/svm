<template>
    <cmpError v-bind:errorCode="errorCode" v-bind:errorDesc="errorDesc"></cmpError>
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