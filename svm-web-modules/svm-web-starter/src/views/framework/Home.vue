<template>
    <div class="wrapper">
        <v-head @collapse="doCollapse"></v-head>
        <v-sidebar :collapse="collapse"></v-sidebar>
        <div class="content-box" :class="{'content-collapse':collapse}">
            <v-tags @create-tag="doCreateTag" @remove-tag="doRemoveTag"></v-tags>
            <div class="content">
                <keep-alive :include="tagsList">
                    <router-view></router-view>
                </keep-alive>
            </div>
        </div>
    </div>
</template>

<script>
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    import vTags from './Tags.vue';
    export default {
        data() {
            return {
                tagsList: [],
                collapse: false
            }
        },
        components: {
            vHead, vSidebar, vTags
        },
        methods: {
            doCollapse: function(value) {
                this.collapse = value;
            },

            // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
            // 注意： keep-alive要求组件必须有name
            doCreateTag: function(name) {
                this.tagsList.push(name);
            },

            doRemoveTag(name) {
                for(let i = this.tagsList.length - 1; i >= 0; i--) {
                    if(this.tagsList[i] == name) {
                        this.tagsList.splice(i, 1);
                        return;
                    }
                }
            }
        }
    }
</script>
