<template>
  <div style="display: flex; justify-content: center;">
    <TopNav style="position: fixed; width: 100%; z-index: 99999"></TopNav>
    <div style="padding-top: 60px;width: 100%;">
      <router-view style=""/>
    </div>
  </div>
</template>

<script setup>
import TopNav from './components/TopNav.vue';
</script>
<style>

</style>
