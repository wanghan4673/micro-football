<template>
  <div style="display: flex; justify-content: center;">
    <TopNav v-if="topNav.topNavStatus" style="position: fixed; width: 100%; z-index: 99;"></TopNav>
    <AdminTopMenu v-if="topNav.adminNavStatus" style="position: fixed; width: 100%;"></AdminTopMenu>
    <div :class="{ 'with-margin': topNav.topNavStatus || topNav.adminNavStatus }" style="width: 100%;">
      <router-view></router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import TopNav from './components/TopNav.vue';
import AdminTopMenu from './components/AdminComponents/AdminTopMenu.vue'
import { topNavStore } from './stores/topNav';

const topNav = topNavStore()
</script>

<style>
@import './assets/css/frame.scss';
.with-margin {
  margin-top: 60px;
}
</style>
