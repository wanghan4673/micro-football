<template>
  <div style="display: flex; justify-content: center;">
    <TopNav v-if="!isAdminPath" style="position: fixed; width: 100%; z-index: 99999"></TopNav>
    <AdminTopMenu v-else style="position: fixed; width: 100%; z-index: 99999"></AdminTopMenu>
    <div style="padding-top: 60px;width: 100%;">
      <router-view style=""/>
    </div>
  </div>
</template>

<script setup>
import TopNav from './components/TopNav.vue';
import AdminTopMenu from './components/AdminComponents/AdminTopMenu.vue'
import { useRoute } from 'vue-router';
import {ref,watch} from 'vue'

const route = useRoute();
let isAdminPath = ref(false);
watch(() => route.path,(newpath,prepath) =>{
  isAdminPath.value = route.path.startsWith('/admin')
})
</script>
<style>

</style>
