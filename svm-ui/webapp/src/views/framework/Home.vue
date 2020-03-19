<template>
    <div class="content-wrapper">
        <v-sidebar></v-sidebar>
        <v-header></v-header>
        <div class="content-box">
            <vue-scroll>
                <div class="content">
                    <router-view></router-view>
                </div>
            </vue-scroll>
        </div>
    </div>
</template>

<script>
import vSidebar from "./Sidebar.vue";
import vHeader from "./Header.vue";
export default {
	components: { vSidebar, vHeader }
};
</script>
