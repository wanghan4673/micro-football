<template>
    <div style="display: flex;">
        <ForumSideNav/>
        <div style="display: flex; flex-direction: column; padding: 10px; width: 100%; ">
            <ForumFilter style="margin-bottom: 20px;"></ForumFilter>
            <ForumPostList></ForumPostList>
        </div>
    </div>
</template>

<script setup>
import ForumSideNav from '../components/ForumSideNav.vue'
import ForumFilter from '../components/ForumFilter.vue'
import ForumPostList from '../components/ForumPostList.vue'
</script>

<style scoped>

</style>